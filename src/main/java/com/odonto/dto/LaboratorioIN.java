package com.odonto.dto;

import java.io.Serializable;

public class LaboratorioIN implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String nomePaciente;
	private Integer idDentista;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public Integer getIdDentista() {
		return idDentista;
	}

	public void setIdDentista(Integer idDentista) {
		this.idDentista = idDentista;
	}

}