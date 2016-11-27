package com.odonto.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import com.odonto.dto.ConfigOUT;
import com.odonto.security.SessionContext;

public class AppConfigProperties {

	private Properties properties;

	@PostConstruct
	public ConfigOUT init() {
		this.properties = new Properties();
		try {
			
			String tomcatDir = System.getProperty("catalina.base");
			if (tomcatDir.contains("\\")) {
				tomcatDir = tomcatDir.replace("\\", "/");
			}
			final InputStream stream = new FileInputStream(tomcatDir + "/VivaDente.properties");
			this.properties.load(stream);
			
			// Dados do arquivo de configuração
			ConfigOUT config = new ConfigOUT();
			config.setMailHost(this.properties.getProperty("mail.server.host"));
			config.setMailPort(Integer.parseInt(this.properties.getProperty("mail.server.port")));
			config.setMailEnvio(Boolean.parseBoolean(this.properties.getProperty("mail.envio")));
			config.setMailUser(this.properties.getProperty("mail.username"));
			config.setMailEmail(this.properties.getProperty("mail.email"));
			config.setMailNome(this.properties.getProperty("mail.nome"));
			config.setMailPassword(this.properties.getProperty("mail.password"));
			config.setDbConexao(this.properties.getProperty("db.conexao"));
			config.setDbUsuario(this.properties.getProperty("db.usuario"));
			config.setDbSenha(this.properties.getProperty("db.senha"));
			config.setCaminhoTemplate(this.properties.getProperty("caminho.template"));
			
			// Adiciona na Sessao as configuracoes
			if (SessionContext.getInstance().currentExternalContext() != null) {
				SessionContext.getInstance().setConfig(config);
			}
			
			return config;

		} catch (final IOException e) {
			throw new RuntimeException("Configuration could not be loaded!");
		}
	}

}