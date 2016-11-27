package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.odonto.BLL.PacienteBLL;
import com.odonto.constants.Constants;
import com.odonto.model.TbPaciente;
import com.odonto.model.TbUsuario;
import com.odonto.security.SessionContext;
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

	public PacienteBean() {
		limpar();
	}
	
	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
			if (paciente.getId() == null) {
				paciente.setDsCidade("Uberlândia, MG");
			}
		}
	}
	
	private void limpar() {
		paciente = new TbPaciente();
		aniversario = StringUtils.EMPTY;
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

}
