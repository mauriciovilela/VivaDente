package com.odonto.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FechCaixaOUT implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private BigDecimal vlMovimentado;
	private BigDecimal vlDebito;
	private BigDecimal vlCredito;
	private BigDecimal vlDinheiro;
	private BigDecimal vlCheque;
	private BigDecimal vlDespesa;
	private BigDecimal vlLiquido;
	private Date dtInicio;
	private Date dtFim;

	public FechCaixaOUT() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getVlMovimentado() {
		return vlMovimentado;
	}

	public void setVlMovimentado(BigDecimal vlMovimentado) {
		this.vlMovimentado = vlMovimentado;
	}

	public BigDecimal getVlDebito() {
		return vlDebito;
	}

	public void setVlDebito(BigDecimal vlDebito) {
		this.vlDebito = vlDebito;
	}

	public BigDecimal getVlCredito() {
		return vlCredito;
	}

	public void setVlCredito(BigDecimal vlCredito) {
		this.vlCredito = vlCredito;
	}

	public BigDecimal getVlDinheiro() {
		return vlDinheiro;
	}

	public void setVlDinheiro(BigDecimal vlDinheiro) {
		this.vlDinheiro = vlDinheiro;
	}

	public BigDecimal getVlCheque() {
		return vlCheque;
	}

	public void setVlCheque(BigDecimal vlCheque) {
		this.vlCheque = vlCheque;
	}

	public BigDecimal getVlDespesa() {
		return vlDespesa;
	}

	public void setVlDespesa(BigDecimal vlDespesa) {
		this.vlDespesa = vlDespesa;
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
		FechCaixaOUT other = (FechCaixaOUT) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public BigDecimal getVlLiquido() {
		return vlLiquido;
	}

	public void setVlLiquido(BigDecimal vlLiquido) {
		this.vlLiquido = vlLiquido;
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Date getDtFim() {
		return dtFim;
	}

	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}

}