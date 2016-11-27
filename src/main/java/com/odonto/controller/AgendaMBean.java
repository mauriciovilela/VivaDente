package com.odonto.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.odonto.BLL.AgendaBLL;
import com.odonto.BLL.UsuarioBLL;
import com.odonto.dto.AgendaOUT;
import com.odonto.dto.UsuarioOUT;
import com.odonto.service.NegocioException;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class AgendaMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataInicial;
	private Date dataFinal;

	@Inject
	private AgendaBLL agendaBLL;
	@Inject
	private UsuarioBLL usuarioBLL;
	
	private List<AgendaOUT> listaAgenda;
	
	private Integer idDentista;
	private String dsNomeDentista;
	
	private List<UsuarioOUT> dentistas;

	public AgendaMBean() {
	}

	@PostConstruct
	private void pageLoad() {
		if (FacesUtil.isNotPostback()) {
			Calendar data = Calendar.getInstance();
			dataInicial = data.getTime();
			data.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			dataFinal = data.getTime();
			setDentistas(usuarioBLL.listarDentitas());
		}
	}
	
	public String pesquisarPorDentista() {
		if (dataInicial == null || dataFinal == null) {
			throw new NegocioException("Preencha todos os campos");
		}
		setListaAgenda(agendaBLL.listarPorDentista(idDentista, dataInicial, dataFinal, false));
		dsNomeDentista = usuarioBLL.porId(idDentista).getDsNome();
		return "pm:pgConsulta";
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<AgendaOUT> getListaAgenda() {
		return listaAgenda;
	}

	public void setListaAgenda(List<AgendaOUT> listaAgenda) {
		this.listaAgenda = listaAgenda;
	}

	public Integer getIdDentista() {
		return idDentista;
	}

	public void setIdDentista(Integer idDentista) {
		this.idDentista = idDentista;
	}

	public List<UsuarioOUT> getDentistas() {
		return dentistas;
	}

	public void setDentistas(List<UsuarioOUT> dentistas) {
		this.dentistas = dentistas;
	}

	public String getDsNomeDentista() {
		return dsNomeDentista;
	}

	public void setDsNomeDentista(String dsNomeDentista) {
		this.dsNomeDentista = dsNomeDentista;
	}

}
