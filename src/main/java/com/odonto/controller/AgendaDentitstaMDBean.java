package com.odonto.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.AgendaBLL;
import com.odonto.dto.AgendaOUT;
import com.odonto.dto.FiltroIN;
import com.odonto.security.SessionContext;

@Named
@ViewScoped
public class AgendaDentitstaMDBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AgendaBLL agendaBLL;
	
	private List<AgendaOUT> registros;
	
	private FiltroIN filtro;

	@PostConstruct
	private void pageLoad() {
		
		Integer idDentista = SessionContext.getInstance().getFiltro().getCodigo();
		Date data = SessionContext.getInstance().getFiltro().getData();
		
		registros = agendaBLL.listarPorDentista(idDentista, data, data, false);
	}

	public FiltroIN getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroIN filtro) {
		this.filtro = filtro;
	}

	public List<AgendaOUT> getRegistros() {
		return registros;
	}

	public void setRegistros(List<AgendaOUT> registros) {
		this.registros = registros;
	}
	
}

