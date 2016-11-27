package com.odonto.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.odonto.controller.LoginMB;
import com.odonto.dto.AcessosOUT;
import com.odonto.model.TbUsuario;

public class LoginFilter implements Filter {

	private static Logger logger = Logger.getLogger(LoginMB.class);

	/**
	 * A cada requisição entra neste filtro
	 */
    public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {
        TbUsuario user = null;
        HttpServletRequest reqServ = ((HttpServletRequest) request);
        HttpSession sess = reqServ.getSession(false);
        
		if (sess != null){
			user = (TbUsuario) sess.getAttribute("usuarioLogado");
		}      
		
		HttpServletResponse respServ = ((HttpServletResponse) response);
		// Caso a sessão expirou
		if (user == null) {
			if (isAjaxRequest(reqServ)) {
				respServ.getWriter().print(xmlPartialRedirectToPage(reqServ, "/error"));  
				respServ.flushBuffer();
			} 
			else {
				respServ.sendRedirect(reqServ.getContextPath() + "/aberta/Login.xhtml");				
			}
		} else {
			// Se for requisição ajax não valida se o usuário tem acesso
			if (isAjaxRequest(reqServ)) {
				chain.doFilter(request, response);
			}
			else {
				// Valida o acesso do usuário na página
				if (acessoPermitido(reqServ.getServletPath(), sess)) {
					chain.doFilter(request, response);				
				}
				else {
					respServ.sendRedirect(reqServ.getContextPath() + "/aberta/AcessoNegado.xhtml");
				}
			}
		}

    }
    
    /**
     * Valida o acesso do usuário na página
     * @param pagina = Nome pagina
     * @param sess = Sessão
     */
    @SuppressWarnings("unchecked")
	private boolean acessoPermitido(String pagina, HttpSession sess) {
    	int posPagina = pagina.indexOf(".xhtml");
    	boolean acessoOK = false;
    	if (posPagina > 0) {
        	pagina = pagina.substring(0, posPagina);
        	List<AcessosOUT> acessos = (List<AcessosOUT>) sess.getAttribute("acessos");
        	if (acessos != null && !acessos.isEmpty()) {
            	for (AcessosOUT acesso : acessos) {
            		if (acesso.getDsPagina().contains(pagina)) {
            			acessoOK = true;
            			break;
            		}
            	}    		
        	}
        	if (!acessoOK) {
        		StringBuilder strLog = new StringBuilder();
        		strLog.append("[LOG] Acesso negado. Tentando acessar página ");
        		strLog.append(pagina);
        		strLog.append(" Usuário: ");
        		TbUsuario usuario = (TbUsuario) sess.getAttribute("usuarioLogado");
        		strLog.append(usuario.getDsUsuario());
        		logger.info(strLog.toString());
        	}
    	}
    	return acessoOK;
    }
    
    /**
     * Verifica se é requisição Ajax
     * @param reqServ = Requisição
     */
    private boolean isAjaxRequest(HttpServletRequest reqServ) {
    	boolean ajaxRequest = reqServ.getHeader("faces-request") != null
                && reqServ.getHeader("faces-request").toLowerCase().indexOf("ajax") > -1;
        return ajaxRequest;
    }
    
    private String xmlPartialRedirectToPage(HttpServletRequest request, String page) {  
    	StringBuilder sb = new StringBuilder();  
    	sb.append("<?xml version='1.0' encoding='UTF-8'?>");  
    	sb.append("<partial-response><redirect url=\"").append(request.getContextPath()).append(request.getServletPath()).append(page).append("\"/></partial-response>");  
    	return sb.toString();  
    }
    
    public void init(FilterConfig arg0) throws ServletException {
    	// TODO Auto-generated method stub
    }

    public void destroy() {
    	// TODO Auto-generated method stub
    }

}