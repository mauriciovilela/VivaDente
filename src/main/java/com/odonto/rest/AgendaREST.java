package com.odonto.rest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.odonto.BLL.AgendaBLL;
import com.odonto.dto.AgendaOUT;

@Path("/agenda")
public class AgendaREST {
	
	@Inject
	private AgendaBLL agendaBLL;

	@GET
	@Path("/dentista/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AgendaOUT> listarPorDentista(@PathParam("id") Integer id) {	
		
		Date dataInicial;
		Date dataFinal;
		
		// Calculando o perido da semana
		Calendar data = Calendar.getInstance();

		data.set(Calendar.HOUR_OF_DAY, 0);
		data.set(Calendar.MINUTE, 0);
		data.set(Calendar.SECOND, 0);
		dataInicial = data.getTime();
		
		// Resto da semana atual e proxima semana
		data.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		data.add(Calendar.DATE, 8);
		data.set(Calendar.HOUR_OF_DAY, 23);
		data.set(Calendar.MINUTE, 59);
		data.set(Calendar.SECOND, 59);
		dataFinal = data.getTime();
		
		// Realizando a consulta
		List<AgendaOUT> result = agendaBLL.listarPorDentista(id, dataInicial, dataFinal, false);
		
		// Formatando a data
		SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		for (AgendaOUT item : result) {
			item.setDtInicioSTR(sm.format(item.getDtInicio()));
			if (StringUtils.isNotBlank(item.getDsProcedimento()) && StringUtils.isNotBlank(item.getDsDescricao())) {
				item.setDsProcedimento(item.getDsProcedimento().concat(", " + item.getDsDescricao()));
			}
			else if (StringUtils.isNotBlank(item.getDsProcedimento())) {
				item.setDsProcedimento(item.getDsProcedimento());
			}
			else {
				item.setDsProcedimento(item.getDsDescricao());
			}
			item.setDtInicio(null);
			item.setDtFim(null);
		}
		
		return result;
	}
	
}
