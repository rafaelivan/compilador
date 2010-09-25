package compilador.analisador.semantico;

public class TabelaSimbolos {
	
	/*
	 * Implementação temporária da tabela de símbolos.
	 * */
	
	private static int[][] TABELA_SIMBOLOS = new int[100][];
	
	private static int PROXIMA_CHAVE = 0;
	
	public static int recuperarChave(int[] buffer) {
		for(int i = 0; i < TABELA_SIMBOLOS.length; i++) {
			if(TABELA_SIMBOLOS[i] != null && buffer.length == TABELA_SIMBOLOS[i].length) {
				for(int j = 0; j < buffer.length; j++) {
					if(buffer[j] != TABELA_SIMBOLOS[i][j])
						break;
					
					if(j == buffer.length - 1)
						return i;
				}
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
	
	public static void main(String[] args) {
		/*
		 * Teste da estrutura.
		 * */
		
		int[] a = new int[2];
		a[0] = 5;
		a[1] = 7;
		
		int[] b = new int[3];
		b[0] = 2;
		b[1] = 1;
		b[2] = 9;
		
		TabelaSimbolos.inserirSimbolo(a);
		TabelaSimbolos.inserirSimbolo(b);
		
		System.out.println(TABELA_SIMBOLOS[0].length);
		System.out.println(TABELA_SIMBOLOS[1].length);
		
		int[] c = new int[3];
		c[0] = 2;
		c[1] = 1;
		c[2] = 9;
		int chave = TabelaSimbolos.recuperarChave(c);
		
		System.out.println(chave);
	}
}