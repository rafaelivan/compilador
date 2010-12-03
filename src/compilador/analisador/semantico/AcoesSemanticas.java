package compilador.analisador.semantico;


public class AcoesSemanticas {
	
	public static void acaoPadrao() {
//		System.out.println("ACAO SEMANTICA PADRAO");
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