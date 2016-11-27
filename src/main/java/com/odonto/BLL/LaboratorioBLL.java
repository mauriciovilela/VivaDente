package com.odonto.BLL;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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

import com.odonto.dto.LaboratorioIN;
import com.odonto.dto.RelatorioPagamentosOUT;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbLaboratorio;
import com.odonto.service.NegocioException;
import com.odonto.util.Util;
import com.odonto.util.jpa.Transactional;

public class LaboratorioBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbLaboratorio porId(Integer id) {
		return manager.find(TbLaboratorio.class, id);
	}

	public TbLaboratorio guardar(TbLaboratorio item) {
		return manager.merge(item);
	}
	
	@Transactional
	public void remover(TbLaboratorio item) {
		try {
			item = porId(item.getId());
			manager.remove(item);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Registro não pode ser excluído.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<TbLaboratorio> filtrados(LaboratorioIN filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbLaboratorio.class);
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("dsDescricao", filtro.getNome(), MatchMode.ANYWHERE));
		}
		if (filtro.getIdDentista() != null) {
			criteria.add(Restrictions.eq("tbDentista.id", filtro.getIdDentista()));
		}
		if (StringUtils.isNotBlank(filtro.getNomePaciente())) {
			criteria.add(Restrictions.ilike("nomePaciente", filtro.getNomePaciente(), MatchMode.ANYWHERE));
		}
		criteria.addOrder(Order.asc("dsDescricao"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbLaboratorio> listar() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbLaboratorio.class);
		criteria.addOrder(Order.asc("dsDescricao"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<String> listarPorServico(String query) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbLaboratorio.class);
		criteria.setProjection(Projections.groupProperty("dsServico"));
		criteria.add(Restrictions.ilike("dsServico", query, MatchMode.ANYWHERE));
		criteria.setMaxResults(10);
		criteria.addOrder(Order.asc("dsServico"));
		return criteria.list();
	}

	public TbLaboratorio validaPendencia(Integer idDentista, String nome) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbLaboratorio.class);
		criteria.add(Restrictions.eq("flPago", false));
		criteria.add(Restrictions.eq("tbDentista.id", idDentista));
		criteria.add(Restrictions.eq("nomePaciente", nome));
		if (criteria.list().isEmpty()) {
			return null;
		}
		return (TbLaboratorio) criteria.list().get(0);
	}

	@SuppressWarnings("unchecked")
	public List<TbLaboratorio> inadimplentes() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbLaboratorio.class, "pg");
		criteria.createAlias("pg.tbDentista", "d");
		criteria.add(Restrictions.eq("flPago", false));
		criteria.addOrder(Order.asc("d.dsNome"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioOUT> pacientesLab() {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbLaboratorio.class);
		criteria.setProjection(Projections.groupProperty("nomePaciente"));
		//criteria.setMaxResults(10);
		criteria.addOrder(Order.asc("nomePaciente"));
		return criteria.list();
	}

	public RelatorioPagamentosOUT relatorioDespesasPorMes(Date data) {
		
		RelatorioPagamentosOUT retorno = new RelatorioPagamentosOUT();
		Session session = manager.unwrap(Session.class);
		
		// Valor total
		Criteria criteria = session.createCriteria(TbLaboratorio.class);
		criteria.setProjection(Projections.sum("vlTotal"));
		criteria.add(Restrictions.between("dtEntrada", Util.getDiaMes(data, true), Util.getDiaMes(data, false)));
		BigDecimal valorTotal = (BigDecimal) criteria.uniqueResult();
		
		// Valor Pago
		criteria = session.createCriteria(TbLaboratorio.class);
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
	
}
