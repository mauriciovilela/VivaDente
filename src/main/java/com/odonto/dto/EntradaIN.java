package com.odonto.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class EntradaIN implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotEmpty
	private String vlTotal;
	
	@NotNull
	private Date dtEntrada;

	private String vlParcela;
	
	private Integer qtParcelas;
	
	public String getVlTotal() {
		return vlTotal;
	}

	public void setVlTotal(String vlTotal) {
		this.vlTotal = vlTotal;
	}

	public Date getDtEntrada() {
		return dtEntrada;
	}

	public void setDtEntrada(Date dtEntrada) {
		this.dtEntrada = dtEntrada;
	}

	public String getVlParcela() {
		return vlParcela;
	}

	public void setVlParcela(String vlParcela) {
		this.vlParcela = vlParcela;
	}

	public Integer getQtParcelas() {
		return qtParcelas;
	}

	public void setQtParcelas(Integer qtParcelas) {
		this.qtParcelas = qtParcelas;
	}
	
}
