package com.odonto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="tb_funcionalidade")
public class TbFuncionalidade implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Short id;

	@NotNull
	@NotBlank
	@Column(name="DS_FUNCIONALIDADE", length=50)
	private String dsNome;

	@Column(name="DS_PAGINA", length=50)
	private String dsPagina;

	@Column(name="DS_PAGINA_EDICAO", length=50)
	private String dsPaginaEdicao;
	
	@NotNull
	@Column(name="NR_ORDEM")
	private Short nrOrdem;
	
	@Column(name="ID_PAI")
	private Short idPai;
	
	@NotNull
	@Column(name="FL_VISIVEL")
	private Short flVisivel;

	@Column(name="DS_ICONE", length=30)
	private String dsIcone;
	
	public TbFuncionalidade() {
		
	}
	
	public TbFuncionalidade(Short id) {
		this.id = id;
	}

	public Short getId() {
		return this.id;
	}
	
	public void setId(Short id) {
		this.id = id;
	}

	public String getDsNome() {
		return dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsPagina() {
		return dsPagina;
	}

	public void setDsPagina(String dsPagina) {
		this.dsPagina = dsPagina;
	}

	public String getDsPaginaEdicao() {
		return dsPaginaEdicao;
	}

	public void setDsPaginaEdicao(String dsPaginaEdicao) {
		this.dsPaginaEdicao = dsPaginaEdicao;
	}

	public Short getNrOrdem() {
		return nrOrdem;
	}

	public void setNrOrdem(Short nrOrdem) {
		this.nrOrdem = nrOrdem;
	}

	public Short getFlVisivel() {
		return flVisivel;
	}

	public void setFlVisivel(Short flVisivel) {
		this.flVisivel = flVisivel;
	}

	public String getDsIcone() {
		return dsIcone;
	}

	public void setDsIcone(String dsIcone) {
		this.dsIcone = dsIcone;
	}

	public Short getIdPai() {
		return idPai;
	}

	public void setIdPai(Short idPai) {
		this.idPai = idPai;
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
		TbFuncionalidade other = (TbFuncionalidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}