package com.odonto.dto;

import java.io.Serializable;

public class FornecedorIN implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fone;
	private String nome;
	
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
	
}