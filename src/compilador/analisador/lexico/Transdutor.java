package compilador.analisador.lexico;

public class Transdutor {
	
	/**
	 * Indica um estado inválido.
	 */
	public static final int ESTADO_INVALIDO = -1;
	
	/**
	 * Indica o estado inicial de qualquer autômato.
	 */
	public static final int ESTADO_INICIAL = 0;
	
	/**
	 * Indica um estado que trata comentários do código-fonte.
	 */
	public static final int ESTADO_COMENTARIO = 4; //TODO: deixar mais flexível.
	
	/**
	 * Número de estados do autômato.
	 */
	private int estados;
	
	/**
	 * Indica o estado atual no qual o autômato se encontra.
	 */
	private int estadoAtual;
	
	/**
	 * Tabela com as transições do autômato.
	 */
	private int[][] tabelaTransicao;
	
	/**
	 * Tabela contendo as classes de tokens para cada estado do autômato.
	 * Se o estado não for final, a classe adotada será a <code>Token.CLASSE_TOKEN_INVALIDO</code>.
	 */
	private int[] tabelaClasses;
	
	/**
	 * Indica se o transdutor está ou não no estado que trata comentários.
	 */
	private boolean estadoComentario;
	
	public Transdutor() {
		this.carregaTabelaTransicao();
		this.carregaTabelaClasses();
		this.estadoAtual = ESTADO_INICIAL;
		this.estadoComentario = false;
	}
	
	/**
	 * Carrega as tabela de transição de estados.
	 */
	private void carregaTabelaTransicao() {
		this.estados = 8;
		this.tabelaTransicao = new int[this.estados][256];
		
		for(int i = 0; i < this.estados; i++) {
			for(int j = 0; j < 256; j++) {
				this.tabelaTransicao[i][j] = -1;
			}
		}
		
		/* Transições do estado 0. */
		for(int i = (int)'A'; i <= (int)'Z'; i++) {
			this.tabelaTransicao[0][i] = 1;
		}
		
		for(int i = (int)'a'; i <= (int)'z'; i++) {
			this.tabelaTransicao[0][i] = 1;
		}
		
		for(int i = (int)'0'; i <= (int)'9'; i++) {
			this.tabelaTransicao[0][i] = 2;
		}
		
		this.tabelaTransicao[0][(int)' '] = 0;
		this.tabelaTransicao[0][(int)'\n'] = 0;
		this.tabelaTransicao[0][(int)'\t'] = 0;
		
		this.tabelaTransicao[0][(int)';'] = 3;
		this.tabelaTransicao[0][(int)','] = 3;
		this.tabelaTransicao[0][(int)'+'] = 3;
		this.tabelaTransicao[0][(int)'-'] = 3;
		this.tabelaTransicao[0][(int)'*'] = 3;
		this.tabelaTransicao[0][(int)'/'] = 3;
		this.tabelaTransicao[0][(int)'('] = 3;
		this.tabelaTransicao[0][(int)')'] = 3;
		this.tabelaTransicao[0][(int)'['] = 3;
		this.tabelaTransicao[0][(int)']'] = 3;
		this.tabelaTransicao[0][(int)'{'] = 3;
		this.tabelaTransicao[0][(int)'}'] = 3;
		this.tabelaTransicao[0][(int)'.'] = 3;
		this.tabelaTransicao[0][(int)'>'] = 3;
		this.tabelaTransicao[0][(int)'='] = 3;
		this.tabelaTransicao[0][(int)'<'] = 3;
		this.tabelaTransicao[0][(int)'%'] = 3;
		this.tabelaTransicao[0][(int)'!'] = 3;
		
		this.tabelaTransicao[0][(int)'"'] = 6;
		
		for(int i = 0; i < 256; i++) {
			if(this.tabelaTransicao[0][i] == -1)
				this.tabelaTransicao[0][i] = 5;
		}
		
		/* Transições do estado 1. */
		for(int i = (int)'A'; i <= (int)'Z'; i++) {
			this.tabelaTransicao[1][i] = 1;
		}
		
		for(int i = (int)'a'; i <= (int)'z'; i++) {
			this.tabelaTransicao[1][i] = 1;
		}
		
		for(int i = (int)'0'; i <= (int)'9'; i++) {
			this.tabelaTransicao[1][i] = 1;
		}
		
		/* Transições do estado 2 */
		for(int i = (int)'0'; i <= (int)'9'; i++) {
			this.tabelaTransicao[2][i] = 2;
		}
		
		/* Transições do estado 3. */
		this.tabelaTransicao[3][(int)'/'] = 4;
		
		this.tabelaTransicao[3][(int)'='] = 1; // Para o caso de ==, <=, >= e !=.
		
		/* Transições do estado 4. */
		for(int i = 0; i < 256; i++) {
			this.tabelaTransicao[4][i] = 4;
		}
		this.tabelaTransicao[4][(int)'\n'] = 0;
		
		/* Transições do estado 5. */
		
		/* Transições do estado 6. */
		for(int i = 0; i < 256; i++) {
			this.tabelaTransicao[6][i] = 6;
		}
		
		this.tabelaTransicao[6][(int)'"'] = 7;
		
		/* Transições do estado 7. */
		
	}
	
