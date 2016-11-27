package com.odonto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.odonto.BLL.PacienteBLL;
import com.odonto.model.TbPaciente;
import com.odonto.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = TbPaciente.class)
public class PacienteConverter implements Converter {

	private PacienteBLL pacienteBLL;
	
	public PacienteConverter() {
		pacienteBLL = CDIServiceLocator.getBean(PacienteBLL.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbPaciente retorno = null;
		
		if (value != null) {
			Integer id = new Integer(value);
			retorno = pacienteBLL.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Integer id = (( TbPaciente) value).getId();
            return String.valueOf(id);
        }
		return "";
	}

}
