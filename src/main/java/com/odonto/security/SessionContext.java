package com.odonto.security;

import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.odonto.dto.AcessosOUT;
import com.odonto.dto.ConfigOUT;
import com.odonto.dto.FiltroIN;
import com.odonto.model.TbUsuario;

public class SessionContext {

	private static SessionContext instance;

	public static SessionContext getInstance() {
		if (instance == null) {
			instance = new SessionContext();
		}
		return instance;
	}

	private SessionContext() {

	}
	
	public Integer getCodigoDentista() {
		return (Integer) getAttribute("idDentista");
	}
	
	public void setCodigoDentista(Integer idDentista) {
		setAttribute("idDentista", idDentista);
	}
	
	public ConfigOUT getConfig() {
		return (ConfigOUT) getAttribute("configOUT");
	}
	
	public void setConfig(ConfigOUT conf) {
		setAttribute("configOUT", conf);
	}
	
	public FiltroIN getFiltro() {
		return (FiltroIN) getAttribute("filtroIN");
	}
	
	public void setFiltro(FiltroIN conf) {
		setAttribute("filtroIN", conf);
	}
	
	public ExternalContext currentExternalContext() {
		if (FacesContext.getCurrentInstance() == null) {
//			throw new RuntimeException("O FacesContext não pode ser chamado fora de uma requisição HTTP");
			return null;
		} else {
			return FacesContext.getCurrentInstance().getExternalContext();
		}
	}

	public TbUsuario getUsuarioLogado() {
		return (TbUsuario) getAttribute("usuarioLogado");
	}

	public String getPerfil() {
		return (String) getAttribute("perfil");
	}

	@SuppressWarnings("unchecked")
	public List<AcessosOUT> getAcessos() {
		return (List<AcessosOUT>) getAttribute("acessos");
	}

	@SuppressWarnings("unchecked")
	public List<AcessosOUT> getMenu() {
		return (List<AcessosOUT>) getAttribute("menu");
	}

	public Integer getID() {
		return (Integer) getAttribute("id");
	}

	public void setID(Integer id) {
		setAttribute("id", id);
	}

	public void encerrarSessao() {
		currentExternalContext().invalidateSession();
	}

	public Object getAttribute(String nome) {
		return currentExternalContext().getSessionMap().get(nome);
	}

	public void setAttribute(String nome, Object valor) {
		currentExternalContext().getSessionMap().remove(nome);
		currentExternalContext().getSessionMap().put(nome, valor);
	}

}