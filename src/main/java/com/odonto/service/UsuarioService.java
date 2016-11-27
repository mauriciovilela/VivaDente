package com.odonto.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.odonto.BLL.UsuarioBLL;
import com.odonto.model.TbUsuario;
import com.odonto.util.Util;
import com.odonto.util.jpa.Transactional;

public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioBLL usuarios;
	
	@Transactional
	public TbUsuario salvar(TbUsuario item) {	
		if (item.getDsUsuario() != null) {
			item.setDsSenha(Util.md5Java("teste"));
		}
		return usuarios.guardar(item);
	}
	
}
