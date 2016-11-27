package com.odonto.dto;

import java.io.Serializable;
import java.util.Date;

public class AgendaOUT implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date dtInicio;
	private Date dtFim;
	private Date dtInclusao;
	private String dtInicioSTR;
	private String dsAgendaStatus;
	private Integer idAgendaStatus;
	private String dsDescricao;
	private String dsProcedimento;
	private String dsPaciente;
	private String dsDentista;
	private Long total;

	public AgendaOUT() {
		
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Date getDtInicio() {
		return dtInicio;
	}


	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}


	public Date getDtFim() {
		return dtFim;
	}


	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}


	public String getDsAgendaStatus() {
		return dsAgendaStatus;
	}


	public void setDsAgendaStatus(String dsAgendaStatus) {
		this.dsAgendaStatus = dsAgendaStatus;
	}


	public Integer getIdAgendaStatus() {
		return idAgendaStatus;
	}


	public void setIdAgendaStatus(Integer idAgendaStatus) {
		this.idAgendaStatus = idAgendaStatus;
	}


	public String getDsDescricao() {
		return dsDescricao;
	}


	public void setDsDescricao(String dsDescricao) {
		this.dsDescricao = dsDescricao;
	}


	public String getDsProcedimento() {
		return dsProcedimento;
	}


	public void setDsProcedimento(String dsProcedimento) {
		this.dsProcedimento = dsProcedimento;
	}


	public String getDsPaciente() {
		return dsPaciente;
	}


	public void setDsPaciente(String dsPaciente) {
		this.dsPaciente = dsPaciente;
	}

	public String getDtInicioSTR() {
		return dtInicioSTR;
	}

	public void setDtInicioSTR(String dtInicioSTR) {
		this.dtInicioSTR = dtInicioSTR;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgendaOUT other = (AgendaOUT) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getDsDentista() {
		return dsDentista;
	}

	public void setDsDentista(String dsDentista) {
		this.dsDentista = dsDentista;
	}

	public Date getDtInclusao() {
		return dtInclusao;
	}

	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}