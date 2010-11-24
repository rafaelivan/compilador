package compilador.analisador.lexico;

import compilador.estruturas.String;

public class Token {
	
	/* 
	 * Possíveis classes para os tokens. 
	 */
	public static final int CLASSE_TOKEN_INVALIDO = -1;
	public static final int CLASSE_TOKEN_NAO_FINALIZADO = 0;
	public static final int CLASSE_PALAVRA_RESERVADA = 1;
	public static final int CLASSE_IDENTIFICADOR = 2;
	public static final int CLASSE_CARACTER_ESPECIAL = 3;
	public static final int CLASSE_NUMERO_INTEIRO = 4;
	public static final int CLASSE_OUTROS_CARACTERES = 5;
	public static final int CLASSE_STRING = 6;
	
	/*
	 * Nome de cada classe de token.
	 */
	public static final String CLASSE_PALAVRA_RESERVADA_STR = new String("PALAVRA RESERVADA".toCharArray());
	public static final String CLASSE_IDENTIFICADOR_STR = new String("IDENTIFICADOR".toCharArray());
	public static final String CLASSE_CARACTER_ESPECIAL_STR = new String("CARACTER ESPECIAL".toCharArray());
	public static final String CLASSE_NUMERO_INTEIRO_STR = new String("NUMERO INTEIRO".toCharArray());
	public static final String CLASSE_OUTROS_CARACTERES_STR = new String("OUTROS CARACTERES".toCharArray());
	public static final String CLASSE_STRING_STR = new String("STRING".toCharArray());
	
	/**
	 * A classe do Token.
	 */
	private int classe;
	
	/**
	 * Identificador do Token na tabela correspondende à sua classe.
	 * Para algumas classes, como a <code>Token.CLASSE_NUMERO_INTEIRO</code>, 
	 * esse campo contém a própria representação do Token.
	 */
	private int id;
	
	/**
	 * A linha onde o Token estava no código-fonte.
	 */
	private int linha;
	
	/**
	 * A coluna onde o Token estava no código-fonte.
	 */
	private int coluna;
	
	/**
	 * Retorna a classe do token na forma de uma String.
	 * 
	 * @param classe
	 * @return
	 */
	public static String getClasseTokenString(int classe) {
		switch(classe){
			case Token.CLASSE_PALAVRA_RESERVADA:
				return Token.CLASSE_PALAVRA_RESERVADA_STR;
			case Token.CLASSE_IDENTIFICADOR:
				return Token.CLASSE_IDENTIFICADOR_STR;
			case Token.CLASSE_CARACTER_ESPECIAL:
				return Token.CLASSE_CARACTER_ESPECIAL_STR;
			case Token.CLASSE_NUMERO_INTEIRO:
				return Token.CLASSE_NUMERO_INTEIRO_STR;
			case Token.CLASSE_OUTROS_CARACTERES:
				return Token.CLASSE_OUTROS_CARACTERES_STR;
			case Token.CLASSE_STRING:
				return Token.CLASSE_STRING_STR;
			default:
				return null;
		
		}
	}
	
	public Token(int classe, int id) {
		this.classe = classe;
		this.id = id;
	}

	public int getClasse() {
		return this.classe;
	}

	public int getID() {
		return this.id;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}
}