package compilador.analisador.lexico;

import compilador.estruturas.String;

public class ParametrosAcoesSemanticas {
	
	/*
	 * Categorias de símbolos.
	 */
	public static final int CATEGORIA_SIMBOLO_VARIAVEL = 0;
	public static final int CATEGORIA_SIMBOLO_FUNCAO = 1;
	public static final int CATEGORIA_SIMBOLO_VETOR = 2;
	public static final int CATEGORIA_SIMBOLO_MATRIZ = 3;
	public static final int CATEGORIA_SIMBOLO_PARAMETRO = 4;
	
	/*
	 * Mapeamento de tipos de símbolos.
	 */
	public static final int TIPO_SIMBOLO_INT = 0;
	public static final int TIPO_SIMBOLO_CHAR = 1;
	public static final int TIPO_SIMBOLO_BOOLEAN = 2;
	public static final int TIPO_SIMBOLO_STRING = 3;
	public static final int TIPO_SIMBOLO_VOID = 4;
	
	/*
	 * Mapeamento de compaadores.
	 */
	public static final int COMPARADOR_MENOR = 0;
	public static final int COMPARADOR_MAIOR = 1;
	public static final int COMPARADOR_IGUAL = 2;
	public static final int COMPARADOR_MENOR_IGUAL = 3;
	public static final int COMPARADOR_MAIOR_IGUAL  = 4;
	public static final int COMPARADOR_DIFERENTE = 5;
	
	public static Token TOKEN;						// O token referente ao símbolo.
	public static Token TOKEN_ID_ANTERIOR;	
	public static Token TOKEN_LADO_ESQUERDO_ATRIB;
	public static Token TOKEN_FUNCAO;
	public static int TIPO;							// Tipo do símbolo.
	public static int CATEGORIA;					// Categoria do símbolo.
	public static String ENDERECO;					// Endereço de 
	public static int TAMANHO;						// Tamanho do símbolo na memória.
	public static boolean DECLARADO;				// Se o símbolo já foi declarado.
	public static int[] PARAMETROS;					// Parâmetros passados (no caso de funções).
	public static int ID_FUNCAO;					// Id da função na tabela de símbolos.
	public static String ROTULO;					// Rótulo do símbolo.
	public static int COMPARADOR;
	
	public static void limparParametros() {
		TOKEN = null;
		TIPO = -1;
		CATEGORIA = -1;
		ENDERECO = null;
		TAMANHO = -1;
		DECLARADO = false;
		PARAMETROS = null;
	}
}