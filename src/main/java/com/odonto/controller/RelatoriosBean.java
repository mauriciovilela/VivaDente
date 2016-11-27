package com.odonto.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import com.odonto.constants.Constants;
import com.odonto.dto.AcessosOUT;
import com.odonto.security.SessionContext;

@Named
@ViewScoped
public class RelatoriosBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<AcessosOUT> menuRelatorios;

	@PostConstruct
	private void pageLoad() {
		List<AcessosOUT> acessos = SessionContext.getInstance().getAcessos();
		menuRelatorios = new ArrayList<AcessosOUT>();
		for (AcessosOUT item : acessos) {
			if (item.getIdFuncionalidadePai() != null && item.getIdFuncionalidadePai().equals(Constants.CodRelatorio)) {
				menuRelatorios.add(item);
			}
		}
	}

	public List<AcessosOUT> getMenuRelatorios() {
		return menuRelatorios;
	}

	public void setMenuRelatorios(List<AcessosOUT> menuRelatorios) {
		this.menuRelatorios = menuRelatorios;
	}

}