package com.odonto.BLL;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.odonto.dto.AgendaOUT;
import com.odonto.model.TbAgendaHistorico;
import com.odonto.security.SessionContext;
import com.odonto.service.NegocioException;
import com.odonto.util.jpa.Transactional;

public class AgendaHistoricoBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbAgendaHistorico porId(Integer id) {
		return manager.find(TbAgendaHistorico.class, id);
	}

	public TbAgendaHistorico guardar(TbAgendaHistorico item) {
		item.setTbUsuario(SessionContext.getInstance().getUsuarioLogado());
		return manager.merge(item);
	}

	@SuppressWarnings("unchecked")
	public List<AgendaOUT> listarPorPaciente(Integer idPaciente) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbAgendaHistorico.class);
		criteria.createAlias("tbDentista", "D");
		criteria.createAlias("tbPaciente", "P");
		criteria.createAlias("tbProcedimento", "PR");
		criteria.createAlias("tbAgendaStatus", "AS");
		criteria.add(Restrictions.eq("idFilial", SessionContext.getInstance().getIdFilial()));
		criteria.add(Restrictions.eq("P.id", idPaciente));
		criteria.addOrder(Order.asc("D.dsNome"));
		criteria.addOrder(Order.asc("dtInicio"));
		criteria.setProjection( Projections.projectionList()
			    .add( Projections.property("id").as("id") )
			    .add( Projections.property("dtInicio").as("dtInicio") )
			    .add( Projections.property("dtFim").as("dtFim") )
			    .add( Projections.property("dtInclusao").as("dtInclusao") )
			    .add( Projections.property("dsDescricao").as("dsDescricao") )
			    .add( Projections.property("PR.dsDescricao").as("dsProcedimento") )
			    .add( Projections.property("AS.dsNome").as("dsAgendaStatus") )
			    .add( Projections.property("AS.id").as("idAgendaStatus") )
			    .add( Projections.property("P.dsNome").as("dsPaciente") )
				.add( Projections.property("D.dsNome").as("dsDentista") ));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AgendaOUT.class));			
		return criteria.list();
	}

	@Transactional
	public void remover(TbAgendaHistorico item) {
		try {
			item = porId(item.getId());
			manager.remove(item);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Registro não pode ser excluído.");
		}
	}
}
