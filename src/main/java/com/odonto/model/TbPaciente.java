package com.odonto.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.NotBlank;

import com.odonto.util.Util;

@Entity
@Table(name = "tb_paciente", uniqueConstraints = @UniqueConstraint(columnNames = { "DS_NOME" }))
public class TbPaciente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@NotBlank
	@Column(name = "DS_NOME", length = 100)
	private String dsNome;

	@Column(name = "DS_EMAIL", length = 80)
	private String dsEmail;

	@Column(name = "NR_RG_CPF", length = 14)
	private String nrRgCpf;

	@Column(name = "DS_FONE", length = 16)
	private String dsFone;

	@Column(name = "DS_CELULAR", length = 16)
	private String dsCelular;

	@Column(name = "DS_ENDERECO", length = 150)
	private String dsEndereco;

	@Column(name = "DS_CIDADE", length = 60)
	private String dsCidade;

	@Column(name = "DS_OBS", length = 200)
	private String dsObservacao;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private TbUsuario tbUsuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_NASCIMENTO")
	private Date dtNascimento;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_CADASTRO")
	private Date dtCadastro;

	public TbPaciente() {
	}

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

	public String getDsEmail() {
		return dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	public String getNrRgCpf() {
		return nrRgCpf;
	}

	public void setNrRgCpf(String nrRgCpf) {
		this.nrRgCpf = nrRgCpf;
	}

	public String getDsFone() {
		return Util.retornaFone(dsFone);
	}

	public void setDsFone(String dsFone) {
		this.dsFone = dsFone;
	}

	public String getDsEndereco() {
		return dsEndereco;
	}

	public void setDsEndereco(String dsEndereco) {
		this.dsEndereco = dsEndereco;
	}

	public String getDsCidade() {
		return dsCidade;
	}

	public void setDsCidade(String dsCidade) {
		this.dsCidade = dsCidade;
	}

	public String getDsObservacao() {
		return dsObservacao;
	}

	public void setDsObservacao(String dsObservacao) {
		this.dsObservacao = dsObservacao;
	}

	public TbUsuario getTbUsuario() {
		return tbUsuario;
	}

	public void setTbUsuario(TbUsuario tbUsuario) {
		this.tbUsuario = tbUsuario;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public String getDsCelular() {
		return Util.retornaFone(dsCelular);
	}

	public void setDsCelular(String dsCelular) {
		this.dsCelular = dsCelular;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
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
		TbPaciente other = (TbPaciente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}