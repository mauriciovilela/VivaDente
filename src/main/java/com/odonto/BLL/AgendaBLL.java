package com.odonto.BLL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.odonto.constants.Constants;
import com.odonto.dto.AgendaOUT;
import com.odonto.dto.FeriadoOUT;
import com.odonto.model.TbAgenda;
import com.odonto.security.SessionContext;
import com.odonto.service.NegocioException;
import com.odonto.util.Util;
import com.odonto.util.jpa.Transactional;

public class AgendaBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbAgenda porId(Integer id) {
		return manager.find(TbAgenda.class, id);
	}

	public TbAgenda guardar(TbAgenda item) {
		try {
			item.setTbUsuario(SessionContext.getInstance().getUsuarioLogado());
			return manager.merge(item);	
		} catch (PersistenceException e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				throw new NegocioException("Paciente já marcado para este horário.");
			}
			throw new NegocioException("Erro na manutenção.");
		}		
	}
	
	@SuppressWarnings("unchecked")
	public List<AgendaOUT> listarPorDentista(Integer idDentista, Date dataInicial, Date dataFinal, boolean horaExata) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbAgenda.class);
		criteria.createAlias("tbDentista", "D");
		criteria.createAlias("tbAgendaStatus", "AS");
		criteria.createAlias("tbPaciente", "P", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("tbProcedimento", "PR");
		criteria.add(Restrictions.eq("idFilial", SessionContext.getInstance().getIdFilial()));
		criteria.add(Restrictions.eq("D.id", idDentista));
		criteria.add(
			Restrictions.not(
				Restrictions.in("AS.id", new Integer[] {
						Constants.TbAgendaStatus.desmarcado})));
		if (horaExata) {
			criteria.add(Restrictions.between("dtInicio", dataInicial, dataFinal));			
		}
		else {
			criteria.add(Restrictions.between("dtInicio", 
					Util.getDataHora(dataInicial, true), 
					Util.getDataHora(dataFinal, false)));					
		}
		criteria.addOrder(Order.asc("dtInicio"));
		criteria.setProjection( Projections.projectionList()
			    .add( Projections.property("id").as("id") )
			    .add( Projections.property("dtInicio").as("dtInicio") )
			    .add( Projections.property("dtFim").as("dtFim") )
			    .add( Projections.property("AS.dsNome").as("dsAgendaStatus") )
			    .add( Projections.property("AS.id").as("idAgendaStatus") )
			    .add( Projections.property("dsDescricao").as("dsDescricao") )
			    .add( Projections.property("PR.dsDescricao").as("dsProcedimento") )
			    .add( Projections.property("P.dsNome").as("dsPaciente") ));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AgendaOUT.class));			
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AgendaOUT> listarPorDia(Date data) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbAgenda.class);
		criteria.createAlias("tbDentista", "D");
		criteria.createAlias("tbPaciente", "P");
		criteria.createAlias("tbProcedimento", "PR");
		criteria.add(
			Restrictions.not(
				Restrictions.in("tbAgendaStatus.id", new Integer[] {
						Constants.TbAgendaStatus.desmarcado, 
						Constants.TbAgendaStatus.falta})));
		criteria.add(Restrictions.between("dtInicio", 
				Util.getDataHora(data, true), 
				Util.getDataHora(data, false)));		
		criteria.addOrder(Order.asc("D.dsNome"));
		criteria.addOrder(Order.asc("dtInicio"));
		criteria.setProjection( Projections.projectionList()
			    .add( Projections.property("id").as("id") )
			    .add( Projections.property("dtInicio").as("dtInicio") )
			    .add( Projections.property("dtFim").as("dtFim") )
			    .add( Projections.property("dsDescricao").as("dsDescricao") )
			    .add( Projections.property("PR.dsDescricao").as("dsProcedimento") )
			    .add( Projections.property("P.dsNome").as("dsPaciente") )
				.add( Projections.property("D.dsNome").as("dsDentista") ));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AgendaOUT.class));			
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AgendaOUT> listarPorPaciente(Integer idPaciente) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbAgenda.class);
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
	
	@SuppressWarnings("unchecked")
	public List<AgendaOUT> listarPorStatus(Date dataInicial, Date dataFinal) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbAgenda.class);
		criteria.createAlias("tbAgendaStatus", "AS");
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("AS.dsNome"), "dsDescricao")
				.add(Projections.rowCount(), "total"));
		criteria.add(Restrictions.between("dtInicio", dataInicial, dataFinal));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AgendaOUT.class));			
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AgendaOUT> listarPorProcedimento(Date dataInicial, Date dataFinal) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbAgenda.class);
		criteria.createAlias("tbProcedimento", "P");
		criteria.add(Restrictions.eq("idFilial", SessionContext.getInstance().getIdFilial()));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("P.id"), "id")
				.add(Projections.groupProperty("P.dsDescricao"), "dsDescricao")
				.add(Projections.rowCount(), "total"));
		criteria.add(Restrictions.between("dtInicio", dataInicial, dataFinal));
		criteria.add(Restrictions.in("tbAgendaStatus.id", new Integer[] {
							Constants.TbAgendaStatus.marcado,
							Constants.TbAgendaStatus.remarcado,
							Constants.TbAgendaStatus.confirmado}));		
		criteria.addOrder(Order.desc("total"));
		criteria.setResultTransformer(new AliasToBeanResultTransformer(AgendaOUT.class));			
		return criteria.list();
	}
	
	public List<FeriadoOUT> listarFeriados(Date dataInicial, Date dataFinal) {
		List<FeriadoOUT> feriados = new ArrayList<FeriadoOUT>();
		feriados.add(new FeriadoOUT("01/01", "Ano Novo"));
		feriados.add(new FeriadoOUT("25/03", "Ano Novo"));
		feriados.add(new FeriadoOUT("01/01", "Ano Novo"));
		feriados.add(new FeriadoOUT("01/01", "Ano Novo"));
		feriados.add(new FeriadoOUT("01/01", "Ano Novo"));
		feriados.add(new FeriadoOUT("01/01", "Ano Novo"));
		return feriados;
	}
	
	@Transactional
	public void remover(TbAgenda item) {
		try {
			item = porId(item.getId());
			manager.remove(item);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Registro não pode ser excluído.");
		}
	}
}
