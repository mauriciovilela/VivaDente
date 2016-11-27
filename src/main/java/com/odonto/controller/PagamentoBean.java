package com.odonto.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import com.odonto.BLL.AgendaBLL;
import com.odonto.BLL.PagamentoBLL;
import com.odonto.BLL.PagamentoHistoricoBLL;
import com.odonto.constants.Constants;
import com.odonto.dto.AgendaOUT;
import com.odonto.model.TbAgenda;
import com.odonto.model.TbAuditoria;
import com.odonto.model.TbPagamento;
import com.odonto.model.TbPagamentoHistorico;
import com.odonto.security.SessionContext;
import com.odonto.service.AuditoriaService;
import com.odonto.service.NegocioException;
import com.odonto.service.PagamentoService;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PagamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PagamentoService pagamentoService;

	@Inject
	private PagamentoBLL bllPagamento;

	@Inject
	private AgendaBLL bllAgenda;
	
	@Inject
	private PagamentoHistoricoBLL bllHistorico;
	
	private TbPagamento pagamento;
	private TbAgenda agenda;

	@NotNull
	private Integer idResponsavel;

	@NotNull
	private Integer idDentista;

	private boolean tipoPgtoMaquina;
	private boolean tipoCredito;
	private boolean pgtoObrigatorio;

	private List<TbPagamento> filtradosPgto;
	private List<AgendaOUT> agendaHoje;
	private BigDecimal vlPagoAtual;
	private BigDecimal vlValorAPagar;
	private BigDecimal vlAcrescimo;
	private List<TbPagamentoHistorico> historico;

	@Inject
	private AuditoriaService auditoria;

	public PagamentoBean() {
		limpar();
	}

	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
			if (agenda != null) {
				pagamento.setTbPaciente(agenda.getTbPaciente());
				idDentista = agenda.getTbDentista().getId();
			}
			idResponsavel = SessionContext.getInstance().getUsuarioLogado().getId();
		}
	}

	public void atualizaTotal() {
		pagamento.setVlPago(pagamento.getVlTotal());
	}

	public void selecionarPacientes(Integer idAgenda) {
		TbAgenda agenda = bllAgenda.porId(idAgenda);
		pagamento.setTbPaciente(agenda.getTbPaciente());
		idDentista = agenda.getTbDentista().getId();
		RequestContext.getCurrentInstance().execute("PF('dlgPacientesHoje').hide();");
	}

	private void limpar() {
		setPagamento(new TbPagamento());
		tipoCredito = false;
		pgtoObrigatorio = false;
		pagamento.setDtEntrada(new Date());
		historico = new ArrayList<TbPagamentoHistorico>();
	}
	
	public void adicionarPagamento() {
		if (vlAcrescimo == null) {
			throw new NegocioException("Campo valor obrigatório.");
		}
		vlValorAPagar = vlValorAPagar.add(vlAcrescimo);
		
		pagamento = bllPagamento.porId(pagamento.getId());
		pagamento.setVlTotal(pagamento.getVlTotal().add(vlAcrescimo));
		pagamento = pagamentoService.salvar(this.pagamento, null);
		
		pagamento.setVlPago(null);
		pagamento.setDsObservacao(StringUtils.EMPTY);
		pagamento.setDtEntrada(new Date());
		
		// Grava log de auditoria
		TbAuditoria audit = new TbAuditoria();
		audit.setDsDescricao("Acréscimo incluido. ID pgto: " + this.pagamento.getId() + 
				", Valor acréscimo " + vlAcrescimo + 
				", Valor total " + this.pagamento.getVlTotal());

		// Insere um registro de log
		auditoria.salvar(audit);

		vlAcrescimo = null;
	}

	public void validarSalvar() {

		// Regra pagamento duplicado
		Integer idPaciente = pagamento.getTbPaciente().getId();

		List<TbPagamento> lista = bllPagamento.porPacienteDataValorPgtoDentista(idPaciente, pagamento.getDtEntrada(),
				pagamento.getVlTotal(), idDentista);
		if (lista.size() > 0) {
			throw new NegocioException("Já existe um pagamento deste valor para este paciente e dentista nesta data.");
		}
		
		if (vlValorAPagar == null) {
			vlValorAPagar = pagamento.getVlTotal();
		}
		if (pagamento.getVlPago().subtract(vlValorAPagar).doubleValue() > 0) {
			throw new NegocioException("Valor pago não pode ser maior que o máximo: R$ " + vlValorAPagar.toString());
		}	
		
		vlPagoAtual = pagamento.getVlPago();
		if (pagamento.getId() != null) {
			TbPagamento pagamentoAntigo = bllPagamento.porId(pagamento.getId());
			BigDecimal vlPagoAcumulado = pagamento.getVlPago().add(pagamentoAntigo.getVlPago()); 
			pagamento.setVlPago(vlPagoAcumulado);
		}

		// Inclui o pagamento
		salvar();
	}
	
	public void verificaPendencias() {
		TbPagamento pagamentoPendente = bllPagamento.validaPendencia(pagamento.getTbPaciente().getId());
		historico = new ArrayList<TbPagamentoHistorico>();
		if (pagamentoPendente != null) {
			vlPagoAtual = pagamento.getVlPago();			
			
			pagamento = pagamentoPendente;
			vlValorAPagar = pagamento.getVlTotal().subtract(pagamento.getVlPago());
			
			pagamento.setVlPago(null);
			pagamento.setDsObservacao(StringUtils.EMPTY);
			pagamento.setDtEntrada(new Date());

			historico = bllHistorico.porPagamento(pagamento.getId());
			
			FacesUtil.addWarningMessage("Paciente com pagamento pendente.");
		}
	}

	public void salvar() {

		// Grava log de auditoria
		TbAuditoria audit = new TbAuditoria();
		audit.setDsDescricao("Pagamento incluido. Paciente: "
				+ this.pagamento.getTbPaciente().getDsNome() + ", Valor total "
				+ this.pagamento.getVlTotal() + ", Valor pago " + this.pagamento.getVlPago() + ", Data "
				+ this.pagamento.getDtEntrada() + ", Usuário " + SessionContext.getInstance().getUsuarioLogado().getDsNome());

		// Persiste o pagamento
		pagamento = pagamentoService.salvar(this.pagamento, vlPagoAtual);
		
		// Insere um registro de log
		auditoria.salvar(audit);
		
		limpar();
		
		FacesUtil.addInfoMessage(Constants.msgSucesso);
		
		filtradosPgto = new ArrayList<TbPagamento>();
	}

	public TbPagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(TbPagamento pagamento) {
		this.pagamento = pagamento;
	}

	public boolean isTipoPgtoMaquina() {
		return tipoPgtoMaquina;
	}

	public void setTipoPgtoMaquina(boolean tipoPgtoMaquina) {
		this.tipoPgtoMaquina = tipoPgtoMaquina;
	}

	public List<TbPagamento> getFiltradosPgto() {
		return filtradosPgto;
	}

	public void setFiltradosPgto(List<TbPagamento> filtradosPgto) {
		this.filtradosPgto = filtradosPgto;
	}

	public Integer getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}

	public TbAgenda getAgenda() {
		return agenda;
	}

	public void setAgenda(TbAgenda agenda) {
		this.agenda = agenda;
	}

	public List<AgendaOUT> getAgendaHoje() {
		return agendaHoje;
	}

	public void setAgendaHoje(List<AgendaOUT> agendaHoje) {
		this.agendaHoje = agendaHoje;
	}

	public Integer getIdDentista() {
		return idDentista;
	}

	public void setIdDentista(Integer idDentista) {
		this.idDentista = idDentista;
	}

	public boolean isTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(boolean tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public boolean isPgtoObrigatorio() {
		return pgtoObrigatorio;
	}

	public void setPgtoObrigatorio(boolean pgtoObrigatorio) {
		this.pgtoObrigatorio = pgtoObrigatorio;
	}

	public BigDecimal getVlPagoAtual() {
		return vlPagoAtual;
	}

	public void setVlPagoAtual(BigDecimal vlPagoAtual) {
		this.vlPagoAtual = vlPagoAtual;
	}

	public BigDecimal getVlValorAPagar() {
		return vlValorAPagar;
	}

	public void setVlValorAPagar(BigDecimal vlValorAPagar) {
		this.vlValorAPagar = vlValorAPagar;
	}

	public List<TbPagamentoHistorico> getHistorico() {
		return historico;
	}

	public void setHistorico(List<TbPagamentoHistorico> historico) {
		this.historico = historico;
	}

	public BigDecimal getVlAcrescimo() {
		return vlAcrescimo;
	}

	public void setVlAcrescimo(BigDecimal vlAcrescimo) {
		this.vlAcrescimo = vlAcrescimo;
	}

}
