package com.odonto.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.LaboratorioBLL;
import com.odonto.dto.FiltroIN;
import com.odonto.model.TbLaboratorio;
import com.odonto.security.SessionContext;
import com.odonto.util.Util;

@Named
@ViewScoped
public class RptInadimplentesLabBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LaboratorioBLL laboratorioBLL;

	private List<TbLaboratorio> filtrados;
	
	private TbLaboratorio selecionado;

	@PostConstruct
	private void pageLoad() {
		setFiltrados(laboratorioBLL.inadimplentes());
	}
	
	public void modalHistoricoPagamento(Integer idCliente, String nomePaciente) {
		FiltroIN filtro = new FiltroIN();
		filtro.setCodigo(idCliente);
		filtro.setDescricao(nomePaciente);
		//		SessionContext.getInstance().setID(idCliente);
		SessionContext.getInstance().setFiltro(filtro);
		Util.abreModal("/restrita/laboratorio/LaboratorioHistoricoMD");
	}
	
	public BigDecimal valorRestante(TbLaboratorio item) {
		return item.getVlTotal().subtract(item.getVlPago());
	}
	
	public List<TbLaboratorio> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<TbLaboratorio> filtrados) {
		this.filtrados = filtrados;
	}

	public TbLaboratorio getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(TbLaboratorio selecionado) {
		this.selecionado = selecionado;
	}

}