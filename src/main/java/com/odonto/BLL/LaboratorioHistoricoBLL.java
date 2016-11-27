package com.odonto.BLL;

import java.io.Serializable;
import java.math.BigDecimal;
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
import com.odonto.model.TbLaboratorio;
import com.odonto.model.TbLaboratorioHistorico;
import com.odonto.service.NegocioException;
import com.odonto.util.jpa.Transactional;

public class LaboratorioHistoricoBLL implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public TbLaboratorioHistorico porId(Integer id) {
		return manager.find(TbLaboratorioHistorico.class, id);
	}

	public TbLaboratorioHistorico guardar(TbLaboratorio item, BigDecimal vlPagoAtual) {
		TbLaboratorioHistorico novoHistorico = new TbLaboratorioHistorico();
		novoHistorico.setDtEntrada(item.getDtEntrada());
		novoHistorico.setNomePaciente(item.getNomePaciente());
		novoHistorico.setDsDescricao(item.getDsDescricao());
		novoHistorico.setDsServico(item.getDsServico());
		novoHistorico.setTbDentista(item.getTbDentista());
		novoHistorico.setTbUsuario(item.getTbUsuario());
		novoHistorico.setVlPago(vlPagoAtual);
		novoHistorico.setVlTotal(item.getVlTotal());
		novoHistorico.setTbLaboratorio(item);
		return manager.merge(novoHistorico);
	}

	@SuppressWarnings("unchecked")
	public List<TbLaboratorioHistorico> filtrados(LaboratorioIN filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbLaboratorioHistorico.class);
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("dsDescricao", filtro.getNome(), MatchMode.ANYWHERE));
		}
		if (filtro.getIdDentista() != null) {
			criteria.add(Restrictions.eq("tbDentista.id", filtro.getIdDentista()));
		}
		if (StringUtils.isNotBlank(filtro.getNomePaciente())) {
			criteria.add(Restrictions.eq("nomePaciente", filtro.getNomePaciente()));
		}
		criteria.addOrder(Order.asc("dsDescricao"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbLaboratorioHistorico> porLaboratorio(Integer idLaboratorio) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbLaboratorioHistorico.class);
		criteria.add(Restrictions.eq("tbLaboratorio.id", idLaboratorio));
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TbLaboratorioHistorico> porClientePaciente(Integer idCliente, String nomePaciente) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbLaboratorioHistorico.class);
		criteria.add(Restrictions.eq("tbDentista.id", idCliente));
		criteria.add(Restrictions.eq("nomePaciente", nomePaciente));
		criteria.addOrder(Order.asc("tbDentista.id"));
		return criteria.list();
	}
	
	public BigDecimal somaPorPagamento(Integer idPagamento) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(TbLaboratorioHistorico.class);
		criteria.setProjection(Projections.sum("vlPago"));
		criteria.add(Restrictions.eq("tbLaboratorio.id", idPagamento));
		BigDecimal valorTotal = (BigDecimal) criteria.uniqueResult();
		return valorTotal;
	}
	
	@Transactional
	public void remover(TbLaboratorioHistorico item) {
		try {
			item = porId(item.getId());
			manager.remove(item);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Registro não pode ser excluído.");
		}
	}
}
