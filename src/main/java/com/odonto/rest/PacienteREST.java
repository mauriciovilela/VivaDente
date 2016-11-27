package com.odonto.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;

import com.odonto.BLL.UsuarioBLL;
import com.odonto.constants.Constants;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbUsuario;

@Path("/paciente")
public class PacienteREST {
	
	@Inject
	private UsuarioBLL usuarioBLL;

	@GET
	@Path("/todos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UsuarioOUT> listarDentistas() {	
		List<UsuarioOUT> retorno = usuarioBLL.listarDentitas();
		return retorno;
	}

	@GET
	@Path("/usuarios")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UsuarioOUT> listarUsuario() {	
		List<UsuarioOUT> retorno = usuarioBLL.listar(Constants.TODOS);
		return retorno;
	}
	
	@POST
	@Path("/salvar")
	@Produces(MediaType.APPLICATION_JSON)
	public TbUsuario salvar( @RequestBody TbUsuario item ) {
		item = usuarioBLL.guardar(item);
		return item;
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TbUsuario porID( @PathParam("id") Integer id ) {		
		return usuarioBLL.porId(id);
	}

}