	/**
	 * Carrega a tabela de classes dos estados finais do autômato.
	 */
	private void carregaTabelaClasses() {
		this.tabelaClasses = new int[this.estados];
		for(int i = 0; i < this.tabelaClasses.length; i++) {
			this.tabelaClasses[i] = Token.CLASSE_TOKEN_INVALIDO;
		}
		
		/* Registra os estados finais. */
		this.tabelaClasses[1] = Token.CLASSE_IDENTIFICADOR;
		this.tabelaClasses[2] = Token.CLASSE_NUMERO_INTEIRO;
		this.tabelaClasses[3] = Token.CLASSE_CARACTER_ESPECIAL;
		this.tabelaClasses[7] = Token.CLASSE_STRING;
	}
	
	/**
	 * Recebe uma entrada e executa uma transição no autômato.
	 * 
	 * @param entrada código ASCII decimal do caracter de entrada.
	 * @return a classe do token reconhecido até o momento.
	 */
	public int transicao(int entrada) {
		int proximoEstado; 
		int saida;
		
		/* Verifica se a entrada é um caracter ASCII válido. */
		if(entrada < 0){
			proximoEstado = ESTADO_INVALIDO;
		} else {
			// Efetua uma transição determinística.
			proximoEstado = this.tabelaTransicao[this.estadoAtual][entrada];
		}
		
		/* Decide qual a classe de token correspondente à situação atual do autômato. */
		if(proximoEstado == ESTADO_INVALIDO) {
			// Terminou o reconhecimento do token, pois não achou transição.
			saida = this.tabelaClasses[this.estadoAtual];
			
			// Faz transição com cadeia vazia para o estado inicial.
			this.estadoAtual = ESTADO_INICIAL;
		} else {
			// Ainda não chegou ao final do reconhecimento.
			saida = Token.CLASSE_TOKEN_NAO_FINALIZADO;
			this.estadoAtual = proximoEstado;
		}
		
		/* Se foi para um estado de comentário, sinaliza isso. */
		if(this.estadoAtual == ESTADO_COMENTARIO) {
			this.estadoComentario = true;
		} else {
			this.estadoComentario = false;
		}
		
		return saida;
	}
	
	/**
	 * Verfica qual a classe dos tokens que são reconhecidos pelo atual estado do autômato
	 * @param estado
	 * @return a classe do token. Se o estado não for final, retorna -1.
	 */
	public int classeEstadoAtual() {
		return this.tabelaClasses[this.estadoAtual];
	}
	
	/**
	 * Verifica se o transdutor está no estado de comentário.
	 * 
	 * @return <code>true</code>, caso o transdutor esteja em uma estado de comentário. <code>false</code>, caso contrário.
	 */
	public boolean estaNoEstadoComentario() {
		return this.estadoComentario;
	}
	
}