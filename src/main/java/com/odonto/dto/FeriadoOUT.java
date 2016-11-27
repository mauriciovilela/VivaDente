package com.odonto.dto;

public class FeriadoOUT {

	private String data;
	private String descricao;
	
	public FeriadoOUT(String data, String descricao) {
		this.data = data;
		this.descricao = descricao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
