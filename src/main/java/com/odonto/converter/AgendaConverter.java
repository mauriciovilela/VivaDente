package com.odonto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.odonto.BLL.AgendaBLL;
import com.odonto.model.TbAgenda;
import com.odonto.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = TbAgenda.class)
public class AgendaConverter implements Converter {

	private AgendaBLL pacienteBLL;
	
	public AgendaConverter() {
		pacienteBLL = CDIServiceLocator.getBean(AgendaBLL.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbAgenda retorno = null;
		
		if (value != null) {
			Integer id = new Integer(value);
			retorno = pacienteBLL.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Integer id = (( TbAgenda) value).getId();
            return String.valueOf(id);
        }
		return "";
	}

}
