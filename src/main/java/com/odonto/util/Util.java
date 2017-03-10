package com.odonto.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.odonto.util.jsf.FacesUtil;

public class Util {

	public static String md5Java(String message) { 
		String digest = null; 
		try { 
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			byte[] hash = md.digest(message.getBytes("UTF-8")); 
			//converting byte array to Hexadecimal String
			StringBuilder sb = new StringBuilder(2*hash.length); 
			for(byte b : hash){ 
				sb.append(String.format("%02x", b&0xff)); 
			} 
			digest = sb.toString(); } 
		catch (Exception ex) {
			
		}
		return digest; 
	}
	
	public static BigDecimal toBigDecimal(String valor) {
		valor = valor.replace(".", "");
		valor = valor.replace(",", ".");
		return new BigDecimal(valor);
	}
    
    public static Calendar dateToCalendar(Date date){ 
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
 
	@SuppressWarnings("rawtypes")
	public static void mensagemRegistros(List lista) {
		if (lista == null || lista.isEmpty()) {
			FacesUtil.addWarningMessage("Nenhum registro foi encontrado");			
		}
	}
	
	public static String getData(Date data){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String reportDate = df.format(data);
		return reportDate;
	}
	
	public static Date stringToDate(String dataSelecionada){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(dataSelecionada);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDiaMes(Date data){
		DateFormat df = new SimpleDateFormat("dd/MM");
		String reportDate = df.format(data);
		return reportDate;
	}
	
	public static String getHora(Date data){
		DateFormat df = new SimpleDateFormat("HH:mm");
		String reportDate = df.format(data);
		return reportDate;
	}
	
	public static Date getDataHora(Date data, boolean dataInicial) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		if (dataInicial) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
		}
		else {
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);			
		}
		return calendar.getTime();
	}
	
	public static Date getDiaMes(boolean primeiroDia) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		if (primeiroDia) {
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		else {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return calendar.getTime();
	}
	
	public static Date getDiaMes(Date data, boolean primeiroDia) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		if (primeiroDia) {
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		}
		else {
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return calendar.getTime();
	}
	
	public static void abreModal(String nomePaginaModal) {		
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("resizable", false);
        options.put("closeOnEscape", true);
        options.put("responsive", true);
        if (isMobile()) {
	        options.put("contentHeight", 300);
	        options.put("contentWidth", 335);
        }
		RequestContext.getCurrentInstance().openDialog(nomePaginaModal, options, null);
	}
	
	public static boolean isMobile() {		
		Map<String, String> headers = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap();
		return ( headers.get("user-agent").contains("iPhone") || 
				 headers.get("user-agent").contains("Android") );
	}
	
	public static void redirect(String page) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			String contextPath = externalContext.getRequestContextPath();
	
			externalContext.redirect(contextPath + page);
			facesContext.responseComplete();
		} catch (IOException e) {
			throw new FacesException(e);
		}
	}

	
}
