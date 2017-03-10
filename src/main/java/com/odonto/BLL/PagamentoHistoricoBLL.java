package com.odonto.BLL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.odonto.model.TbPaciente;
import com.odonto.model.TbPagamento;
import com.odonto.model.TbPagamentoHistorico;
import com.odonto.security.SessionContext;
import com.odonto.service.NegocioException;
import com.odonto.util.jpa.Transactional;

public class PagamentoHistoricoBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbPagamentoHistorico porId(Integer id) {
		return manager.find(TbPagamentoHistorico.class, id);
	}

	public TbPagamentoHistorico guardar(TbPagamento item, BigDecimal vlPagoAtual) {
		TbPagamentoHistorico novoHistorico = new TbPagamentoHistorico();
		novoHistorico.setDsObservacao(item.getDsObservacao());
		novoHistorico.setDtEntrada(item.getDtEntrada());
		novoHistorico.setTbDentista(item.getTbDentista());
		novoHistorico.setTbPaciente(item.getTbPaciente());
		novoHistorico.setTbUsuario(item.getTbUsuario());
		novoHistorico.setVlPago(vlPagoAtual);
		novoHistorico.setVlTotal(item.getVlTotal());
		novoHistorico.setTbPagamento(item);
		novoHistorico.setIdFilial(SessionContext.getInstance().getIdFilial());
		return manager.merge(novoHistorico);
	}

	@SuppressWarnings("unchecked")
	public List<TbPagamentoHistorico> porPaciente(TbPaciente tbPaciente) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamentoHistorico.class);
		criteria.add(Restrictions.eq("idFilial", SessionContext.getInstance().getIdFilial()));
		if (tbPaciente != null) {
			criteria.add(Restrictions.eq("tbPaciente.id", tbPaciente.getId()));
		}
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbPagamentoHistorico> porPaciente(Integer idPaciente) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamentoHistorico.class);
		criteria.add(Restrictions.eq("idFilial", SessionContext.getInstance().getIdFilial()));
		criteria.add(Restrictions.eq("tbPaciente.id", idPaciente));
		criteria.addOrder(Order.asc("tbPagamento.id"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbPagamentoHistorico> porPagamento(Integer idPagamento) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamentoHistorico.class);
		criteria.add(Restrictions.eq("idFilial", SessionContext.getInstance().getIdFilial()));
		criteria.add(Restrictions.eq("tbPagamento.id", idPagamento));
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}

	public BigDecimal somaPorPagamento(Integer idPagamento) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamentoHistorico.class);
		criteria.setProjection(Projections.sum("vlPago"));
		criteria.add(Restrictions.eq("idFilial", SessionContext.getInstance().getIdFilial()));
		criteria.add(Restrictions.eq("tbPagamento.id", idPagamento));
		BigDecimal valorTotal = (BigDecimal) criteria.uniqueResult();
		return valorTotal;
	}

	@Transactional
	public void remover(TbPagamentoHistorico item) {
		try {
			item = porId(item.getId());
			manager.remove(item);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Registro não pode ser excluído.");
		}
	}
}
