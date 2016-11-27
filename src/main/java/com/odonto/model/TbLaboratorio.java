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
@Table(name = "tb_laboratorio")
public class TbLaboratorio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "DS_PACIENTE", length = 100)
	private String nomePaciente;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_DENTISTA")
	private TbUsuario tbDentista;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_ENTRADA")
	@NotNull
	private Date dtEntrada;

	@Column(name = "VL_TOTAL")
	@NotNull
	private BigDecimal vlTotal;

	@Column(name = "VL_PAGO")
	@NotNull
	private BigDecimal vlPago;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private TbUsuario tbUsuario;

	@Column(name = "DS_SERVICO", length = 100)
	private String dsServico;

	@Column(name = "DS_OBS", length = 150)
	private String dsDescricao;

	@Column(name = "FL_PAGO")
	private Boolean flPago;

	public TbLaboratorio() {
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

	public TbUsuario getTbUsuario() {
		return tbUsuario;
	}

	public void setTbUsuario(TbUsuario tbUsuario) {
		this.tbUsuario = tbUsuario;
	}

	public String getDsDescricao() {
		return dsDescricao;
	}

	public void setDsDescricao(String dsDescricao) {
		this.dsDescricao = dsDescricao;
	}

	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public String getDsServico() {
		return dsServico;
	}

	public void setDsServico(String dsServico) {
		this.dsServico = dsServico;
	}

	public Boolean getFlPago() {
		return flPago;
	}

	public void setFlPago(Boolean flPago) {
		this.flPago = flPago;
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
		TbLaboratorio other = (TbLaboratorio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}