package compilador.analisador.semantico;

public class TabelaPalavrasReservadas {
	
	/*
	 * Implementação temporária da tabela de palavras reservadas.
	 * */
	
	private static int[][] TABELA_PALAVRAS_RESERVADAS;
	
	static {
		TABELA_PALAVRAS_RESERVADAS = new int[4][];
		
		TABELA_PALAVRAS_RESERVADAS[0] = TabelaPalavrasReservadas.converter("IF".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[1] = TabelaPalavrasReservadas.converter("ELSE".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[2] = TabelaPalavrasReservadas.converter("WHILE".toCharArray());
		TABELA_PALAVRAS_RESERVADAS[3] = TabelaPalavrasReservadas.converter("FOR".toCharArray());
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
			if(TABELA_PALAVRAS_RESERVADAS[i] != null && buffer.length == TABELA_PALAVRAS_RESERVADAS[i].length) {
				for(int j = 0; j < buffer.length; j++) {
					if(buffer[j] != TABELA_PALAVRAS_RESERVADAS[i][j])
						break;
					
					if(j == buffer.length - 1)
						return i;
				}
			}
		}
		
		return -1;
	}
}