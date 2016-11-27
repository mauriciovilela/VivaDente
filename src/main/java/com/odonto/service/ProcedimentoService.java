package com.odonto.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.odonto.BLL.ProcedimentoBLL;
import com.odonto.model.TbProcedimento;
import com.odonto.util.jpa.Transactional;

public class ProcedimentoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProcedimentoBLL bll;
	
	@Transactional
	public TbProcedimento salvar(TbProcedimento item) {	
		return bll.guardar(item);
	}
	
}
