package com.odonto.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.odonto.BLL.ProcedimentoBLL;
import com.odonto.model.TbProcedimento;
import com.odonto.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = TbProcedimento.class)
public class ProcedimentoConverter implements Converter {

	private ProcedimentoBLL bll;
	
	public ProcedimentoConverter() {
		bll = CDIServiceLocator.getBean(ProcedimentoBLL.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		TbProcedimento retorno = null;
		
		if (value != null) {
			Integer id = new Integer(value);
			retorno = bll.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !value.equals("")) {
            Integer id = (( TbProcedimento) value).getId();
            return String.valueOf(id);
        }
		return "";
	}

}
