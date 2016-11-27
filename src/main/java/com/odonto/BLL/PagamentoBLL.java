package com.odonto.BLL;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.odonto.dto.RelatorioPagamentosOUT;
import com.odonto.model.TbPagamento;
import com.odonto.security.SessionContext;
import com.odonto.service.NegocioException;
import com.odonto.util.Util;
import com.odonto.util.jpa.Transactional;

public class PagamentoBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbPagamento porId(Integer id) {
		return manager.find(TbPagamento.class, id);
	}

	public TbPagamento guardar(TbPagamento item) {
		item.setTbUsuario(SessionContext.getInstance().getUsuarioLogado());
		return manager.merge(item);			
	}

	@SuppressWarnings("unchecked")
	public List<TbPagamento> porPaciente(Integer idPaciente) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamento.class);
		criteria.add(Restrictions.eq("tbPaciente.id", idPaciente));
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbPagamento> porPacienteDataValorPgtoDentista(
			Integer idPaciente, Date data, BigDecimal valorTotal, Integer idDentista) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamento.class);
		criteria.add(Restrictions.eq("tbPaciente.id", idPaciente));
		criteria.add(Restrictions.eq("tbDentista.id", idDentista));
		criteria.add(Restrictions.eq("dtEntrada", data));
		criteria.add(Restrictions.eq("vlTotal", valorTotal));
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TbPagamento> porData(Date data) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamento.class);
		criteria.add(Restrictions.between("dtEntrada", data, data));
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbPagamento> inadimplentes() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamento.class, "pg");
		criteria.createAlias("pg.tbPaciente", "pac");
		criteria.add(Restrictions.eq("flPago", false));
		criteria.addOrder(Order.asc("pac.dsNome"));
		return criteria.list();
	}

	public RelatorioPagamentosOUT relatorioDespesasPorMes(Date data) {
		
		RelatorioPagamentosOUT retorno = new RelatorioPagamentosOUT();
		Session session = manager.unwrap(Session.class);
		
		// Valor total
		Criteria criteria = session.createCriteria(TbPagamento.class);
		criteria.setProjection(Projections.sum("vlTotal"));
		criteria.add(Restrictions.between("dtEntrada", Util.getDiaMes(data, true), Util.getDiaMes(data, false)));
		BigDecimal valorTotal = (BigDecimal) criteria.uniqueResult();
		
		// Valor Pago
		criteria = session.createCriteria(TbPagamento.class);
		criteria.setProjection(Projections.sum("vlPago"));
		criteria.add(Restrictions.between("dtEntrada", Util.getDiaMes(data, true), Util.getDiaMes(data, false)));
		BigDecimal valorPago = (BigDecimal) criteria.uniqueResult();		
		
		retorno.setValorTotal(valorTotal);
		retorno.setValorPago(valorPago);
		if (valorTotal != null && valorPago != null) {			
			retorno.setValorPendente(valorTotal.subtract(valorPago));
		}
		
		return retorno;
	}
	
	public TbPagamento validaPendencia(Integer idPaciente) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbPagamento.class);
		criteria.add(Restrictions.eq("flPago", false));
		criteria.add(Restrictions.eq("tbPaciente.id", idPaciente));
		if (criteria.list().isEmpty()) {
			return null;
		}
		return (TbPagamento) criteria.list().get(0);
	}
	
	@Transactional
	public void remover(TbPagamento item) {
		try {
			item = porId(item.getId());
			manager.remove(item);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Registro não pode ser excluído.");
		}
	}
	
}
