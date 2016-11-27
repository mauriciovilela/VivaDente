package com.odonto.BLL;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.odonto.constants.Msgs;
import com.odonto.dto.AcessosOUT;
import com.odonto.model.TbFuncionalidadePerfil;
import com.odonto.service.NegocioException;
import com.odonto.util.jpa.Transactional;

public class FuncionalidadePerfilBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

//	public TbFuncionalidadePerfil porId(Short id) {
//		return manager.find(TbFuncionalidadePerfil.class, id);
//	}

	@SuppressWarnings("unchecked")
	public List<AcessosOUT> listarFuncionalidadesPorPerfil(String perfil) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbFuncionalidadePerfil.class);
		criteria.createAlias("tbFuncionalidade", "F");
		criteria.addOrder(Order.asc("F.nrOrdem"));
		criteria.add(Restrictions.eq("perfil", perfil));
		criteria.setProjection( Projections.projectionList()
		    .add( Projections.property("F.id").as("idFuncionalidade") )
		    .add( Projections.property("F.idPai").as("idFuncionalidadePai") )
		    .add( Projections.property("F.dsNome").as("dsFuncionalidade") )
		    .add( Projections.property("F.dsPagina").as("dsPagina") )
		    .add( Projections.property("F.flVisivel").as("visivel") )
		    .add( Projections.property("F.dsIcone").as("dsIcone") ));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AcessosOUT.class));			
		return criteria.list();
	}

	public TbFuncionalidadePerfil guardar(TbFuncionalidadePerfil item) {
		return manager.merge(item);
	}
	
	@Transactional
	public void removerPorUsuario(Integer idUsuario) {
		try {
			Session session = manager.unwrap(Session.class);
			String hql = "delete from TbFuncionalidadePerfil where tbUsuario.id = :idUsuario";  
			Query qrDelete = session.createQuery(hql).setInteger("idUsuario", idUsuario);
			qrDelete.executeUpdate();  
		} catch (PersistenceException e) {
			throw new NegocioException(Msgs.MSG_08);
		}
	}
}
