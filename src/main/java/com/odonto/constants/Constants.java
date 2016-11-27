package com.odonto.constants;

public final class Constants {

	public class TbLoteria {
		public static final int cdLoteriaMegaSena = 1;	
	}

	public class TbTipoPgto {
		public static final int dinheiro = 1;
		public static final int credito = 2;
		public static final int debito = 3;
		public static final int cheque = 4;
	}

	public class TbStatusPgto {
		public static final int pago = 1;
		public static final int pendente = 2;
		public static final int pasta = 3;
	}
	
	public class TbAgendaStatus {
		public static final int marcado = 1;
		public static final int falta = 2;
		public static final int desmarcado = 3;
		public static final int faltaDentista = 4;
		public static final int remarcado = 5;
		public static final int confirmado = 6;
	}
	
	public class TbFinalidade {
		public static final int orcamento = 1;
		public static final int consulta = 2;
	}	

	public static final int firstTab = 0;
	
	public static final byte Sim = 1;
	public static final byte Nao = 0;
	public static final boolean SIM = true;
	public static final boolean NAO = false;
	public static final short CodRelatorio = 13;
	
	public static final String msgSucesso = "Operação realizada com sucesso"; 

	public static final Boolean ATIVOS = true;
	public static final Boolean INATIVOS = false;
	public static final Boolean TODOS = null;
	
}
