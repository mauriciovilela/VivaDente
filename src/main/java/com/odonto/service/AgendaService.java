package com.odonto.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.odonto.BLL.AgendaBLL;
import com.odonto.BLL.AgendaHistoricoBLL;
import com.odonto.constants.Constants;
import com.odonto.dto.AgendaOUT;
import com.odonto.model.TbAgenda;
import com.odonto.model.TbAgendaHistorico;
import com.odonto.model.TbAgendaStatus;
import com.odonto.model.TbProcedimento;
import com.odonto.security.SessionContext;
import com.odonto.util.jpa.Transactional;

public class AgendaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AgendaBLL bll;

	@Inject
	private AgendaHistoricoBLL bllHistorico;

	@Transactional
	public void excluir(TbAgenda agenda) {
		bll.remover(agenda);
	}

	@Transactional
	public TbAgenda salvar(TbAgenda item) {

		boolean houveAgenda = (item.getId() != null);
		
		Date dtInicioAntigo = null;
		Date dtFimAntigo = null;
		TbAgendaStatus statusAntigo = null;
		TbProcedimento procedimentoAntigo = null;
		String descricaoAntiga = null;
		
		if (houveAgenda) {
			TbAgenda antigo = bll.porId(item.getId());
			dtInicioAntigo = antigo.getDtInicio();
			dtFimAntigo = antigo.getDtFim();
			statusAntigo = antigo.getTbAgendaStatus();
			procedimentoAntigo = antigo.getTbProcedimento();
			descricaoAntiga = antigo.getDsDescricao();
		}

		item.setDtInclusao(new Date());
		item.setIdFilial(SessionContext.getInstance().getIdFilial());

		TbAgenda novo = bll.guardar(item);

		boolean remarcado = novo.getTbAgendaStatus().getId().equals(Constants.TbAgendaStatus.remarcado);
		boolean confirmado = novo.getTbAgendaStatus().getId().equals(Constants.TbAgendaStatus.confirmado);
		boolean falta = novo.getTbAgendaStatus().getId().equals(Constants.TbAgendaStatus.falta);
		if (houveAgenda && (remarcado || confirmado || falta)) {
			TbAgendaHistorico itemHistorico = new TbAgendaHistorico();
			itemHistorico.setDtInicio(dtInicioAntigo);
			itemHistorico.setDtFim(dtFimAntigo);
			itemHistorico.setTbAgendaStatus(statusAntigo);
			itemHistorico.setTbProcedimento(procedimentoAntigo);
			itemHistorico.setDsDescricao(descricaoAntiga);
			itemHistorico.setTbPaciente(novo.getTbPaciente());
			itemHistorico.setTbDentista(novo.getTbDentista());
			itemHistorico.setDtInclusao(new Date());
			bllHistorico.guardar(itemHistorico);
		}

		return novo;
	}

	public List<AgendaOUT> listarPorPaciente(Integer idPaciente) {

		List<AgendaOUT> agenda = bll.listarPorPaciente(idPaciente);
		List<AgendaOUT> agendaHist = bllHistorico.listarPorPaciente(idPaciente);

		List<AgendaOUT> retorno = new ArrayList<AgendaOUT>();
		retorno.addAll(agenda);
		retorno.addAll(agendaHist);

		// Ordenacao
		Collections.sort(retorno, new Comparator<AgendaOUT>() {
			@Override
			public int compare(AgendaOUT item2, AgendaOUT item1) {
				return item1.getDtInclusao().compareTo(item2.getDtInclusao());
			}
		});

		return retorno;
	}

}
