package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.ProcedimentoBLL;
import com.odonto.dto.ProcedimentoIN;
import com.odonto.model.TbProcedimento;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProcedimentoBeanPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProcedimentoBLL procedimentoBLL;

	private ProcedimentoIN filtro;
	private List<TbProcedimento> filtrados;
	
	private TbProcedimento selecionado;

    @PostConstruct
	private void pageLoad() {
		if (FacesUtil.isNotPostback()) {
			filtro = new ProcedimentoIN();
			filtrados = procedimentoBLL.filtrados(filtro);
		}
	}
    
	public ProcedimentoBeanPesquisa() {
		filtro = new ProcedimentoIN();
	}
	
	public void excluir() {
		procedimentoBLL.remover(selecionado);
		filtrados.remove(selecionado);		
		FacesUtil.addInfoMessage("Registro excluído com sucesso.");
	}
	
	public void pesquisar() {
		filtrados = procedimentoBLL.filtrados(filtro);
	}

	public ProcedimentoIN getFiltro() {
		return filtro;
	}

	public List<TbProcedimento> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<TbProcedimento> filtrados) {
		this.filtrados = filtrados;
	}

	public TbProcedimento getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(TbProcedimento selecionado) {
		this.selecionado = selecionado;
	}

	public void setFiltro(ProcedimentoIN filtro) {
		this.filtro = filtro;
	}
	
}