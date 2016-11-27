package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.LaboratorioHistoricoBLL;
import com.odonto.BLL.UsuarioBLL;
import com.odonto.dto.FiltroIN;
import com.odonto.model.TbLaboratorioHistorico;
import com.odonto.security.SessionContext;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class LaboratorioHistoricoMDBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private LaboratorioHistoricoBLL bllHistorico;
	
	@Inject
	private UsuarioBLL usuarioBLL;
	
	private String nome;
	
	private List<TbLaboratorioHistorico> historico;

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
			FiltroIN filtro = SessionContext.getInstance().getFiltro();
			setNome(usuarioBLL.porId(filtro.getCodigo()).getDsNome());
			historico = bllHistorico.porClientePaciente(filtro.getCodigo(), filtro.getDescricao());
		}
	}

	public List<TbLaboratorioHistorico> getHistorico() {
		return historico;
	}

	public void setHistorico(List<TbLaboratorioHistorico> historico) {
		this.historico = historico;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}

