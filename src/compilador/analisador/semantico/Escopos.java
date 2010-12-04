package compilador.analisador.semantico;

import compilador.estruturas.String;


public class Escopos {
	
	private static TabelaSimbolos[] ESCOPOS = new TabelaSimbolos[100];
	
	private static int PROXIMA_CHAVE = 0;
	
	private static int INDICE = -1;
	
	public static TabelaSimbolos getTabelaSimbolosAtual() {
		return ESCOPOS[INDICE];
	}
	
	public static void putTabelaSimbolos(TabelaSimbolos ts) {
		INDICE++;
		ESCOPOS[INDICE] = ts;
	}
	
	public static void popTabelaSimbolos() {
		ESCOPOS[INDICE] = null;
		INDICE--;
	}
	
	public static void atualizarTabelaSimbolosAtual(TabelaSimbolos ts) {
		ESCOPOS[INDICE] = ts;
	}
	
	public static int recuperarChave(int[] buffer) {
		for(int i = INDICE; i >= 0; i--) {
			int chave = ESCOPOS[i].recuperarChave(buffer);
			if(chave != -1)
				return chave;
		}
		
		return -1;
	}
	
	public static int inserirSimbolo(int[] buffer) {
		int chave = PROXIMA_CHAVE;
		Escopos.getTabelaSimbolosAtual().inserirSimbolo(chave, buffer);
		PROXIMA_CHAVE++;
		return chave;
	}
	
	public static void setSimboloTipo(int chave, int tipo) {
		Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).setTipo(tipo);
	}
	
	public static void setSimboloCategoria(int chave, int categoria) {
		Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).setCategoria(categoria);
	}
	
	public static void setSimboloEndereco(int chave, String endereco) {
		Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).setEndereco(endereco);
	}
	
	public static void setSimboloTamanho(int chave, int tamanho) {
		Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).setTamanho(tamanho);
	}
	
	public static void setSimboloDeclarado(int chave, boolean declarado) {
		Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).setDeclarado(declarado);
	}
	
	public static void addSimboloParametro(int chave, TSLinha parametro) {
		Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).addToParametros(parametro);
	}
	
	public static void setSimboloRotulo(int chave, String rotulo) {
		Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).setRotulo(rotulo);
	}
	
	public static int getSimboloTipo(int chave) {
		return Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).getTipo();
	}
	
	public static int getSimboloCategoria(int chave) {
		return Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).getCategoria();
	}
	
	public static String getSimboloEndereco(int chave) {
		return Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).getEndereco();
	}
	
	public static int getSimboloTamanho(int chave) {
		return Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).getTamanho();
	}
	
	public static boolean isSimboloDeclarado(int chave) {
		return Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).isDeclarado();
	}
	
	public static TSLinha[] getSimboloParametros(int chave) {
		return Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).getParametros();
	}
	
	public static String getSimboloRotulo(int chave) {
		return Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).getRotulo();
	}
	
	/**
	 * Recebe a chave de um item e recupera a tabela de s’mbolos onde ele est‡ registrado.
	 * @param chave
	 */
	private static TabelaSimbolos recuperarTabelaSimbolos(int chave) {
		for(int i = INDICE; i >= 0; i--)
			if(ESCOPOS[i].getTSLinha(chave) != null)
				return ESCOPOS[i];
		
		return null;
	}
}