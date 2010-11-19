package compilador.analisador.lexico;

public class Token {
	
	/* Possíveis classes para os tokens. */
	public static final int CLASSE_TOKEN_INVALIDO = -1;
	public static final int CLASSE_TOKEN_NAO_FINALIZADO = 0;
	public static final int CLASSE_PALAVRA_RESERVADA = 1;
	public static final int CLASSE_IDENTIFICADOR = 2;
	public static final int CLASSE_CARACTER_ESPECIAL = 3;
	public static final int CLASSE_NUMERO_INTEIRO = 4;
	public static final int CLASSE_OUTROS_CARACTERES = 5;
	public static final int CLASSE_STRING = 6;
	// TODO: Adicionar as classes que faltarem quando definirmos a linguagem.
	
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
	
	public Token(int classe, int id) {
		this.classe = classe;
		this.id = id;
	}

	public int getClasse() {
		return classe;
	}

	public int getID() {
		return this.id;
	}
}