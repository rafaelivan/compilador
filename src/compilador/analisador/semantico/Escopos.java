package compilador.analisador.semantico;

import compilador.estruturas.String;


public class Escopos {

	/*
	 * Categorias de s’mbolos.
	 */
	public final static int CATEGORIA_SIMBOLO_VARIAVEL = 0;
	public final static int CATEGORIA_SIMBOLO_FUNCAO = 1;
	public final static int CATEGORIA_SIMBOLO_VETOR = 2;
	public final static int CATEGORIA_SIMBOLO_MATRIZ = 3;
	public final static int CATEGORIA_SIMBOLO_PARAMETRO = 4;
	
	/*
	 * Mapeamento de tipos de s’mbolos.
	 */
	public static final int TIPO_SIMBOLO_VOID = -1;
	public static final int TIPO_SIMBOLO_INT = 0;
	public static final int TIPO_SIMBOLO_CHAR = 1;
	public static final int TIPO_SIMBOLO_BOOLEAN = 2;
	public static final int TIPO_SIMBOLO_STRING = 3;
	
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
	
	public static void addSimboloParametro(int chave, int parametro) {
		Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).addToParametros(parametro);
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
	
	public static int[] getSimboloParametros(int chave) {
		return Escopos.recuperarTabelaSimbolos(chave).getTSLinha(chave).getParametros();
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