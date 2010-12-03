package compilador.analisador.semantico;

import compilador.analisador.lexico.ParametrosAcoesSemanticas;


public class AcoesSemanticas {
	
	// AÇÕES SEMÂNTICAS ESPECÍFICAS
	
	public static void programa_0_1_5_1() {
		ParametrosAcoesSemanticas.TIPO = ParametrosAcoesSemanticas.TIPO_SIMBOLO_VOID;
		ParametrosAcoesSemanticas.CATEGORIA = ParametrosAcoesSemanticas.CATEGORIA_SIMBOLO_FUNCAO;
		ParametrosAcoesSemanticas.DECLARADO = true;
	}
	
	public static void programa_1_2_0_4() {
		Escopos.setSimboloTipo(ParametrosAcoesSemanticas.TOKEN.getID(), ParametrosAcoesSemanticas.TIPO);
		Escopos.setSimboloCategoria(ParametrosAcoesSemanticas.TOKEN.getID(), ParametrosAcoesSemanticas.CATEGORIA);
		Escopos.setSimboloDeclarado(ParametrosAcoesSemanticas.TOKEN.getID(), ParametrosAcoesSemanticas.DECLARADO);
	}
	
	// AÇÕES SEMÂNTICAS GLOBAIS
	
	public static void acaoPadrao() {
		// System.out.println("ACAO SEMANTICA PADRAO");
	}
	
	public static void criarEscopo() {
		System.out.println("ACAO SEMANTICA: criarEscopo");
		
		TabelaSimbolos ts = new TabelaSimbolos();
		Escopos.putTabelaSimbolos(ts);
	}
	
	public static void deletarEscopo() {
		System.out.println("ACAO SEMANTICA: deletarEscopo");
		
		Escopos.popTabelaSimbolos();
	}
}