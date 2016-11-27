package com.odonto.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tb_pagamento_historico")
public class TbPagamentoHistorico implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_ENTRADA")
	@NotNull
	private Date dtEntrada;

	@Column(name="VL_TOTAL")
	@NotNull
	private BigDecimal vlTotal;

	@Column(name="VL_PAGO")
	@NotNull
	private BigDecimal vlPago;

	@ManyToOne
	@JoinColumn(name="ID_PACIENTE")
	@NotNull
	private TbPaciente tbPaciente;
	
	@ManyToOne
	@JoinColumn(name="ID_DENTISTA")
	private TbUsuario tbDentista;

	@Column(name="DS_OBS", length=150)
	private String dsObservacao;
	
	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	private TbUsuario tbUsuario;

	@ManyToOne
	@JoinColumn(name="ID_PAGAMENTO")
	@NotNull
	private TbPagamento tbPagamento;
	
	public TbPagamentoHistorico() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDtEntrada() {
		return this.dtEntrada;
	}

	public void setDtEntrada(Date dtEntrada) {
		this.dtEntrada = dtEntrada;
	}

	public BigDecimal getVlTotal() {
		return this.vlTotal;
	}

	public void setVlTotal(BigDecimal vlTotal) {
		this.vlTotal = vlTotal;
	}

	public TbPaciente getTbPaciente() {
		return this.tbPaciente;
	}

	public void setTbPaciente(TbPaciente tbPaciente) {
		this.tbPaciente = tbPaciente;
	}

	public BigDecimal getVlPago() {
		return vlPago;
	}

	public void setVlPago(BigDecimal vlPago) {
		this.vlPago = vlPago;
	}

	public TbUsuario getTbDentista() {
		return tbDentista;
	}

	public void setTbDentista(TbUsuario tbDentista) {
		this.tbDentista = tbDentista;
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

	public TbPagamento getTbPagamento() {
		return tbPagamento;
	}

	public void setTbPagamento(TbPagamento tbPagamento) {
		this.tbPagamento = tbPagamento;
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
		TbPagamentoHistorico other = (TbPagamentoHistorico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}