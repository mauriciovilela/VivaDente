package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import com.odonto.BLL.PagamentoBLL;
import com.odonto.BLL.PagamentoHistoricoBLL;
import com.odonto.model.TbAuditoria;
import com.odonto.model.TbPaciente;
import com.odonto.model.TbPagamento;
import com.odonto.model.TbPagamentoHistorico;
import com.odonto.security.SessionContext;
import com.odonto.service.AuditoriaService;
import com.odonto.service.PagamentoService;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PagamentoBeanPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PagamentoBLL pagamentoBLL;

	@Inject
	private PagamentoHistoricoBLL pagamentoHistBLL;

	@Inject
	private PagamentoService pagamentoService;

	private TbPagamento pagamento;

	@Inject
	private AuditoriaService auditoria;

	private String texto;
	private List<TbPagamentoHistorico> filtrados;

	@NotNull
	private TbPaciente tbPaciente;

	private TbPagamentoHistorico selecionado;
	
	private double acumulaAUX = 0;

	public PagamentoBeanPesquisa() {
		texto = StringUtils.EMPTY;
	}

	public void excluir() {
		pagamentoHistBLL.remover(selecionado);

		pagamento = selecionado.getTbPagamento();
		pagamento.setVlPago(pagamento.getVlPago().subtract(selecionado.getVlPago()));
		pagamento.setFlPago(false);
		pagamentoService.salvar(pagamento, null);

		filtrados.remove(selecionado);

		filtrados = pagamentoHistBLL.porPagamento(pagamento.getId());
		// Caso tenha removido o ultimo historico, remove também o registro principal
		if (filtrados.size() == 0) {
			pagamentoBLL.remover(pagamento);
		}

		// Grava log de auditoria
		TbAuditoria audit = new TbAuditoria();
		audit.setDsDescricao("Pagamento excluído. Usuario: "
				+ SessionContext.getInstance().getUsuarioLogado().getDsNome() + ", Valor total "
				+ selecionado.getVlTotal() + ", Valor pago " + selecionado.getVlPago() + ", Data "
				+ selecionado.getDtEntrada() + ", Paciente " + selecionado.getTbPaciente().getDsNome());
		auditoria.salvar(audit);

		FacesUtil.addInfoMessage("Registro excluído com sucesso.");
	}

	public void pesquisar() {
		filtrados = pagamentoHistBLL.porPaciente(tbPaciente);
		tbPaciente = null;
	}

	public double getValorAcumulado() {
		double aux = acumulaAUX;
		acumulaAUX = 0;
		return aux;
	}

	public void acumulaValor(double value) {
		acumulaAUX += value;
	}

	public List<TbPagamentoHistorico> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<TbPagamentoHistorico> filtrados) {
		this.filtrados = filtrados;
	}

	public TbPagamentoHistorico getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(TbPagamentoHistorico selecionado) {
		this.selecionado = selecionado;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public TbPaciente getTbPaciente() {
		return tbPaciente;
	}

	public void setTbPaciente(TbPaciente tbPaciente) {
		this.tbPaciente = tbPaciente;
	}

}