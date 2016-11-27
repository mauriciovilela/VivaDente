package com.odonto.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.PagamentoBLL;
import com.odonto.model.TbPagamento;
import com.odonto.security.SessionContext;
import com.odonto.util.Util;

@Named
@ViewScoped
public class RptInadimplentesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PagamentoBLL pagamentoBLL;

	private List<TbPagamento> filtrados;
	
	private TbPagamento selecionado;

	@PostConstruct
	private void pageLoad() {
		setFiltrados(pagamentoBLL.inadimplentes());
	}
	
	public void modalHistoricoPagamento(Integer idPaciente) {
		SessionContext.getInstance().setID(idPaciente);
		Util.abreModal("/restrita/pagamento/PagamentoHistoricoMD");
	}
	
	public BigDecimal valorRestante(TbPagamento item) {
		return item.getVlTotal().subtract(item.getVlPago());
	}
	
	public List<TbPagamento> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<TbPagamento> filtrados) {
		this.filtrados = filtrados;
	}

	public TbPagamento getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(TbPagamento selecionado) {
		this.selecionado = selecionado;
	}

}