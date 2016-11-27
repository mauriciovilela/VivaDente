package com.odonto.jobs;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.deltaspike.scheduler.api.Scheduled;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.odonto.BLL.AgendaBLL;
import com.odonto.BLL.UsuarioBLL;
import com.odonto.dto.AgendaOUT;
import com.odonto.dto.ConfigOUT;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbAuditoria;
import com.odonto.service.AuditoriaService;
import com.odonto.util.AppConfigProperties;
import com.odonto.util.Util;

@Scheduled(cronExpression = "0 0 18 * * ?")
public class JobAgenda implements org.quartz.Job {
	
	// 0 0 19 * * ? = as 19 horas
	// 0/10 * * * * ? = A cada 10 segundos

	@Inject
	private UsuarioBLL usuarioBLL;

	@Inject
	private AgendaBLL agendaBLL;

	@Inject
	private AuditoriaService auditoriaService;

	private static Logger logger = Logger.getLogger(JobAgenda.class);

	private ConfigOUT config = null;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		AppConfigProperties prop = new AppConfigProperties();
		config = prop.init();
		if (config.isMailEnvio()) {
			enviaEmail();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void enviaEmail() {

		VelocityEngine ve = new VelocityEngine();
		Properties velocityProp = new Properties();
		velocityProp.setProperty("file.resource.loader.path", config.getCaminhoTemplate());
		velocityProp.setProperty("input.encoding", "UTF-8");
		velocityProp.setProperty("output.encoding", "UTF-8");
		ve.init(velocityProp);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 1);

		List<UsuarioOUT> dentistas = usuarioBLL.listarDentitas();
		StringBuilder strDentista = new StringBuilder();
		StringBuilder strProcedimentos = null;
		boolean emailEnviado = false;

		// Todos os dentistas
		for (UsuarioOUT dentista : dentistas) {
			
			// Verifica se tem e-mail
			if (StringUtils.isNotBlank(dentista.getDsEmail())) {
				
				strDentista.append(dentista.getDsEmail() + ", ");
				// Lista a agenda do proximo dia do Dentista
				List<AgendaOUT> agendaDentista = agendaBLL.listarPorDentista(
						dentista.getId(), calendar.getTime(), calendar.getTime(), false);
				
				// Caso existe agenda neste dia
				if (agendaDentista.size() > 0) {

					ArrayList mapAgendas = new ArrayList();
					Map map = new HashMap();
					VelocityContext context = new VelocityContext();
					context.put("data", Util.getData(calendar.getTime()));
					context.put("dentista", dentista.getDsNome());
					context.put("totalPacientes", agendaDentista.size());
					context.put("bordas", "border-right: 1px solid #D5D5D5;border-bottom: 1px solid #D5D5D5;");
					
					for (AgendaOUT agenda : agendaDentista) {
						map = new HashMap();
						map.put("paciente", agenda.getDsPaciente());
						strProcedimentos = new StringBuilder();
						if (StringUtils.isNotBlank(agenda.getDsProcedimento())) {
							strProcedimentos.append(agenda.getDsProcedimento());
						}
						if (StringUtils.isNotBlank(agenda.getDsDescricao())) {
							strProcedimentos.append(" ** ".concat(agenda.getDsDescricao()));
						}
						map.put("procedimento", strProcedimentos.toString());
						map.put("horario", Util.getHora(agenda.getDtInicio()) + " - " + Util.getHora(agenda.getDtFim()));
						mapAgendas.add(map);
					}
					context.put("agendas", mapAgendas);

					Template t = ve.getTemplate("TAgenda.html");
					StringWriter writer = new StringWriter();
					t.merge(context, writer);

					try {
						enviarEmail(dentista, writer.toString(), calendar);
						emailEnviado = true;
					} catch (EmailException e) {
						logger.error(e);
					}
				}
			}
		}
		if (emailEnviado) {
			// Grava log de auditoria
			TbAuditoria audit = new TbAuditoria();
			String mensagem = "E-mail enviado com a agenda para: " + strDentista.toString();
			audit.setDsDescricao(mensagem);
			auditoriaService.salvar(audit);
			logger.info(mensagem);
		}
	}

	@SuppressWarnings("deprecation")
	private void enviarEmail(UsuarioOUT dentista, String assunto, Calendar calendar) throws EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setHostName(config.getMailHost());
		email.setSmtpPort(config.getMailPort());
		email.addTo(dentista.getDsEmail(), dentista.getDsNome());
		email.setFrom(config.getMailEmail(), config.getMailNome());
		// Adicione um assunto
		email.setSubject(config.getMailNome().concat(" Agenda ").concat(Util.getData(calendar.getTime())));
		// Adicione a mensagem do email
		email.setHtmlMsg(assunto);
		// Para autenticar no servidor é necessário chamar os dois métodos
		// abaixo
		email.setSSL(true);
		email.setAuthentication(config.getMailUser(), config.getMailPassword());
		email.send();
	}

}
