package com.odonto.BLL;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.odonto.dto.AcessosOUT;
import com.odonto.model.TbFuncionalidade;

public class FuncionalidadeBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbFuncionalidade porId(Short id) {
		return manager.find(TbFuncionalidade.class, id);
	}

	public TbFuncionalidade porNome(String dsNome) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbFuncionalidade.class);
		criteria.add(Restrictions.eq("dsNome", dsNome));
		return (TbFuncionalidade) criteria.list().get(0);
	}

	@SuppressWarnings("unchecked")
	public List<AcessosOUT> listarModulosFuncionalidades() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbFuncionalidade.class);
		criteria.createAlias("tbFuncionalidadePai", "M");
		criteria.addOrder(Order.asc("M.nrOrdem"));
		criteria.addOrder(Order.asc("nrOrdem"));
		criteria.setProjection( Projections.projectionList()
			    .add( Projections.property("id").as("idFuncionalidade") )
			    .add( Projections.property("dsNome").as("dsFuncionalidade") )
			    .add( Projections.property("M.dsNome").as("dsModulo") ));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AcessosOUT.class));			
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TbFuncionalidade> listarModulos() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbFuncionalidade.class);
		criteria.add(Restrictions.isNull("tbFuncionalidadePai.id"));
		criteria.addOrder(Order.asc("nrOrdem"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbFuncionalidade> listarPorModulos(Short idModulo) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbFuncionalidade.class);
		criteria.add(Restrictions.eq("tbFuncionalidadePai.id", idModulo));
		criteria.addOrder(Order.asc("nrOrdem"));
		return criteria.list();
	}
	
	public TbFuncionalidade guardar(TbFuncionalidade item) {
		return manager.merge(item);
	}
	
}
