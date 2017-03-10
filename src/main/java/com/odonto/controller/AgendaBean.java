package com.odonto.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.text.MaskFormatter;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.odonto.BLL.AgendaBLL;
import com.odonto.BLL.AgendaStatusBLL;
import com.odonto.BLL.UsuarioBLL;
import com.odonto.constants.Constants;
import com.odonto.constants.Constants.TbAgendaStatus;
import com.odonto.dto.AgendaOUT;
import com.odonto.dto.UsuarioOUT;
import com.odonto.model.TbAgenda;
import com.odonto.model.TbPaciente;
import com.odonto.model.TbProcedimento;
import com.odonto.model.TbUsuario;
import com.odonto.security.SessionContext;
import com.odonto.service.AgendaService;
import com.odonto.service.NegocioException;
import com.odonto.util.Util;
import com.odonto.util.jsf.FacesUtil;

@Named
@ViewScoped
public class AgendaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AgendaBLL agendaBLL;

	@Inject
	private UsuarioBLL usuarioBLL;

	@Inject
	private AgendaStatusBLL agendaStatusBLL;

	@Inject
	private AgendaService agendaService;

	private TbAgenda agenda;
	private List<AgendaOUT> listaAgenda;
	private TbAgenda itemDesmacar;

	private Integer idDentista;

	private TbPaciente paciente;

	@NotNull
	private Integer idProcedimento;
	
	private String telefonePaciente;

	private ScheduleModel eventModel;

	private boolean emEdicao;
	private Date dataInicialConsulta;
	private Date dataFinalConsulta;

	@NotEmpty
	private String horaInicio;

	@NotEmpty
	private String horaFim;
	
	@Inject
	private HomeMB homeMB;
	
	private List<UsuarioOUT> dentistas;
	
	private List<AgendaOUT> agendaFutura;
	
	private String nomeDentista;
	private String dataDentista;
	private Integer idFilial;
	
	Map<Integer, String> mapPatternFone = new HashMap<Integer, String>(); 
	
	public AgendaBean() {
		eventModel = new LazyScheduleModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void loadEvents(Date start, Date end) {
				dataInicialConsulta = start;
				dataFinalConsulta = end;
				pesquisarPorDentista();
			}
		};
	}

	@PostConstruct
	private void pageLoad() {
		if (FacesUtil.isNotPostback()) {
			idFilial = SessionContext.getInstance().getIdFilial();
			agenda = new TbAgenda();
			if (homeMB.isPerfilDentista()) {
				TbUsuario usuarioLogado = SessionContext.getInstance().getUsuarioLogado();
				UsuarioOUT dentistaLogado = new UsuarioOUT();
				dentistaLogado.setId(usuarioLogado.getId());
				dentistaLogado.setDsNome(usuarioLogado.getDsNome());
				dentistas = new ArrayList<UsuarioOUT>();
				dentistas.add(dentistaLogado);
			}
			// Ultimo dentista selecionado
			if (SessionContext.getInstance().getCodigoDentista() != null) {
				idDentista = SessionContext.getInstance().getCodigoDentista();
				pesquisarPorDentista();
			}
			setPatternFone();
		}
	}

	public void trocarFilial() {
		SessionContext.getInstance().setIdFilial(idFilial);
		homeMB.setNomeFilial(idFilial.equals(1) ? "Centro" : "Tocantins");		
		pesquisarPorDentista();
	}

	public void pesquisarPorDentista() {
		agenda = new TbAgenda();

		listaAgenda = new ArrayList<AgendaOUT>();
		if (dataInicialConsulta != null) {
			setListaAgenda(agendaBLL.listarPorDentista(idDentista, dataInicialConsulta, dataFinalConsulta, false));
		}

		// percorre a lista de eventos e popula o calendario
		for (AgendaOUT agenda : listaAgenda) {

			DefaultScheduleEvent evento = new DefaultScheduleEvent();
			// evento.setAllDay(agenda.isDiaTodo());
			evento.setStartDate(agenda.getDtInicio());
			evento.setEndDate(agenda.getDtFim());
			evento.setTitle(getTituloAgenda(agenda));
			evento.setData(agenda.getId());
			evento.setEditable(true);

			if (agenda.getIdAgendaStatus().equals(TbAgendaStatus.confirmado)) {
				evento.setStyleClass("agendaConfirmado");
			}
			else if (agenda.getIdAgendaStatus().equals(TbAgendaStatus.falta)) {
				evento.setStyleClass("agendaFalta");
			}
			else if (agenda.getIdAgendaStatus().equals(TbAgendaStatus.marcado)) {
				evento.setStyleClass("agendaMarcado");
			}
			else if (agenda.getIdAgendaStatus().equals(TbAgendaStatus.faltaDentista)) {
				evento.setStyleClass("agendaFaltaDentista");
			}
			else {
//					evento.setStyleClass("agendaRemarcado");
			}				

			// o evento e adicionado na lista
			eventModel.addEvent(evento);
		}
		
		// Ultimo dentista selecionado
		SessionContext.getInstance().setCodigoDentista(idDentista);
	}

	private String getTituloAgenda(AgendaOUT itemAgenda) {
		StringBuilder titulo = new StringBuilder();
		if (StringUtils.isNotBlank(itemAgenda.getDsPaciente())) {
			titulo.append(itemAgenda.getDsPaciente());
		}
		if (StringUtils.isNotBlank(itemAgenda.getDsProcedimento())) {
			titulo.append(" (");
			titulo.append(itemAgenda.getDsProcedimento());
			titulo.append(")");
		} else if (StringUtils.isNotBlank(itemAgenda.getDsDescricao())) {
			titulo.append(" (");
			titulo.append(itemAgenda.getDsDescricao());
			titulo.append(")");
		} else {
			titulo.append(StringUtils.EMPTY);
		}
		return titulo.toString();
	}

	public void agendaSalvar() {
		agendaSalvar(Constants.TbAgendaStatus.marcado);
		adicionaMensagem(Constants.TbAgendaStatus.marcado);
	}

	public void agendaRegistrarFalta() {
		agendaSalvar(Constants.TbAgendaStatus.falta);
		adicionaMensagem(Constants.TbAgendaStatus.falta);
	}

	public void agendaConfirmarPaciente() {
		agendaSalvar(Constants.TbAgendaStatus.confirmado);
		adicionaMensagem(Constants.TbAgendaStatus.confirmado);
	}
	
	public void agendaDesmarcar() {
		agendaSalvar(Constants.TbAgendaStatus.desmarcado);
		adicionaMensagem(Constants.TbAgendaStatus.desmarcado);
	}

	public void agendaRemarcar() {
		agendaSalvar(Constants.TbAgendaStatus.remarcado);
		adicionaMensagem(Constants.TbAgendaStatus.remarcado);
	}
	
	public void abreDesmarcar() {
		RequestContext.getCurrentInstance().execute("PF('dlgRemarcar').show();");
		itemDesmacar = agenda;
	}
	
	public void agendaExcluir() {
		agendaService.excluir(agenda);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Consulta excluída"));	
		RequestContext.getCurrentInstance().execute("PF('dlgAgenda').hide();");
	}

	private void adicionaMensagem(int status) {
		String mensagem = "";
		if (status == Constants.TbAgendaStatus.marcado) {
			mensagem = "Consulta Marcada";
		} else if (status == Constants.TbAgendaStatus.falta) {
			mensagem = "Falta registrada";
		} else if (status == Constants.TbAgendaStatus.desmarcado) {
			mensagem = "Consulta desmarcada";
		} else if (status == Constants.TbAgendaStatus.confirmado) {
			mensagem = "Consulta confimada";
		} else if (status == Constants.TbAgendaStatus.remarcado) {
			mensagem = "Consulta remarcada";
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(mensagem));
//		context.addMessage(null,
//				new FacesMessage(mensagem,
//						paciente.getDsNome() + "<br /> Data: " + Util.getData(agenda.getDtInicio()) + "<br />Hora: "
//								+ Util.getHora(agenda.getDtInicio()) + " - " + Util.getHora(agenda.getDtFim())));
	}

	/**
	 * Metodo para salvar o evento
	 */
	private void agendaSalvar(int status) {
		if (paciente == null) {
			status = TbAgendaStatus.faltaDentista;
		}

		Calendar calDtInicio = Util.dateToCalendar(agenda.getDtInicio());
		calDtInicio.set(Calendar.HOUR_OF_DAY, retornaHora(horaInicio));
		calDtInicio.set(Calendar.MINUTE, retornaMinutos(horaInicio));
		
		Calendar calDtFim = Util.dateToCalendar(agenda.getDtInicio());
		calDtFim.set(Calendar.HOUR_OF_DAY, retornaHora(horaFim));
		calDtFim.set(Calendar.MINUTE, retornaMinutos(horaFim));

		if (status == Constants.TbAgendaStatus.remarcado) {
			//RequestContext.getCurrentInstance().execute("PF('dlgRemarcar').hide();");
			List<AgendaOUT> agendaOcupada = agendaBLL.listarPorDentista(idDentista, calDtInicio.getTime(), calDtFim.getTime(), true);
			int totalEncontrados = agendaOcupada.size(); 
			if (totalEncontrados > 0) {
				boolean intervalo = false;				
				for (AgendaOUT item : agendaOcupada) {
					if (Util.getHora(item.getDtInicio()).equals(Util.getHora(calDtFim.getTime()))) {
						intervalo = true;
						break;
					}
					if (Util.getHora(item.getDtFim()).equals(Util.getHora(calDtInicio.getTime()))) {
						intervalo = true;
						break;
					}
				}
				boolean liberado = (intervalo && totalEncontrados == 1);
				if (!liberado) {
					//RequestContext.getCurrentInstance().execute("PF('dlgAgenda').hide();");
					throw new NegocioException("Já existe um paciente marcado neste dia e horário.");					
				}
			}
			RequestContext.getCurrentInstance().execute("PF('dlgRemarcar').hide();");
			RequestContext.getCurrentInstance().execute("PF('dlgAgendaFutura').hide();");
		}

		agenda.setTbPaciente(paciente);
		agenda.setDtInicio(calDtInicio.getTime());
		agenda.setTbDentista(usuarioBLL.porId(idDentista));
		agenda.setTbProcedimento(new TbProcedimento(idProcedimento));
		agenda.setTbAgendaStatus(agendaStatusBLL.porId(status));
		agenda.setDtFim(calDtFim.getTime());

		agenda = agendaService.salvar(agenda);

		RequestContext.getCurrentInstance().execute("PF('dlgAgenda').hide();");
	}

	private int retornaHora(String horaMinuto) {
		String[] hora = horaMinuto.split(":");
		return Integer.parseInt(hora[0]);
	}

	private int retornaMinutos(String horaMinuto) {
		String[] hora = horaMinuto.split(":");
		return Integer.parseInt(hora[1]);
	}

	/**
	 * Evento para quando o usuario clica em um espaco em branco no calendario
	 * 
	 * @param selectEvent
	 */
	public void quandoNovo(SelectEvent selectEvent) {

		idProcedimento = null;
		paciente = null;

		ScheduleEvent event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(),
				(Date) selectEvent.getObject());

		Calendar dataInicial = Util.dateToCalendar(event.getStartDate());

		agenda = new TbAgenda();
		// Recupera a data em que o usuário clicou
		agenda.setDtInicio(dataInicial.getTime());
		agenda.setDtFim(event.getEndDate());

		horaInicio = new SimpleDateFormat("HH:mm").format(dataInicial.getTime());

		dataInicial.add(Calendar.MINUTE, 30);
		horaFim = new SimpleDateFormat("HH:mm").format(dataInicial.getTime());

		idProcedimento = 136;

		agenda.setDsDescricao("");
	}

	private void setHoraInicioFim(TbAgenda _agenda) {
		horaInicio = new SimpleDateFormat("HH:mm").format(_agenda.getDtInicio().getTime());
		horaFim = new SimpleDateFormat("HH:mm").format(_agenda.getDtFim().getTime());
	}

	/**
	 * Evento para quando usuario clica em um enveto ja existente
	 * 
	 * @param selectEvent
	 */
	public void quandoSelecionado(SelectEvent selectEvent) {

		ScheduleEvent event = (ScheduleEvent) selectEvent.getObject();

		for (AgendaOUT itemAgenda : listaAgenda) {
			if (itemAgenda.getId() == (Integer) event.getData()) {
				agenda = agendaBLL.porId(itemAgenda.getId());
				paciente = agenda.getTbPaciente();
				idProcedimento = agenda.getTbProcedimento().getId();
				setHoraInicioFim(agenda);
				break;
			}
		}
	}

	/**
	 * Evento para quando o usuario 'move' um evento atraves de 'drag and drop'
	 * 
	 * @param event
	 */
	public void quandoMovido(ScheduleEntryMoveEvent event) {
		for (AgendaOUT itemAgenda : listaAgenda) {
			if (itemAgenda.getId() == (Integer) event.getScheduleEvent().getData()) {
				agenda = agendaBLL.porId(itemAgenda.getId());
				// setHoraInicioFim(agenda);
				agenda.setTbAgendaStatus(agendaStatusBLL.porId(Constants.TbAgendaStatus.remarcado));
				agenda.setDtInicio(event.getScheduleEvent().getStartDate());
				agenda.setDtFim(event.getScheduleEvent().getEndDate());
				agendaService.salvar(agenda);
				adicionaMensagem(Constants.TbAgendaStatus.remarcado);
				break;
			}
		}
	}

	/**
	 * Evento para quando o usuario 'redimenciona' um evento
	 * 
	 * @param event
	 */
	public void quandoRedimensionado(ScheduleEntryResizeEvent event) {
		for (AgendaOUT itemAgenda : listaAgenda) {
			if (itemAgenda.getId() == (Integer) event.getScheduleEvent().getData()) {
				agenda = agendaBLL.porId(itemAgenda.getId());
				// setHoraInicioFim(agenda);
				agenda.setDtInicio(event.getScheduleEvent().getStartDate());
				agenda.setDtFim(event.getScheduleEvent().getEndDate());
				agendaService.salvar(agenda);
				break;
			}
		}
	}

	public void modalHistoricoPagamento() {
		SessionContext.getInstance().setID(paciente.getId());
		Util.abreModal("/restrita/pagamento/PagamentoHistoricoMD");
	}

	public void modalHistoricoConsulta() {
		SessionContext.getInstance().setID(paciente.getId());
		Util.abreModal("/restrita/agenda/AgendaHistoricoMD");
	}
	
	public String getLayoutRB() {
		if (Util.isMobile()) {
			return "pageDirection";
		}
		else {
			return "lineDirection";
		}
	}
	
	public void consultaAgendaFutura(SelectEvent event) {
		if (idDentista != null) {
			agendaFutura = agendaBLL.listarPorDentista(idDentista, agenda.getDtInicio(), agenda.getDtInicio(), false);
			nomeDentista = usuarioBLL.porId(idDentista).getDsNome();
			dataDentista = Util.getData(agenda.getDtInicio());
			// Abre janela com os horários disponíveis na data selecionada
			RequestContext.getCurrentInstance().execute("PF('dlgAgendaFutura').show();");			
		}
	}
	
	public String formata(String valor) {
		if (valor == null) {
			return valor;
		}
		if (!StringUtils.isNumeric(valor)) {
			return valor;
		}
		String pattern = mapPatternFone.get(valor.trim().length());
		if (pattern == null) {
			return valor;
		}
        MaskFormatter mf;
        try {
            mf = new MaskFormatter(pattern);
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(valor);
        } catch (ParseException ex) {
            ex.getStackTrace();
        }
        return null;
    }
	
	private void setPatternFone() {
		mapPatternFone.put(8, "####-####");
		mapPatternFone.put(9, "#-####-####");
		mapPatternFone.put(10, "(##) ####-####");
		mapPatternFone.put(11, "(##) #-####-####");	
	}
	
	public void selecionaDentista() {
		//do nothing
	}
	
	public void capturaTelefone() {
		
	}

	// getters and setters

	public TbAgenda getAgenda() {
		return agenda;
	}

	public void setAgenda(TbAgenda agenda) {
		this.agenda = agenda;
	}

	public List<AgendaOUT> getListaAgenda() {
		return listaAgenda;
	}

	public void setListaAgenda(List<AgendaOUT> listaAgenda) {
		this.listaAgenda = listaAgenda;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public boolean isEmEdicao() {
		return emEdicao;
	}

	public void setEmEdicao(boolean emEdicao) {
		this.emEdicao = emEdicao;
	}

	public TbPaciente getPaciente() {
		return paciente;
	}

	public void setPaciente(TbPaciente paciente) {
		this.paciente = paciente;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}

	public Integer getIdProcedimento() {
		return idProcedimento;
	}

	public void setIdProcedimento(Integer idProcedimento) {
		this.idProcedimento = idProcedimento;
	}

	public Integer getIdDentista() {
		return idDentista;
	}

	public void setIdDentista(Integer idDentista) {
		this.idDentista = idDentista;
	}

	public Date getDataInicialConsulta() {
		return dataInicialConsulta;
	}

	public void setDataInicialConsulta(Date dataInicialConsulta) {
		this.dataInicialConsulta = dataInicialConsulta;
	}

	public Date getDataFinalConsulta() {
		return dataFinalConsulta;
	}

	public void setDataFinalConsulta(Date dataFinalConsulta) {
		this.dataFinalConsulta = dataFinalConsulta;
	}

	public List<UsuarioOUT> getDentistas() {
		return dentistas;
	}

	public void setDentistas(List<UsuarioOUT> dentistas) {
		this.dentistas = dentistas;
	}

	public TbAgenda getItemDesmacar() {
		return itemDesmacar;
	}

	public void setItemDesmacar(TbAgenda itemDesmacar) {
		this.itemDesmacar = itemDesmacar;
	}

	public List<AgendaOUT> getAgendaFutura() {
		return agendaFutura;
	}

	public void setAgendaFutura(List<AgendaOUT> agendaFutura) {
		this.agendaFutura = agendaFutura;
	}

	public String getNomeDentista() {
		return nomeDentista;
	}

	public void setNomeDentista(String nomeDentista) {
		this.nomeDentista = nomeDentista;
	}

	public String getDataDentista() {
		return dataDentista;
	}

	public void setDataDentista(String dataDentista) {
		this.dataDentista = dataDentista;
	}

	public String getTelefonePaciente() {
		return telefonePaciente;
	}

	public void setTelefonePaciente(String telefonePaciente) {
		this.telefonePaciente = telefonePaciente;
	}

	public Integer getIdFilial() {
		return idFilial;
	}

	public void setIdFilial(Integer idFilial) {
		this.idFilial = idFilial;
	}


}
