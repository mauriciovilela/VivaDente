package com.odonto.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.odonto.BLL.PacienteBLL;
import com.odonto.dto.PacienteIN;
import com.odonto.model.TbPaciente;
import com.odonto.security.SessionContext;
import com.odonto.util.Util;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PacienteBeanPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PacienteBLL pacienteBLL;
	
	private PacienteIN filtro;
	private LazyDataModel<TbPaciente> filtrados;
	
	private TbPaciente selecionado;
	
	public PacienteBeanPesquisa() {
		filtro = new PacienteIN();
		
		setFiltrados(new LazyDataModel<TbPaciente>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<TbPaciente> load(int first, int pageSize, String sortField, 
					SortOrder sortOrder, Map<String, Object> filters) {
				if (StringUtils.isNotBlank(filtro.getNome()) || 
					StringUtils.isNotBlank(filtro.getFone()) || 
					StringUtils.isNotBlank(filtro.getCpfRG())) {
					filtro.setFirst(first);
					filtro.setPageSize(pageSize);
					setRowCount(pacienteBLL.filtradosQTD(filtro));
					return pacienteBLL.filtrados(filtro);					
				}
				else {
					return null;
				}
			}
		});
	}
	
	public void excluir() {
		pacienteBLL.remover(selecionado);
		FacesUtil.addInfoMessage("Registro excluído com sucesso.");
	}
	
	public void redireciona() {
		Util.redirect("/restrita/paciente/Paciente.xhtml?paciente=" + selecionado.getId());
	}

	public void modalHistoricoPagamento(Integer idPaciente) {
		SessionContext.getInstance().setID(idPaciente);
		Util.abreModal("/restrita/pagamento/PagamentoHistoricoMD");
	}
	
	public void modalHistoricoConsulta(Integer idPaciente) {
		SessionContext.getInstance().setID(idPaciente);
		Util.abreModal("/restrita/agenda/AgendaHistoricoMD");
	}
	
	public PacienteIN getFiltro() {
		return filtro;
	}

	public LazyDataModel<TbPaciente> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(LazyDataModel<TbPaciente> filtrados) {
		this.filtrados = filtrados;
	}

	public TbPaciente getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(TbPaciente selecionado) {
		this.selecionado = selecionado;
	}

	public void setFiltro(PacienteIN filtro) {
		this.filtro = filtro;
	}

}