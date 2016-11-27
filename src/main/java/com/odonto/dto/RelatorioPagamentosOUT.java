package com.odonto.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class RelatorioPagamentosOUT implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private BigDecimal valorTotal;
	private BigDecimal valorPago;
	private BigDecimal valorPendente;

	public RelatorioPagamentosOUT() {

	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorPago() {
		return valorPago;
	}

	public void setValorPago(BigDecimal valorPago) {
		this.valorPago = valorPago;
	}

	public BigDecimal getValorPendente() {
		return valorPendente;
	}

	public void setValorPendente(BigDecimal valorPendente) {
		this.valorPendente = valorPendente;
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
		RelatorioPagamentosOUT other = (RelatorioPagamentosOUT) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}