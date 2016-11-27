package com.odonto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "tb_usuario")
public class TbUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@NotNull
	@NotBlank
	@Column(name = "DS_NOME", length = 100)
	private String dsNome;

	@Column(name = "DS_SENHA", length = 50)
	private String dsSenha;

	@Column(name = "DS_EMAIL", length = 100)
	private String dsEmail;

	@Column(name = "DS_USUARIO", length = 15)
	private String dsUsuario;

	@Column(name = "DS_PERFIL", length = 15)
	private String dsPerfil;

	@Column(name = "FL_DENTISTA")
	private Boolean flDentista;

	@Column(name = "FL_SOCIO")
	private Boolean flSocio;

	@Column(name = "FL_CLIENTE")
	private Boolean flCliente;

	@Column(name = "FL_ATIVO")
	private Boolean flAtivo;

	public TbUsuario() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDsNome() {
		return this.dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsSenha() {
		return this.dsSenha;
	}

	public void setDsSenha(String dsSenha) {
		this.dsSenha = dsSenha;
	}

	public String getDsUsuario() {
		return this.dsUsuario;
	}

	public String getDsPerfil() {
		return dsPerfil;
	}

	public void setDsPerfil(String dsPerfil) {
		this.dsPerfil = dsPerfil;
	}

	public String getDsEmail() {
		return dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	public void setDsUsuario(String dsUsuario) {
		this.dsUsuario = dsUsuario;
	}

	public Boolean getFlDentista() {
		return this.flDentista;
	}

	public void setFlDentista(Boolean flDentista) {
		this.flDentista = flDentista;
	}

	public Boolean getFlSocio() {
		return flSocio;
	}

	public void setFlSocio(Boolean flSocio) {
		this.flSocio = flSocio;
	}

	public Boolean getFlAtivo() {
		return flAtivo;
	}

	public void setFlAtivo(Boolean flAtivo) {
		this.flAtivo = flAtivo;
	}

	public Boolean getFlCliente() {
		return flCliente;
	}

	public void setFlCliente(Boolean flCliente) {
		this.flCliente = flCliente;
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
		TbUsuario other = (TbUsuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}