package com.odonto.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.chart.PieChartModel;

import com.odonto.BLL.LaboratorioBLL;
import com.odonto.dto.MesesOUT;
import com.odonto.dto.RelatorioPagamentosOUT;
import com.odonto.util.Util;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class RptPagamentosLabBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private PieChartModel registros;

	@Inject
	private LaboratorioBLL laboratorioBLL;

	private List<MesesOUT> meses;
	private String mesSelecinado;
	private String dataSelecionada;

	@PostConstruct
	private void pageLoad() {
		if (FacesUtil.isNotPostback()) {

			String dtExtenso = StringUtils.EMPTY;
			SimpleDateFormat formatMesAno = new SimpleDateFormat("MMMM/Y");
			SimpleDateFormat formatData = new SimpleDateFormat("yyyy-MM-dd");

			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());

			MesesOUT mesSel = new MesesOUT();
			meses = new ArrayList<MesesOUT>();

			for (int i = 13; i != 0; i--) {
				dtExtenso = formatMesAno.format(cal.getTime());
				mesSel = new MesesOUT();
				mesSel.setData(formatData.format(cal.getTime()));
				mesSel.setMes(dtExtenso);
				meses.add(mesSel);
				cal.add(Calendar.MONTH, -1);
			}
		}
	}

	public void pesquisar() {
		if (dataSelecionada != null) {
			Date data = Util.stringToDate(dataSelecionada);
			RelatorioPagamentosOUT relatorio = laboratorioBLL.relatorioDespesasPorMes(data);

			if (relatorio.getValorTotal() != null) {
				registros = new PieChartModel();

				registros.set("Valor Total", relatorio.getValorTotal());
				registros.set("Valor Pago", relatorio.getValorPago());
				registros.set("Valor Dívida", relatorio.getValorPendente());

				registros.setShowDataLabels(true);
				registros.setDataFormat("value");
				registros.setDataLabelFormatString("%#.2f");
				registros.setLegendPosition("w");
			}
			else {
				registros = null;
			}
		}
		else {
			registros = null;
		}
	}

	public PieChartModel getRegistros() {
		return registros;
	}

	public void setRegistros(PieChartModel registros) {
		this.registros = registros;
	}

	public List<MesesOUT> getMeses() {
		return meses;
	}

	public void setMeses(List<MesesOUT> meses) {
		this.meses = meses;
	}


	public String getMesSelecinado() {
		return mesSelecinado;
	}

	public void setMesSelecinado(String mesSelecinado) {
		this.mesSelecinado = mesSelecinado;
	}

	public String getDataSelecionada() {
		return dataSelecionada;
	}

	public void setDataSelecionada(String dataSelecionada) {
		this.dataSelecionada = dataSelecionada;
	}

}