package com.odonto.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.chart.PieChartModel;

import com.odonto.BLL.AgendaBLL;
import com.odonto.dto.AgendaOUT;
import com.odonto.util.Util;

@Named
@ViewScoped
public class RptProcedimentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PieChartModel registrosConsolidado;
	private List<AgendaOUT> registros;

	@Inject
	private AgendaBLL agendaBLL;
	
	private Date dataIni;
	private Date dataFim;
	
	@PostConstruct
	private void pageLoad() {
		dataIni = Util.getDiaMes(true);
		dataFim = Util.getDiaMes(false);
	}

	public void pesquisar() {
		List<AgendaOUT> listaConsolidados = agendaBLL.listarPorProcedimento(dataIni, dataFim);
		registros = agendaBLL.listarPorProcedimento(dataIni, dataFim);
		
		registrosConsolidado = new PieChartModel();
		
		for (AgendaOUT item: listaConsolidados) {
			if (StringUtils.isEmpty(item.getDsDescricao())) {
				item.setDsDescricao("Outros Procedimentos");
			}			
			registrosConsolidado.set(item.getDsDescricao(), item.getTotal());
		}
		
		for (AgendaOUT item: registros) {
			if (StringUtils.isEmpty(item.getDsDescricao())) {
				item.setDsDescricao("Outros Procedimentos");
			}
		}

		registrosConsolidado.setShowDataLabels(true);
//		registros.setDataFormat("value");
		registrosConsolidado.setLegendPosition("w");
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public PieChartModel getRegistrosConsolidado() {
		return registrosConsolidado;
	}

	public void setRegistrosConsolidado(PieChartModel registrosConsolidado) {
		this.registrosConsolidado = registrosConsolidado;
	}

	public List<AgendaOUT> getRegistros() {
		return registros;
	}

	public void setRegistros(List<AgendaOUT> registros) {
		this.registros = registros;
	}
	
}