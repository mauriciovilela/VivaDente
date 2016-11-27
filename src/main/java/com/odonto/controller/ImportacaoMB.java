package com.odonto.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.odonto.model.TbPaciente;

@Named
@ViewScoped
public class ImportacaoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ImportacaoMB.class);
	
	private List<TbPaciente> lista = new ArrayList<TbPaciente>();

	public void realizarImportacao() throws ParseException {
		BufferedReader br = null;
		TbPaciente item = new TbPaciente();
		try {

			String sCurrentLine;
			String foneCelular;
			String foneFixo;
			String nascimento;
			String nome;

			String arquivo = "C:\\temp\\clientes_hudson.txt";
			br = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "Cp1252"));
			
			Integer i = 0;
			Integer y = 0;

			while ((sCurrentLine = br.readLine()) != null) {
				if (sCurrentLine.contains("###")) {
					if (i == 1) {
						lista.add(item);
//						pacienteService.salvar(item);
						item = new TbPaciente();
					}
					sCurrentLine = br.readLine();
					if (i > 1) {
						lista.add(item);
//						pacienteService.salvar(item);
						item = new TbPaciente();
					}
				}
				if (sCurrentLine.contains("Nome Completo")) {
					sCurrentLine = sCurrentLine.replace("Nome Completo:", "");
					y = sCurrentLine.indexOf("Nascimento:");
					nome = sCurrentLine.substring(0, y).trim();
					item.setDsNome(nome.substring(0, nome.lastIndexOf(" ")));
					nascimento = sCurrentLine.substring(y, sCurrentLine.length()).replace("Nascimento:", "").trim();
					if (StringUtils.isNotBlank(nascimento)) {
						SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						item.setDtNascimento(formatter.parse(nascimento));
					}
				}
				if (sCurrentLine.contains("Endereço")) {
					sCurrentLine = sCurrentLine.replace("Endereço:", "");
					y = sCurrentLine.indexOf("Bairro:");
					item.setDsEndereco(sCurrentLine.substring(0, y).trim());
				}
				if (sCurrentLine.contains("E-mail")) {
					item.setDsEmail(sCurrentLine.replace("E-mail:", "").trim());
				}
				if (sCurrentLine.contains("Telefone")) {
					sCurrentLine = sCurrentLine.replace("Telefone:", "");
					y = sCurrentLine.indexOf("Celular:");
					foneFixo = sCurrentLine.substring(0, y).replace("-", "").replace(" ", "").replace("(", "").replace(")", "").trim();
					foneCelular = sCurrentLine.substring(y, sCurrentLine.length()).replace("-", "").replace("Celular:", "").replace(" ", "").replace("(", "").replace(")", "").trim();
					if ( (foneFixo.length() >= 8 && foneFixo.substring(0, 1).equals("9")) &&
						 (foneCelular.length() >= 8 && foneCelular.substring(0, 1).equals("9")) ) {
						item.setDsCelular(foneCelular);
						item.setDsFone(foneFixo);
					}
					else {
						if ( (foneFixo.length() == 8 || foneFixo.length() == 9) && foneFixo.substring(0, 1).equals("9")) {
							item.setDsCelular(foneFixo);
						}
						else {
							item.setDsCelular(foneCelular);
						}
						if ( (foneCelular.length() == 8 ) && foneCelular.substring(0, 1).equals("3")) {
							item.setDsFone(foneCelular);
						}
						else {
							item.setDsFone(foneFixo);
						}						
					}
				}
				if (sCurrentLine.contains("Cidade")) {
					sCurrentLine = br.readLine();
					if (sCurrentLine.length() >= 59) {
						item.setDsCidade(sCurrentLine.substring(9, 56).trim());
						y = sCurrentLine.indexOf("UF:");
						sCurrentLine = sCurrentLine.replace("UF:", "");
						item.setDsCidade(item.getDsCidade().concat(", " + sCurrentLine.substring(y, sCurrentLine.indexOf("CEP:")).trim()).trim());
						if (item.getDsCidade().toUpperCase().equals("SÃO PAULO, SP") ||
							item.getDsCidade().toUpperCase().equals("UBERLANDIA, SP") ||
							item.getDsCidade().toUpperCase().equals("UBERLÂNDIA, SP") ||
							item.getDsCidade().toUpperCase().equals("UBERLANDIA, MG") ||
							item.getDsCidade().toUpperCase().equals("UBERLÂNDIA, MG") ) {
							item.setDsCidade("Uberlândia, MG");
						}
						else {
							item.setDsCidade(item.getDsCidade().toUpperCase().replace("SÃO PAULO, SP", "Uberlândia, MG"));
						}
					}
				}
				i++;
			}
//			TbPaciente encontrado = null;
//			for (TbPaciente item2 : lista) {
//				encontrado = pacienteBLL.porNome2(item2.getDsNome());
//				if (encontrado != null) {
//					item2.setId(encontrado.getId());
//					pacienteService.salvar(item2);					
//				}
//			}
			logger.info("total: " + lista.size());

		} catch (Exception e) {
			logger.info("item: " + item.getDsNome());
			e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public List<TbPaciente> getLista() {
		return lista;
	}

	public void setLista(List<TbPaciente> lista) {
		this.lista = lista;
	}
	
	

}