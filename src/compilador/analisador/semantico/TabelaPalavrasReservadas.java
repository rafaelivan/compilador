package compilador.analisador.semantico;

import compilador.helper.ArrayHelper;

public class TabelaPalavrasReservadas {
	
	/**
	 * Instância do Singleton.
	 */
	private volatile static TabelaPalavrasReservadas INSTANCE;
	
	/**
	 * Representação da tabela de palavras reservadas.
	 */
	private int[][] tabelaPalavrasReservadas;
	
	/**
	 * @return a instância Singleton.
	 */
	public static TabelaPalavrasReservadas getInstance() {
		if(INSTANCE == null) {
			synchronized (TabelaPalavrasReservadas.class) {
				if(INSTANCE == null) {
					INSTANCE = new TabelaPalavrasReservadas();
				}
			}
		}
		
		return INSTANCE;
	}
	
	private TabelaPalavrasReservadas() {
		this.tabelaPalavrasReservadas = new int[20][];
		
		this.tabelaPalavrasReservadas[0] = this.converter("if".toCharArray());
		this.tabelaPalavrasReservadas[1] = this.converter("else".toCharArray());
		this.tabelaPalavrasReservadas[2] = this.converter("while".toCharArray());
		this.tabelaPalavrasReservadas[3] = this.converter("int".toCharArray());
		this.tabelaPalavrasReservadas[4] = this.converter("char".toCharArray());
		this.tabelaPalavrasReservadas[5] = this.converter("void".toCharArray());
		this.tabelaPalavrasReservadas[6] = this.converter("boolean".toCharArray());
		this.tabelaPalavrasReservadas[7] = this.converter("true".toCharArray());
		this.tabelaPalavrasReservadas[8] = this.converter("false".toCharArray());
		this.tabelaPalavrasReservadas[9] = this.converter("typedef".toCharArray());
		this.tabelaPalavrasReservadas[10] = this.converter("struct".toCharArray());
		this.tabelaPalavrasReservadas[11] = this.converter("const".toCharArray());
		this.tabelaPalavrasReservadas[12] = this.converter("write".toCharArray());
		this.tabelaPalavrasReservadas[13] = this.converter("read".toCharArray());
		this.tabelaPalavrasReservadas[14] = this.converter("return".toCharArray());
		this.tabelaPalavrasReservadas[15] = this.converter("main".toCharArray());
		this.tabelaPalavrasReservadas[16] = this.converter("==".toCharArray());
		this.tabelaPalavrasReservadas[17] = this.converter("<=".toCharArray());
		this.tabelaPalavrasReservadas[18] = this.converter(">=".toCharArray());
		this.tabelaPalavrasReservadas[19] = this.converter("!=".toCharArray());
	}
	
	private int[] converter(char[] arr) {
		int[] vetor = new int[arr.length];
		
		for(int i = 0; i < arr.length; i++) {
			vetor[i] = (int)arr[i];
		}
		
		return vetor;
	}
	
	public int recuperarChave(int[] buffer) {
		for(int i = 0; i < this.tabelaPalavrasReservadas.length; i++) {
			if(this.tabelaPalavrasReservadas[i] != null && ArrayHelper.compararVetoresInt(this.tabelaPalavrasReservadas[i], buffer)) {
				return i;
			}
		}
		
		return -1;
	}
}