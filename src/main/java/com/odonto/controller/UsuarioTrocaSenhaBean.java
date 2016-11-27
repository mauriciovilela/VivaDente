package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.validator.constraints.NotBlank;

import com.odonto.BLL.UsuarioBLL;
import com.odonto.constants.Constants;
import com.odonto.constants.Msgs;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbUsuario;
import com.odonto.security.SessionContext;
import com.odonto.service.NegocioException;
import com.odonto.service.UsuarioService;
import com.odonto.util.Util;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class UsuarioTrocaSenhaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private UsuarioBLL usuarioBLL;

	private TbUsuario usuario;

	@NotBlank
	private String senha;
	@NotBlank
	private String senhaConf;
	
	private List<UsuarioOUT> usuarios;

	public UsuarioTrocaSenhaBean() {
		limpar();
	}
	
	public void inicializar(ComponentSystemEvent ev) {
		if (FacesUtil.isNotPostback()) {
			usuario = SessionContext.getInstance().getUsuarioLogado();
	        // dentistas
	        setUsuarios(usuarioBLL.listar(Constants.ATIVOS));
		}
	}
	
	private void limpar() {
		usuario = new TbUsuario();
	}
	
	public void salvar() {
		if (!senha.equals(senhaConf)) {
			throw new NegocioException(Msgs.MSG_12);
		}
		this.usuario.setDsSenha(Util.md5Java(senhaConf));
		this.usuario = usuarioService.salvar(this.usuario);
		limpar();		
		FacesUtil.addInfoMessage(Constants.msgSucesso);
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenhaConf() {
		return senhaConf;
	}

	public void setSenhaConf(String senhaConf) {
		this.senhaConf = senhaConf;
	}

	public List<UsuarioOUT> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioOUT> usuarios) {
		this.usuarios = usuarios;
	}

}
