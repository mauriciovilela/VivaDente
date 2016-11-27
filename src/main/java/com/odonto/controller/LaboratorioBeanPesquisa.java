package com.odonto.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.odonto.BLL.LaboratorioBLL;
import com.odonto.BLL.LaboratorioHistoricoBLL;
import com.odonto.dto.LaboratorioIN;
import com.odonto.model.TbAuditoria;
import com.odonto.model.TbLaboratorio;
import com.odonto.model.TbLaboratorioHistorico;
import com.odonto.security.SessionContext;
import com.odonto.service.AuditoriaService;
import com.odonto.service.LaboratorioService;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class LaboratorioBeanPesquisa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private LaboratorioBLL laboratorioBLL;
	
	@Inject
	private LaboratorioHistoricoBLL laboratorioHistoricoBLL;

	@Inject
	private LaboratorioService laboratorioService;

	@Inject
	private AuditoriaService auditoria;
	
	private String texto;
	private List<TbLaboratorioHistorico> filtrados;
	private Date data;
	private LaboratorioIN filtro;

	private TbLaboratorio laboratorio;

	private TbLaboratorioHistorico selecionado;
	
	private double acumulaAUX = 0;

	public LaboratorioBeanPesquisa() {
		limpar();
	}
	
	private void limpar() {
		filtro = new LaboratorioIN();
		texto = StringUtils.EMPTY;
		data = new Date();
	}
	
	public void excluir() {
		laboratorioHistoricoBLL.remover(selecionado);
		
		laboratorio = selecionado.getTbLaboratorio();
		laboratorio.setVlPago(laboratorio.getVlPago().subtract(selecionado.getVlPago()));
		laboratorio.setFlPago(false);
		laboratorioService.salvar(laboratorio, null);

		filtrados.remove(selecionado);

//		filtro.setIdDentista(selecionado.getTbDentista().getId());
//		filtro.setNomePaciente(selecionado.getNomePaciente());
//		filtrados = laboratorioHistoricoBLL.filtrados(filtro);
		filtrados = laboratorioHistoricoBLL.porLaboratorio(laboratorio.getId());
		// Caso tenha removido o ultimo historico, remove também o registro
		// principal
		if (filtrados.size() == 0) {
			laboratorioBLL.remover(laboratorio);
		}

		// Grava log de auditoria
		TbAuditoria audit = new TbAuditoria();
		audit.setDsDescricao("Laboratório excluído. Usuario: "
				+ SessionContext.getInstance().getUsuarioLogado().getDsNome() + ", Valor total "
				+ selecionado.getVlTotal() + ", Valor pago " + selecionado.getVlPago() + ", Data "
				+ selecionado.getDtEntrada() + ", Lab/Cliente " + selecionado.getTbDentista().getDsNome()
				+ selecionado.getDtEntrada() + ", Paciente " + selecionado.getNomePaciente());
		auditoria.salvar(audit);

		FacesUtil.addInfoMessage("Registro excluído com sucesso.");
		
		limpar();
	}
	
	public void pesquisar() {
		filtrados = laboratorioHistoricoBLL.filtrados(filtro);
		//filtro.setIdDentista(null);
	}

	public double getValorAcumulado() {
		double aux = acumulaAUX;
		acumulaAUX = 0;
		return aux;
	}

	public void acumulaValor(double value) {
		acumulaAUX += value;
	}

	public List<TbLaboratorioHistorico> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<TbLaboratorioHistorico> filtrados) {
		this.filtrados = filtrados;
	}

	public TbLaboratorioHistorico getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(TbLaboratorioHistorico selecionado) {
		this.selecionado = selecionado;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public LaboratorioIN getFiltro() {
		return filtro;
	}

	public void setFiltro(LaboratorioIN filtro) {
		this.filtro = filtro;
	}

}