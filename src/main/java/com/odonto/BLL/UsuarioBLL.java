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

import com.odonto.constants.Constants;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbUsuario;

public class UsuarioBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbUsuario guardar(TbUsuario item) {
		return manager.merge(item);
	}
	
	public TbUsuario porId(Integer id) {
		return manager.find(TbUsuario.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioOUT> listar(Boolean status) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbUsuario.class);
		criteria.setProjection( Projections.projectionList()
			    .add( Projections.property("id").as("id") )
			    .add( Projections.property("dsEmail").as("dsEmail") )
			    .add( Projections.property("dsUsuario").as("dsUsuario") )
			    .add( Projections.property("flSocio").as("flSocio") )
			    .add( Projections.property("flDentista").as("flDentista") )
			    .add( Projections.property("flAtivo").as("flAtivo") )
			    .add( Projections.property("flCliente").as("flCliente") )
			    .add( Projections.property("dsNome").as("dsNome") ));
		if (status != null) {
			criteria.add(Restrictions.eq("flAtivo", status));
		}
		criteria.addOrder(Order.asc("dsNome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioOUT.class));			
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioOUT> listar(List<String> filtros) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbUsuario.class);
		criteria.setProjection( Projections.projectionList()
			    .add( Projections.property("id").as("id") )
			    .add( Projections.property("dsEmail").as("dsEmail") )
			    .add( Projections.property("dsUsuario").as("dsUsuario") )
			    .add( Projections.property("flSocio").as("flSocio") )
			    .add( Projections.property("flDentista").as("flDentista") )
			    .add( Projections.property("flAtivo").as("flAtivo") )
			    .add( Projections.property("flCliente").as("flCliente") )
			    .add( Projections.property("dsNome").as("dsNome") ));
		for (String campo : filtros) {
			criteria.add(Restrictions.eq(campo, Constants.SIM));
		}
		criteria.addOrder(Order.asc("dsNome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioOUT.class));			
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioOUT> listarDentitas() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbUsuario.class);
		criteria.add(Restrictions.eq("flDentista", Constants.SIM));
		criteria.add(Restrictions.eq("flAtivo", Constants.SIM));
		criteria.setProjection( Projections.projectionList()
			    .add( Projections.property("id").as("id") )
			    .add( Projections.property("dsNome").as("dsNome") )
			    .add( Projections.property("flAtivo").as("flAtivo") )
			    .add( Projections.property("dsEmail").as("dsEmail") ));
		criteria.addOrder(Order.asc("dsNome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioOUT.class));			
		return criteria.list();
	}	

	@SuppressWarnings("unchecked")
	public List<UsuarioOUT> listarSocios() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbUsuario.class);
		criteria.add(Restrictions.eq("flSocio", Constants.SIM));
		criteria.add(Restrictions.eq("flAtivo", Constants.SIM));
		criteria.setProjection( Projections.projectionList()
			    .add( Projections.property("id").as("id") )
			    .add( Projections.property("dsNome").as("dsNome") ));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioOUT.class));			
		return criteria.list();
	}	

	@SuppressWarnings("unchecked")
	public List<UsuarioOUT> listarLaboratorio() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbUsuario.class);
		criteria.add(Restrictions.eq("flCliente", Constants.SIM));
		criteria.setProjection( Projections.projectionList()
			    .add( Projections.property("id").as("id") )
			    .add( Projections.property("dsNome").as("dsNome") ));
		criteria.addOrder(Order.asc("dsNome"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(UsuarioOUT.class));			
		return criteria.list();
	}	

	public TbUsuario porLogin(String login, String senha) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbUsuario.class);
		criteria.add(Restrictions.eq("dsUsuario", login));
		criteria.add(Restrictions.eq("dsSenha", senha));
		if (!criteria.list().isEmpty()) {
			return (TbUsuario) criteria.list().get(0);
		}
		return null;
	}	
	
}
