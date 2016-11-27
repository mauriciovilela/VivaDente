package com.odonto.BLL;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.odonto.model.TbAuditoria;

public class AuditoriaBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbAuditoria porId(Integer id) {
		return manager.find(TbAuditoria.class, id);
	}

	public TbAuditoria guardar(TbAuditoria item) {
		return manager.merge(item);
	}
	
	public void adicionar(String mensagem) {
		TbAuditoria item = new TbAuditoria();
		item.setDsDescricao(mensagem);
		item.setDtInclusao(new Date());
		guardar(item);	
	}
	
}
