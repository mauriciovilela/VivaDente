package com.odonto.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.odonto.BLL.AuditoriaBLL;
import com.odonto.model.TbAuditoria;
import com.odonto.util.jpa.Transactional;

public class AuditoriaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AuditoriaBLL bll;
	
	@Transactional
	public TbAuditoria salvar(TbAuditoria item) {
		item.setDtInclusao(new Date());
		return bll.guardar(item);
	}
	
}
