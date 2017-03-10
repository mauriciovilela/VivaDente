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

@Entity
@Table(name="tb_agenda" , uniqueConstraints=@UniqueConstraint(columnNames={"ID_PACIENTE", "DT_INICIO", "DT_FIM", "ID_AGENDA_STATUS"}) )
public class TbAgenda implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name="DS_DESCRICAO", length = 100)
	private String dsDescricao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INICIO")
	private Date dtInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_FIM")
	private Date dtFim;

	@ManyToOne
	@JoinColumn(name="ID_PACIENTE")
	private TbPaciente tbPaciente;

	@ManyToOne
	@JoinColumn(name="ID_PROCEDIMENTO")
	private TbProcedimento tbProcedimento;
	
	@ManyToOne
	@JoinColumn(name="ID_DENTISTA")
	private TbUsuario tbDentista;
	
	@ManyToOne
	@JoinColumn(name="ID_AGENDA_STATUS")
	private TbAgendaStatus tbAgendaStatus;
	
	@ManyToOne
	@JoinColumn(name="ID_USUARIO")
	private TbUsuario tbUsuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INCLUSAO")
	private Date dtInclusao;
	
	@Column(name="ID_FILIAL")
	private Integer idFilial;
	
	public TbAgenda() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TbPaciente getTbPaciente() {
		return this.tbPaciente;
	}

	public void setTbPaciente(TbPaciente tbPaciente) {
		this.tbPaciente = tbPaciente;
	}

	public TbProcedimento getTbProcedimento() {
		return tbProcedimento;
	}

	public void setTbProcedimento(TbProcedimento tbProcedimento) {
		this.tbProcedimento = tbProcedimento;
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

	public String getDsDescricao() {
		return dsDescricao;
	}

	public void setDsDescricao(String dsDescricao) {
		this.dsDescricao = dsDescricao;
	}

	public TbUsuario getTbDentista() {
		return tbDentista;
	}

	public void setTbDentista(TbUsuario tbDentista) {
		this.tbDentista = tbDentista;
	}

	public TbAgendaStatus getTbAgendaStatus() {
		return tbAgendaStatus;
	}

	public void setTbAgendaStatus(TbAgendaStatus tbAgendaStatus) {
		this.tbAgendaStatus = tbAgendaStatus;
	}

	public TbUsuario getTbUsuario() {
		return tbUsuario;
	}

	public void setTbUsuario(TbUsuario tbUsuario) {
		this.tbUsuario = tbUsuario;
	}

	public Date getDtInclusao() {
		return dtInclusao;
	}

	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}

	public Integer getIdFilial() {
		return idFilial;
	}

	public void setIdFilial(Integer idFilial) {
		this.idFilial = idFilial;
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
		TbAgenda other = (TbAgenda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}