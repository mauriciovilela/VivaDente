package com.odonto.dto;

import java.io.Serializable;

public class PacienteIN implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fone;
	private String nome;
	private String cpfRG;
	private Integer first;
	private Integer pageSize;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}
	public Integer getFirst() {
		return first;
	}
	public void setFirst(Integer first) {
		this.first = first;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getCpfRG() {
		return cpfRG;
	}
	public void setCpfRG(String cpfRG) {
		this.cpfRG = cpfRG;
	}
	
}