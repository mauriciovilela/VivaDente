package com.odonto.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="tb_procedimento")
public class TbProcedimento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@NotBlank
	@Column(name="DS_DESCRICAO")
	private String dsDescricao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_CADASTRO")
	private Date dtCompra;
	
	public TbProcedimento() {
	}
	
	public TbProcedimento(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDsDescricao() {
		return this.dsDescricao;
	}

	public void setDsDescricao(String dsDescricao) {
		this.dsDescricao = dsDescricao;
	}

	public Date getDtCompra() {
		return this.dtCompra;
	}

	public void setDtCompra(Date dtCompra) {
		this.dtCompra = dtCompra;
	}

}