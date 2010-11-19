package compilador.analisador.semantico;

import compilador.helper.ArrayHelper;

public class TabelaSimbolos {
	
	/*
	 * Implementação temporária da tabela de símbolos.
	 * */
	
	private static int[][] TABELA_SIMBOLOS = new int[100][];
	
	private static int PROXIMA_CHAVE = 0;
	
	public static int recuperarChave(int[] buffer) {
		for(int i = 0; i < TABELA_SIMBOLOS.length; i++) {
			if(TABELA_SIMBOLOS[i] != null && ArrayHelper.compararVetoresInt(TABELA_SIMBOLOS[i], buffer)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public static int inserirSimbolo(int[] buffer) {
		int chave = PROXIMA_CHAVE;
		TABELA_SIMBOLOS[chave] = buffer;
		PROXIMA_CHAVE++;
		return chave;
	}
}