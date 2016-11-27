package com.odonto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.odonto.BLL.UsuarioBLL;
import com.odonto.model.TbUsuario;
import com.odonto.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = TbUsuario.class)
public class UsuarioConverter implements Converter {

	private UsuarioBLL usuarioBLL;
	
	public UsuarioConverter() {
		usuarioBLL = CDIServiceLocator.getBean(UsuarioBLL.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbUsuario retorno = null;
		
		if (value != null) {
			Integer id = new Integer(value);
			retorno = usuarioBLL.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Integer id = (( TbUsuario) value).getId();
            return String.valueOf(id);
        }
		return "";
	}

}
