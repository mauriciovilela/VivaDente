package com.odonto.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.deltaspike.scheduler.api.Scheduled;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.odonto.BLL.PacienteBLL;
import com.odonto.dto.ConfigOUT;
import com.odonto.model.TbPaciente;
import com.odonto.util.AppConfigProperties;
import com.odonto.util.Util;

@Scheduled(cronExpression = "0 0 18 * * ?")
public class JobInconsistencia implements org.quartz.Job {

	// 0 0 19 * * ? = as 19 horas
	// 0/10 * * * * ? = A cada 10 segundos

	@Inject
	private PacienteBLL pacienteBLL;

	private static Logger logger = Logger.getLogger(JobInconsistencia.class);

	private ConfigOUT config = null;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("## Verificando inconsistências nos pagamentos...");
		AppConfigProperties prop = new AppConfigProperties();
		config = prop.init();
		if (config.isMailEnvio()) {
			try {
				enviaEmail();
			} catch (EmailException e) {
				logger.error(e);
			}
		}
	}

	private void enviaEmail() throws EmailException {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 1);

		List<TbPaciente> inc1 = pacienteBLL.listarInconsistencias1();
		List<TbPaciente> inc2 = pacienteBLL.listarInconsistencias2();

		StringBuilder strTexto = new StringBuilder();

		for (TbPaciente item1 : inc1) {
			strTexto.append("<br>INC1 Nome: " + item1.getDsNome());
		}
		for (TbPaciente item2 : inc2) {
			strTexto.append("<br>INC2 Nome: " + item2.getDsNome());
		}
		enviarEmail(strTexto.toString(), calendar);
		if (strTexto.toString().length() > 0) {
			// Grava log de auditoria
			logger.info("## Problemas encontrados nos pagamentos... " + strTexto.toString());
		}
	}

	@SuppressWarnings("deprecation")
	private void enviarEmail(String texto, Calendar calendar) throws EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setHostName(config.getMailHost());
		email.setSmtpPort(config.getMailPort());
		email.addTo("mauriciovilela@gmail.com", "Mauricio Vilela");
		email.setFrom(config.getMailEmail(), config.getMailNome());
		// Adicione um assunto
		email.setSubject(config.getMailNome().concat(" Inconsistências ").concat(Util.getData(calendar.getTime())));
		// Adicione a mensagem do email
		email.setHtmlMsg(texto);
		// Para autenticar no servidor é necessário chamar os dois métodos
		// abaixo
		email.setSSL(true);
		email.setAuthentication(config.getMailUser(), config.getMailPassword());
		email.send();
	}

}
