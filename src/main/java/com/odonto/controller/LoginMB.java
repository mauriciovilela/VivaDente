package com.odonto.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.odonto.BLL.FuncionalidadePerfilBLL;
import com.odonto.BLL.UsuarioBLL;
import com.odonto.constants.Constants;
import com.odonto.dto.AcessosOUT;
import com.odonto.model.TbUsuario;
import com.odonto.security.SessionContext;
import com.odonto.util.AppConfigProperties;
import com.odonto.util.Util;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class LoginMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(LoginMB.class);

	private String email;
	
	@NotNull
	private String login;
	
	@NotNull
	private String senha;
	
	@Inject
	private UsuarioBLL usuarioBLL;
	
	@Inject
	private FuncionalidadePerfilBLL funcionalidadePerfilBLL;
	
	private TbUsuario usuarioLogado;
	
	private Integer idFilial;
	
	public LoginMB() {
		// Carrega o arquivo de configuração para a memória
		AppConfigProperties prop = new AppConfigProperties();
		prop.init();
		idFilial = Constants.TbFilial.centro;
	}
	
	public String realizarLogin() {
		try {
			if (StringUtils.isBlank(login) || StringUtils.isBlank(senha)) {
				FacesUtil.addErrorMessage("Login e Senha devem ser informados.");
				return StringUtils.EMPTY;
			}
			logger.info("[LOG] Tentando logar com usuário " + login);
			
			senha = Util.md5Java(senha);
			usuarioLogado = usuarioBLL.porLogin(login, senha);
			
			if (usuarioLogado == null) {
				FacesUtil.addErrorMessage("Login ou Senha inválidos, tente novamente.");
				FacesContext.getCurrentInstance().validationFailed();
				return StringUtils.EMPTY;
			}
			else {
				FacesUtil.addInfoMessage("Login realizado com sucesso !");
				SessionContext.getInstance().setIdFilial(idFilial);
				// Adiciona os dados do usuario logado na sessao
				SessionContext.getInstance().setAttribute("usuarioLogado", usuarioLogado);
				// Carrega os dados do Menu
				consultarMenu();
				return "/aberta/Home.xhtml?faces-redirect=true";
			}
			
		} catch (Exception e) {
			logger.error(e);
			FacesUtil.addErrorMessage("Ocorreu um ERRO: " + e.getMessage());
			FacesContext.getCurrentInstance().validationFailed();
			e.printStackTrace();
			return StringUtils.EMPTY;
		}

	}
	
	/*
	 * Carrega o menu dinamicamente (Banco de dados)
	 */
    private void consultarMenu() {

    	List<AcessosOUT> acessos = null;

    	String perfil = SessionContext.getInstance().getUsuarioLogado().getDsPerfil();
    	SessionContext.getInstance().setAttribute("perfil", perfil);
    	
	    acessos = funcionalidadePerfilBLL.listarFuncionalidadesPorPerfil(perfil);
	    if (acessos.size() > 0) {
		    List<AcessosOUT> acessosMenu = new ArrayList<AcessosOUT>();
			for (AcessosOUT item : acessos) {
				if (item.getVisivel() == 1) {
					acessosMenu.add(item);
				}
			}
			// Todas as funcionalidades do sistema
		    SessionContext.getInstance().setAttribute("acessos", acessos);
		    // Todas as funcionalidades do menu
		    SessionContext.getInstance().setAttribute("menu", acessosMenu);
	    }
    }	
    
//    public MenuModel getMenuModel() {
//    	return (MenuModel) SessionContext.getInstance().getAttribute("menuModel");
//    }
    
	public String doLogout() {
		//logger.info("Fazendo logout com usuário " + SessionContext.getInstance().getUsuarioLogado().getDsUsuario());
		SessionContext.getInstance().encerrarSessao();
		return "/aberta/Login.xhtml?faces-redirect=true";
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TbUsuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(TbUsuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Integer getIdFilial() {
		return idFilial;
	}

	public void setIdFilial(Integer idFilial) {
		this.idFilial = idFilial;
	}

}