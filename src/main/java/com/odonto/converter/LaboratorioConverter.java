package com.odonto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.odonto.BLL.LaboratorioBLL;
import com.odonto.model.TbLaboratorio;
import com.odonto.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = TbLaboratorio.class)
public class LaboratorioConverter implements Converter {

	private LaboratorioBLL bll;
	
	public LaboratorioConverter() {
		bll = CDIServiceLocator.getBean(LaboratorioBLL.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbLaboratorio retorno = null;
		
		if (value != null) {
			Integer id = new Integer(value);
			retorno = bll.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Integer id = (( TbLaboratorio) value).getId();
            return String.valueOf(id);
        }
		return "";
	}

}
