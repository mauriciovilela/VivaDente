package com.odonto.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.odonto.BLL.PacienteBLL;
import com.odonto.model.TbPaciente;
import com.odonto.util.jpa.Transactional;

public class PacienteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PacienteBLL usuarios;
	
	@Transactional
	public TbPaciente salvar(TbPaciente item) {	
		return usuarios.guardar(item);
	}
	
}
