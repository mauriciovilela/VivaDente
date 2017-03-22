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

import com.odonto.BLL.LaboratorioBLL;
import com.odonto.BLL.LaboratorioHistoricoBLL;
import com.odonto.constants.Constants;
import com.odonto.model.TbAuditoria;
import com.odonto.model.TbLaboratorio;
import com.odonto.model.TbLaboratorioHistorico;
import com.odonto.model.TbUsuario;
import com.odonto.security.SessionContext;
import com.odonto.service.AuditoriaService;
import com.odonto.service.LaboratorioService;
import com.odonto.service.NegocioException;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class LaboratorioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private LaboratorioService laboratorioService;

	@Inject
	private LaboratorioBLL laboratorioBLL;

	@Inject
	private LaboratorioHistoricoBLL historicoBLL;
	
	private TbLaboratorio laboratorio;
	private String texto;

	@NotNull
	private Integer idDentista;

	@Inject
	private AuditoriaService auditoria;

	private BigDecimal vlPagoAtual;
	private BigDecimal vlValorAPagar;

	@Inject
	private HomeMB homeMB;
	
	private List<TbLaboratorioHistorico> historico;
	
	public LaboratorioBean() {
		limpar();
	}

	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
			if (laboratorio.getTbDentista() != null) {
				idDentista = laboratorio.getTbDentista().getId();
			}
			else {
				laboratorio.setDtEntrada(new Date());
			}
		}
	}

	private void limpar() {
		idDentista = null;
		laboratorio = new TbLaboratorio();
		texto = StringUtils.EMPTY;
	}

	public void validarSalvar() {
		
		if (vlValorAPagar == null) {
			vlValorAPagar = laboratorio.getVlTotal();
		}
		if (laboratorio.getVlPago().subtract(vlValorAPagar).doubleValue() > 0) {
			throw new NegocioException("Valor pago não pode ser maior que o máximo: R$ " + vlValorAPagar.toString());
		}	
		
		vlPagoAtual = laboratorio.getVlPago();
		if (laboratorio.getId() != null) {
			TbLaboratorio pagamentoAntigo = laboratorioBLL.porId(laboratorio.getId());
			BigDecimal vlPagoAcumulado = laboratorio.getVlPago().add(pagamentoAntigo.getVlPago()); 
			laboratorio.setVlPago(vlPagoAcumulado);
		}

		// Inclui o pagamento
		salvar();
	}
	
	public void salvar() {
		
		TbUsuario dentista = new TbUsuario();
		dentista.setId(idDentista);
		
		this.laboratorio.setTbUsuario(SessionContext.getInstance().getUsuarioLogado());
		this.laboratorio.setTbDentista(dentista);
		if (StringUtils.isNotBlank(this.laboratorio.getNomePaciente())) {
			this.laboratorio.setNomePaciente(this.laboratorio.getNomePaciente().trim().toUpperCase());
		}
		else {
			this.laboratorio.setNomePaciente(StringUtils.EMPTY);
		}
		this.laboratorio.setDsServico(texto);

		// Grava log de auditoria
		TbAuditoria audit = new TbAuditoria();
		audit.setDsDescricao("Laboratório incluido. Paciente: "
				+ this.laboratorio.getTbUsuario().getDsNome() + ", Valor total "
				+ this.laboratorio.getVlTotal() + ", Valor pago " + this.laboratorio.getVlPago() + ", Data "
				+ this.laboratorio.getDtEntrada() + ", Usuário " + SessionContext.getInstance().getUsuarioLogado().getDsNome());

		// Persiste o pagamento
		this.laboratorio = laboratorioService.salvar(this.laboratorio, vlPagoAtual);
		
		// Atualiza os dados na sessao
		homeMB.setPacientesLab(laboratorioBLL.pacientesLab());		

		// Insere um registro de log
		auditoria.salvar(audit);
		
		limpar();
		FacesUtil.addInfoMessage(Constants.msgSucesso);
	}
	
	public void verificaPendencias() {
		String nomePaciente = laboratorio.getNomePaciente();
		TbLaboratorio pagamentoPendente = laboratorioBLL.validaPendencia(idDentista, nomePaciente);
		setHistorico(new ArrayList<TbLaboratorioHistorico>());
		if (pagamentoPendente != null) {
			vlPagoAtual = laboratorio.getVlPago();			
			
			laboratorio = pagamentoPendente;
			vlValorAPagar = laboratorio.getVlTotal().subtract(laboratorio.getVlPago());
			laboratorio.setVlPago(null);
			laboratorio.setDtEntrada(new Date());
			laboratorio.setDsDescricao(StringUtils.EMPTY);

			setHistorico(historicoBLL.porLaboratorio(laboratorio.getId()));
			
			FacesUtil.addWarningMessage("Cliente com pagamento pendente.");
		}
		else {
			laboratorio = new TbLaboratorio();
			laboratorio.setDtEntrada(new Date());
			laboratorio.setNomePaciente(nomePaciente);
			setHistorico(new ArrayList<TbLaboratorioHistorico>());
		}
	}

	public List<String> completeText(String query) {
		List<String> results = laboratorioBLL.listarPorServico(query);
		return results;
	}

	public TbLaboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(TbLaboratorio Laboratorio) {
		this.laboratorio = Laboratorio;
	}

	public boolean isEditando() {
		return this.laboratorio.getId() != null;
	}

	public Integer getIdDentista() {
		return idDentista;
	}

	public void setIdDentista(Integer idDentista) {
		this.idDentista = idDentista;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
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

	public List<TbLaboratorioHistorico> getHistorico() {
		return historico;
	}

	public void setHistorico(List<TbLaboratorioHistorico> historico) {
		this.historico = historico;
	}

}
