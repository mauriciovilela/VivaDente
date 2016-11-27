package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.PacienteBLL;
import com.odonto.BLL.PagamentoHistoricoBLL;
import com.odonto.model.TbPagamentoHistorico;
import com.odonto.security.SessionContext;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PagamentoHistoricoMDBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PagamentoHistoricoBLL bllHistorico;

	@Inject
	private PacienteBLL pacienteBLL;
	private String nome;
	
	private List<TbPagamentoHistorico> historico;

	private int acumulaAUX = 0;

	public int getValorAcumulado() {
		int aux = acumulaAUX;
		acumulaAUX = 0;
		return aux;
	}

	public void acumulaValor(int idTipoPgto, int value) {
		acumulaAUX += value;
	}
	
	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
			Integer id = SessionContext.getInstance().getID();
			nome = pacienteBLL.porId(id).getDsNome();
			historico = bllHistorico.porPaciente(id);
		}
	}

	public List<TbPagamentoHistorico> getHistorico() {
		return historico;
	}

	public void setHistorico(List<TbPagamentoHistorico> historico) {
		this.historico = historico;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}

