package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.odonto.BLL.FeriadoBLL;
import com.odonto.BLL.LaboratorioBLL;
import com.odonto.BLL.ProcedimentoBLL;
import com.odonto.BLL.UsuarioBLL;
import com.odonto.constants.Constants;
import com.odonto.dto.AcessosOUT;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbFeriado;
import com.odonto.model.TbProcedimento;
import com.odonto.security.SessionContext;
import com.odonto.util.Util;
import com.odonto.util.jsf.FacesUtil;

@Named
@SessionScoped
public class HomeMB implements Serializable {    

	private static final long serialVersionUID = -8529695076388473379L;
	
	/**
	 * Combos que irao ficar na Sessao
	 */
	private static Logger logger = Logger.getLogger(HomeMB.class);

	@Inject
	private ProcedimentoBLL procedimentoBLL;
	private List<TbProcedimento> procedimentos;

	@Inject
	private UsuarioBLL usuarioBLL;
	
	@Inject
	private LaboratorioBLL laboratorioBLL;
	
	@Inject
	private FeriadoBLL feriadoBLL;
	
	private List<UsuarioOUT> usuarios;
	private List<UsuarioOUT> dentistas;
	private List<UsuarioOUT> socios;
	private List<AcessosOUT> acessosMenu;
	private List<TbFeriado> feriados;
	private List<UsuarioOUT> proteticos;
	private List<UsuarioOUT> pacientesLab;
	
	private boolean perfilDentista;
	private boolean perfilSocio;
	private boolean perfilSecretaria;
	
	private String nomeFilial;
	private Integer idFilial;

	@PostConstruct
	private void pageLoad() {
		if (FacesUtil.isNotPostback()) {
			inicializarCombos();
			// Monta o menu a esquerda, somente com itens de menu
			acessosMenu = SessionContext.getInstance().getMenu();
			String perfilLogado = SessionContext.getInstance().getUsuarioLogado().getDsPerfil();
			perfilDentista = perfilLogado.equals("DENTISTA");
			perfilSocio = perfilLogado.equals("SOCIO");
			perfilSecretaria = perfilLogado.equals("SECRETARIA");
			
			idFilial = SessionContext.getInstance().getIdFilial();
			setNomeFilial(idFilial.equals(1) ? "Centro" : "Tocantins");

			if (SessionContext.getInstance().getUsuarioLogado().getDsSenha().equals("698dc19d489c4e4db73e28a713eab07b")) {
				Util.redirect("/restrita/usuario/UsuarioTrocaSenha.xhtml");
			}
		}
	}

	public void inicializarCombos() {
		if (procedimentos == null) {
			logger.info("[LOG] Carregando todos os combos pra sessao ...");
			feriados = feriadoBLL.listar();
			setProcedimentos(procedimentoBLL.listar());
			setUsuarios(usuarioBLL.listar(Constants.ATIVOS));
			setDentistas(usuarioBLL.listarDentitas());
			setSocios(usuarioBLL.listarSocios());
			setProteticos(usuarioBLL.listarLaboratorio());
			setPacientesLab(laboratorioBLL.pacientesLab());
			logger.info("[LOG] Objetos carregados.");
		}
	}

	public void modalHistoricoMensagem() {
		Util.abreModal("/restrita/mensagem/MensagemHistoricoMD");
	}
	
	public void carregarPagina(String nomePagina) {
	 ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	 String contextPath = externalContext.getRequestContextPath();
	 try {
	        externalContext.redirect(contextPath + nomePagina + ".xhtml");
	     } catch (Exception e)
	       {
	           e.printStackTrace();
	       }
	}
	
	public List<TbProcedimento> getProcedimentos() {
		return procedimentos;
	}

	public void setProcedimentos(List<TbProcedimento> procedimentos) {
		this.procedimentos = procedimentos;
	}

	public List<UsuarioOUT> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioOUT> usuarios) {
		this.usuarios = usuarios;
	}

	public List<UsuarioOUT> getDentistas() {
		return dentistas;
	}

	public void setDentistas(List<UsuarioOUT> dentistas) {
		this.dentistas = dentistas;
	}

	public List<UsuarioOUT> getSocios() {
		return socios;
	}

	public void setSocios(List<UsuarioOUT> socios) {
		this.socios = socios;
	}

	public String getNomeUsuario() {
		return SessionContext.getInstance().getUsuarioLogado().getDsNome();
	}

	public List<AcessosOUT> getAcessosMenu() {
		return acessosMenu;
	}

	public void setAcessosMenu(List<AcessosOUT> acessosMenu) {
		this.acessosMenu = acessosMenu;
	}

	public boolean isPerfilDentista() {
		return perfilDentista;
	}

	public void setPerfilDentista(boolean perfilDentista) {
		this.perfilDentista = perfilDentista;
	}

	public boolean isPerfilSocio() {
		return perfilSocio;
	}

	public void setPerfilSocio(boolean perfilSocio) {
		this.perfilSocio = perfilSocio;
	}

	public boolean isPerfilSecretaria() {
		return perfilSecretaria;
	}

	public void setPerfilSecretaria(boolean perfilSecretaria) {
		this.perfilSecretaria = perfilSecretaria;
	}

	public List<TbFeriado> getFeriados() {
		return feriados;
	}

	public void setFeriados(List<TbFeriado> feriados) {
		this.feriados = feriados;
	}

	public List<UsuarioOUT> getProteticos() {
		return proteticos;
	}

	public void setProteticos(List<UsuarioOUT> proteticos) {
		this.proteticos = proteticos;
	}

	public List<UsuarioOUT> getPacientesLab() {
		return pacientesLab;
	}

	public void setPacientesLab(List<UsuarioOUT> pacientesLab) {
		this.pacientesLab = pacientesLab;
	}

	public String getNomeFilial() {
		return nomeFilial;
	}

	public void setNomeFilial(String nomeFilial) {
		this.nomeFilial = nomeFilial;
	}

	public Integer getIdFilial() {
		return idFilial;
	}

	public void setIdFilial(Integer idFilial) {
		this.idFilial = idFilial;
	}

}