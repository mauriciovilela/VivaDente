package com.odonto.service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;

import com.odonto.BLL.LaboratorioBLL;
import com.odonto.BLL.LaboratorioHistoricoBLL;
import com.odonto.model.TbLaboratorio;
import com.odonto.util.jpa.Transactional;

public class LaboratorioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private LaboratorioBLL bll;

	@Inject
	private LaboratorioHistoricoBLL labHistBLL;
	
	@Transactional
	public TbLaboratorio salvar(TbLaboratorio item, BigDecimal vlPagoAtual) {
		item.setFlPago(false);
		TbLaboratorio pagamento = bll.guardar(item);		
		if (vlPagoAtual != null) {
			labHistBLL.guardar(pagamento, vlPagoAtual);			
		}
		// Verifica se finalizou os pagamentos
		if (verificaBaixa(item)) {
			item.setFlPago(true);
			bll.guardar(item);
		}
		return pagamento;			
	}
	
	private boolean verificaBaixa(TbLaboratorio pagamento) {
		BigDecimal totalPago = labHistBLL.somaPorPagamento(pagamento.getId());
		return (pagamento.getVlTotal().equals(totalPago));
	}
}
