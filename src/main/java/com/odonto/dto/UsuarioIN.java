package com.odonto.dto;

import java.io.Serializable;

public class UsuarioIN implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String dsNome;
	private String dsSenha;
	private String dsEmail;
	private String dsUsuario;
	private byte flDentista;
	private byte flSocio;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDsNome() {
		return dsNome;
	}
	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}
	public String getDsSenha() {
		return dsSenha;
	}
	public void setDsSenha(String dsSenha) {
		this.dsSenha = dsSenha;
	}
	public String getDsEmail() {
		return dsEmail;
	}
	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}
	public String getDsUsuario() {
		return dsUsuario;
	}
	public void setDsUsuario(String dsUsuario) {
		this.dsUsuario = dsUsuario;
	}
	public byte getFlDentista() {
		return flDentista;
	}
	public void setFlDentista(byte flDentista) {
		this.flDentista = flDentista;
	}
	public byte getFlSocio() {
		return flSocio;
	}
	public void setFlSocio(byte flSocio) {
		this.flSocio = flSocio;
	}
	
}