package com.odonto.dto;

import java.io.Serializable;
import java.util.Date;

public class FiltroIN implements Serializable {

	private static final long serialVersionUID = 1L;

	private String descricao;
	private Integer codigo;
	private String outros;
	private Date data;
	int first;
	int pageSize;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOutros() {
		return outros;
	}

	public void setOutros(String outros) {
		this.outros = outros;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}