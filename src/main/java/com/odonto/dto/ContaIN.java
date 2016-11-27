package com.odonto.dto;

import java.io.Serializable;
import java.util.Date;

public class ContaIN implements Serializable {

	private static final long serialVersionUID = 1L;

	private String descricao;
	private Date data;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
}