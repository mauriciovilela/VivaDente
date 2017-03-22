package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.odonto.BLL.ProcedimentoBLL;
import com.odonto.constants.Constants;
import com.odonto.model.TbProcedimento;
import com.odonto.service.ProcedimentoService;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProcedimentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProcedimentoService procedimentoService;
	
	@Inject
	private ProcedimentoBLL procedimentoBLL;

	@Inject
	private HomeMB homeMB;
	
	private TbProcedimento procedimento;
	
	private String aniversario;
	
	private List<TbProcedimento> procedimentos;
	private Boolean panfleto;

	public ProcedimentoBean() {
		limpar();
	}
	
	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {

		}
	}
	
	private void limpar() {
		procedimento = new TbProcedimento();
		aniversario = StringUtils.EMPTY;
	}
	
	public void salvar() {
		this.procedimento = procedimentoService.salvar(this.procedimento);
		// Atualiza os dados na sessao
		homeMB.setProcedimentos(procedimentoBLL.listar());		
		limpar();		
		FacesUtil.addInfoMessage(Constants.msgSucesso);
	}

	public List<String> completeText(String query) {
		List<String> registros = procedimentoBLL.listarPorNome(query);
		return registros;
	}

	public TbProcedimento getProcedimento() {
		return procedimento;
	}
	
	public void setProcedimento(TbProcedimento Procedimento) {
		this.procedimento = Procedimento;	
	}

	public boolean isEditando() {
		return this.procedimento.getId() != null;
	}

	public String getAniversario() {
		return aniversario;
	}

	public void setAniversario(String aniversario) {
		this.aniversario = aniversario;
	}

	public List<TbProcedimento> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(List<TbProcedimento> procedimentos) {
		this.procedimentos = procedimentos;
	}

	public Boolean getPanfleto() {
		return panfleto;
	}

	public void setPanfleto(Boolean panfleto) {
		this.panfleto = panfleto;
	}

}
