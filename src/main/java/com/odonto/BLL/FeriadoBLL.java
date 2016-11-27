package com.odonto.BLL;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.odonto.model.TbFeriado;

public class FeriadoBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbFeriado porId(Integer id) {
		return manager.find(TbFeriado.class, id);
	}

	public TbFeriado guardar(List<TbFeriado> lista) {
		for (TbFeriado item : lista) {
			manager.merge(item);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<TbFeriado> listar() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbFeriado.class);
		return criteria.list();
	}
}
