package com.odonto.util.jsf;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.StringUtils;

public class FacesUtil {
	
	public static String getQueryString(String param) {
		Map<String, String> hsr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if (hsr != null && hsr.get("idPaciente") != null) {
			return hsr.get("idPaciente").toString();
		}
		else {
			return StringUtils.EMPTY;
		}
	}

	public static boolean isPostback() {
		return FacesContext.getCurrentInstance().isPostback();
	}
	
	public static boolean isNotPostback() {
		return !isPostback();
	}
	
	public static void addErrorMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	}
	
	public static void addInfoMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
	}
	
	public static void addWarningMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
	}
}