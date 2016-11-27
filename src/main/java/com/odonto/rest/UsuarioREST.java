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
import com.odonto.service.UsuarioService;

@Path("/usuario")
public class UsuarioREST {
	
	@Inject
	private UsuarioBLL usuarioBLL;

	@Inject
	private UsuarioService usuarioService;
	
	@GET
	@Path("/dentistas")
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
	public TbUsuario salvar( @RequestBody UsuarioOUT item ) {
		TbUsuario obj = new TbUsuario();
		if (item.getId() != null) {
			obj = usuarioBLL.porId(item.getId());
		}
		else {
			obj.setDsSenha("698dc19d489c4e4db73e28a713eab07b");
		}
		obj.setDsEmail(item.getDsEmail());
		obj.setDsNome(item.getDsNome());
		obj.setDsUsuario(item.getDsUsuario());
		obj.setFlDentista(item.getFlDentista());
		obj.setFlSocio(item.getFlSocio());
		obj = usuarioService.salvar(obj);
		return obj;
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TbUsuario porID( @PathParam("id") Integer id ) {		
		return usuarioBLL.porId(id);
	}

}
