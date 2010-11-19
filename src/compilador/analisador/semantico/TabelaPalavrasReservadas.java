package compilador.analisador.semantico;

import compilador.helper.ArrayHelper;

public class TabelaPalavrasReservadas {
	
	/*
	 * Implementação temporária da tabela de palavras reservadas.
	 * */
	
	private static int[][] TABELA_PALAVRAS_RESERVADAS;
	
	static {
		TABELA_PALAVRAS_RESERVADAS = new int[16][];
		
		TABELA_PALAVRAS_RESERVADAS[0] = TabelaPalavrasReservadas.converter("if".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[1] = TabelaPalavrasReservadas.converter("else".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[2] = TabelaPalavrasReservadas.converter("while".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[3] = TabelaPalavrasReservadas.converter("int".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[4] = TabelaPalavrasReservadas.converter("char".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[5] = TabelaPalavrasReservadas.converter("void".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[6] = TabelaPalavrasReservadas.converter("boolean".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[7] = TabelaPalavrasReservadas.converter("true".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[8] = TabelaPalavrasReservadas.converter("false".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[9] = TabelaPalavrasReservadas.converter("typedef".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[10] = TabelaPalavrasReservadas.converter("struct".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[11] = TabelaPalavrasReservadas.converter("const".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[12] = TabelaPalavrasReservadas.converter("write".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[13] = TabelaPalavrasReservadas.converter("read".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[14] = TabelaPalavrasReservadas.converter("return".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[15] = TabelaPalavrasReservadas.converter("main".toCharArray());
	}
	
	private static int[] converter(char[] arr) {
		int[] vetor = new int[arr.length];
		
		for(int i = 0; i < arr.length; i++) {
			vetor[i] = (int)arr[i];
		}
		
		return vetor;
	}
	
	public static int recuperarChave(int[] buffer) {
		for(int i = 0; i < TABELA_PALAVRAS_RESERVADAS.length; i++) {
			if(TABELA_PALAVRAS_RESERVADAS[i] != null && ArrayHelper.compararVetoresInt(TABELA_PALAVRAS_RESERVADAS[i], buffer)) {
				return i;
			}
		}
		
		return -1;
	}
}