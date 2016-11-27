package com.odonto.BLL;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.odonto.model.TbAgendaStatus;

public class AgendaStatusBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbAgendaStatus porId(Integer id) {
		return manager.find(TbAgendaStatus.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<TbAgendaStatus> listar() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbAgendaStatus.class);
		return criteria.list();
	}
}
