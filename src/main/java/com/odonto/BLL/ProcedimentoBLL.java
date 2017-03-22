package com.odonto.BLL;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.odonto.dto.ProcedimentoIN;
import com.odonto.model.TbProcedimento;
import com.odonto.service.NegocioException;
import com.odonto.util.jpa.Transactional;

public class ProcedimentoBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbProcedimento porId(Integer id) {
		return manager.find(TbProcedimento.class, id);
	}

	public TbProcedimento guardar(TbProcedimento item) {
		return manager.merge(item);
	}
	
	@Transactional
	public void remover(TbProcedimento item) {
		try {
			item = porId(item.getId());
			manager.remove(item);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Registro não pode ser excluído.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<TbProcedimento> filtrados(ProcedimentoIN filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbProcedimento.class);
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("dsDescricao", filtro.getNome(), MatchMode.ANYWHERE));
		}
		criteria.addOrder(Order.asc("dsDescricao"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbProcedimento> listar() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbProcedimento.class);
		criteria.addOrder(Order.asc("dsDescricao"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> listarPorNome(String query) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbProcedimento.class);
		criteria.setProjection(Projections.groupProperty("dsDescricao"));
		criteria.add(Restrictions.ilike("dsDescricao", query, MatchMode.ANYWHERE));
		criteria.setMaxResults(10);
		criteria.addOrder(Order.asc("dsDescricao"));
		return criteria.list();
	}	
	
}
