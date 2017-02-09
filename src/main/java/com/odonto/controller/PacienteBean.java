package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.odonto.BLL.PacienteBLL;
import com.odonto.BLL.PagamentoHistoricoBLL;
import com.odonto.constants.Constants;
import com.odonto.dto.AgendaOUT;
import com.odonto.model.TbPaciente;
import com.odonto.model.TbPagamentoHistorico;
import com.odonto.model.TbUsuario;
import com.odonto.security.SessionContext;
import com.odonto.service.AgendaService;
import com.odonto.service.PacienteService;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PacienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PacienteService pacienteService;

	@Inject
	private PacienteBLL pacienteBLL;
	
	private TbPaciente paciente;
	
	private String aniversario;
	
	private Boolean panfleto;
	private String promocao;

	@Inject
	private PagamentoHistoricoBLL bllHistoricoPagamento;

	@Inject
	private AgendaService agendaService;
	
	private List<TbPagamentoHistorico> historicoPagamentos;
	private List<AgendaOUT> historicoAgenda;

	private int acumulaAUX = 0;

	public int getValorAcumulado() {
		int aux = acumulaAUX;
		acumulaAUX = 0;
		return aux;
	}

	public void acumulaValor(int idTipoPgto, int value) {
		acumulaAUX += value;
	}
	
	public PacienteBean() {
		limpar();
	}
	
	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
			if (paciente.getId() == null) {
				paciente.setDsCidade("Uberlândia, MG");
			}
			else {
				historicoPagamentos = bllHistoricoPagamento.porPaciente(paciente.getId());
				historicoAgenda = agendaService.listarPorPaciente(paciente.getId());
			}
		}
	}
	
	private void limpar() {
		paciente = new TbPaciente();
		aniversario = StringUtils.EMPTY;
	}
	
	public void excluir() {
		pacienteBLL.remover(paciente);
		FacesUtil.addInfoMessage("Registro excluído com sucesso.");
	}
	
	public void salvar() {
		TbUsuario logado = SessionContext.getInstance().getUsuarioLogado();
		this.paciente.setTbUsuario(logado);
		this.paciente = pacienteService.salvar(this.paciente);
		limpar();		
		FacesUtil.addInfoMessage(Constants.msgSucesso);
	}
	
	public void atualizaPromocao() {
		
	}

	public List<TbPaciente> pacientesPorNome(String query) {
		List<TbPaciente> registros = pacienteBLL.listarPorNome(query);
		return registros;
	}

	public TbPaciente getPaciente() {
		return paciente;
	}
	
	public void setPaciente(TbPaciente Paciente) {
		this.paciente = Paciente;	
	}

	public boolean isEditando() {
		return this.paciente.getId() != null;
	}

	public String getAniversario() {
		return aniversario;
	}

	public void setAniversario(String aniversario) {
		this.aniversario = aniversario;
	}

	public Boolean getPanfleto() {
		return panfleto;
	}

	public void setPanfleto(Boolean panfleto) {
		this.panfleto = panfleto;
	}
	
	public String getPromocao() {
		return promocao;
	}

	public void setPromocao(String promocao) {
		this.promocao = promocao;
	}

	public List<AgendaOUT> getHistoricoAgenda() {
		return historicoAgenda;
	}

	public void setHistoricoAgenda(List<AgendaOUT> historicoAgenda) {
		this.historicoAgenda = historicoAgenda;
	}

	public List<TbPagamentoHistorico> getHistoricoPagamentos() {
		return historicoPagamentos;
	}

	public void setHistoricoPagamentos(List<TbPagamentoHistorico> historicoPagamentos) {
		this.historicoPagamentos = historicoPagamentos;
	}

}
