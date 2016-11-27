package com.odonto.dto;

public class ConfigOUT {

	private String mailHost;
	private int mailPort;
	private String mailUser;
	private String mailEmail;
	private String mailNome;
	private String mailPassword;
	private boolean mailEnvio;
	
	private String dbConexao;
	private String dbUsuario;
	private String dbSenha;
	private String caminhoTemplate;
	
	public String getMailHost() {
		return mailHost;
	}
	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}
	public int getMailPort() {
		return mailPort;
	}
	public void setMailPort(int mailPort) {
		this.mailPort = mailPort;
	}
	public String getMailUser() {
		return mailUser;
	}
	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}
	public String getMailPassword() {
		return mailPassword;
	}
	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}
	public String getDbConexao() {
		return dbConexao;
	}
	public void setDbConexao(String dbConexao) {
		this.dbConexao = dbConexao;
	}
	public String getDbUsuario() {
		return dbUsuario;
	}
	public void setDbUsuario(String dbUsuario) {
		this.dbUsuario = dbUsuario;
	}
	public String getDbSenha() {
		return dbSenha;
	}
	public void setDbSenha(String dbSenha) {
		this.dbSenha = dbSenha;
	}
	public boolean isMailEnvio() {
		return mailEnvio;
	}
	public void setMailEnvio(boolean mailEnvio) {
		this.mailEnvio = mailEnvio;
	}
	public String getMailEmail() {
		return mailEmail;
	}
	public void setMailEmail(String mailEmail) {
		this.mailEmail = mailEmail;
	}
	public String getMailNome() {
		return mailNome;
	}
	public void setMailNome(String mailNome) {
		this.mailNome = mailNome;
	}
	public String getCaminhoTemplate() {
		return caminhoTemplate;
	}
	public void setCaminhoTemplate(String caminhoTemplate) {
		this.caminhoTemplate = caminhoTemplate;
	}
	
}
