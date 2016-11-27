package com.odonto.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.dto.AgendaOUT;
import com.odonto.dto.FiltroIN;
import com.odonto.security.SessionContext;
import com.odonto.service.AgendaService;

@Named
@ViewScoped
public class AgendaHistoricoMDBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AgendaService agendaService;
	
	private List<AgendaOUT> historico;
	
	private FiltroIN filtro;

	@PostConstruct
	private void pageLoad() {
		Integer idPaciente = SessionContext.getInstance().getID();
		historico = agendaService.listarPorPaciente(idPaciente);
	}

	public List<AgendaOUT> getHistorico() {
		return historico;
	}

	public void setHistorico(List<AgendaOUT> historico) {
		this.historico = historico;
	}

	public FiltroIN getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroIN filtro) {
		this.filtro = filtro;
	}
	
}

