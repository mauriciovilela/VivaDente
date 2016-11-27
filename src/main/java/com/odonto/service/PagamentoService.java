package com.odonto.service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;

import com.odonto.BLL.PagamentoBLL;
import com.odonto.BLL.PagamentoHistoricoBLL;
import com.odonto.model.TbPagamento;
import com.odonto.util.jpa.Transactional;

public class PagamentoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PagamentoBLL bll;

	@Inject
	private PagamentoHistoricoBLL pgtoHistBLL;
	
	@Transactional
	public TbPagamento salvar(TbPagamento item, BigDecimal vlPagoAtual) {
		item.setFlPago(false);
		TbPagamento pagamento = bll.guardar(item);		
		if (vlPagoAtual != null) {
			pgtoHistBLL.guardar(pagamento, vlPagoAtual);			
		}
		// Verifica se finalizou os pagamentos
		if (verificaBaixa(item)) {
			item.setFlPago(true);
			bll.guardar(item);
		}
		return pagamento;			
	}
	
	private boolean verificaBaixa(TbPagamento pagamento) {
		BigDecimal totalPago = pgtoHistBLL.somaPorPagamento(pagamento.getId());
		return (pagamento.getVlTotal().equals(totalPago));
	}
	
}
