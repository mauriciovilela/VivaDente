package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.UsuarioBLL;
import com.odonto.constants.Constants;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbUsuario;
import com.odonto.service.UsuarioService;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;

	@Inject
	private UsuarioBLL usuarioBLL;
	
	@Inject
	private HomeMB homeMB;
	
	private TbUsuario usuario;

	private List<UsuarioOUT> usuarios;
	
	public UsuarioBean() {
		limpar();
	}
	
	public void inicializar(ComponentSystemEvent ev) {
		this.usuario.setFlAtivo(true);
	}
	
	private void limpar() {
		usuario = new TbUsuario();
	}
	
	public void salvar() {
		
		this.usuario.setDsPerfil(getPerfil());
		this.usuario = usuarioService.salvar(this.usuario);
		
		// Atualiza os dados na sessao
		homeMB.setUsuarios(usuarioBLL.listar(Constants.ATIVOS));
		homeMB.setDentistas(usuarioBLL.listarDentitas());
		homeMB.setSocios(usuarioBLL.listarSocios());
		homeMB.setProteticos(usuarioBLL.listarLaboratorio());	
		
		limpar();		
		
		FacesUtil.addInfoMessage(Constants.msgSucesso);
	}
	
	private String getPerfil() {
		if (this.usuario.getFlSocio()) {
			return "SOCIO";
		}
		else if (this.usuario.getFlDentista()) {
			return "DENTISTA";
		}
		else if (this.usuario.getFlCliente()) {
			return "CLIENTE";
		}
		else {
			return "SECRETARIA";
		}
	}
	
	public TbUsuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(TbUsuario Usuario) {
		this.usuario = Usuario;	
	}

	public boolean isEditando() {
		return this.usuario.getId() != null;
	}

	public List<UsuarioOUT> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioOUT> usuarios) {
		this.usuarios = usuarios;
	}

}
