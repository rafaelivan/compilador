package compilador.analisador.sintatico;

import compilador.analisador.lexico.Token;
import compilador.estruturas.ListaLigada;
import compilador.estruturas.String;

public class Submaquina {
	
	/**
	 * Indica um estado inválido.
	 */
	public static final int ESTADO_INVALIDO = -1;
	
	/**
	 * Indica que a transição ocorreu normalmente.
	 */
	public static final int TRANSICAO_OK = 10;
	
	/**
	 * Indica que a transição é de chamada de submáquina.
	 */
	public static final int TRANSICAO_CHAMADA_SUBMAQUINA = 11;
	
	/**
	 * O nome da submáquina.
	 */
	private String nome;
	
	/**
	 * O estado inicial da submáquina.
	 */
	private int estadoInicial;
	
	/**
	 * Array com os estados iniciais da submáquina.
	 */
	private int[] estadosFinais;
	
	/**
	 * Estado atual da submáquina.
	 */
	private int estadoAtual;
	
	/**
	 * Número de estados da submáquina.
	 */
	private int numeroEstados;
	
	/**
	 * Tabela de transição de estados da submáquina.
	 */
	private int[][][] tabelaTransicao;
	
	/**
	 * Tabela com a relação de quais submáquinas podem ser chamadas em cada estado.
	 */
	private int[][] tabelaChamadaSubmaquinas;
	
	public Submaquina(String nome, int estadoInicial, int[] estadosFinais, int[][][] tabelaTransicao, int[][] tabelaChamadaSubmaquinas) {
		this.nome = nome;
		this.estadoInicial = this.estadoAtual = estadoInicial;
		this.estadosFinais = estadosFinais;
		this.tabelaTransicao = tabelaTransicao;
		this.numeroEstados = this.tabelaTransicao.length;
		this.tabelaChamadaSubmaquinas = tabelaChamadaSubmaquinas;
	}
	
	/**
	 * @return o nome da submáquina.
	 */
	public String getNome() {
		return this.nome;
	}
	
	/**
	 * @return o estado inicial da submáquina.
	 */
	public int getEstadoInicial() {
		return this.estadoInicial;
	}
	
	/**
	 * @return o estado atual da submáquina.
	 */
	public int getEstadoAtual() {
		return this.estadoAtual;
	}
	
	/**
	 * Seta o estado atual da sumáquina.
	 * 
	 * @param estadoAutal
	 */
	public void setEstadoAtual(int estadoAutal) {
		this.estadoAtual = estadoAutal;
	}
	
	/**
	 * Executa uma transição na submáquina.
	 * 
	 * @param token
	 * @return
	 */
	public int transicao(Token token) {
		int proximoEstado = this.tabelaTransicao[this.estadoAtual][token.getClasse()][token.getID()];
		
		System.out.println("Prox estado : " + proximoEstado);
		System.out.println();
		
		if(proximoEstado == ESTADO_INVALIDO) {
			// Deve tentar chamar uma submáquina.
			return TRANSICAO_CHAMADA_SUBMAQUINA;
		} else {
			// Há transição disponível.
			this.estadoAtual = proximoEstado;
			return TRANSICAO_OK;
		}
	}
	
	/**
	 * Identifica qual submáquina deve ser chamada.
	 * 
	 * @param token
	 * @return o identificador da submáquina a ser chamada.
	 */
	public ListaLigada<Integer> possiveisSubmaquinas(Token token) {
		ListaLigada<Integer> possiveisSubmaquinas = new ListaLigada<Integer>();
		
		// Identificar as submáquinas que podem ser chamadas neste estado.
		for(int i = 0; i < this.tabelaChamadaSubmaquinas[this.estadoAtual].length; i++)
			if(this.tabelaChamadaSubmaquinas[this.estadoAtual][i] != -1)
				possiveisSubmaquinas.put(i);
		
		return possiveisSubmaquinas;
	}
	
	/**
	 * Verifica se a submáquina possui transição para o token dado.
	 * 
	 * @param token 
	 * @return <code>true</code>, caso exista a transição. <code>false</code>, caso não exista.
	 */
	public boolean haTransicao(Token token) {
		int proximoEstado = this.tabelaTransicao[this.estadoAtual][token.getClasse()][token.getID()];
		
		if(proximoEstado == ESTADO_INVALIDO)
			return false;
		return true;
	}
	
	/**
	 * Executa uma transição de estado baseada em chamada de submáquina.
	 * Não chama efetivamente outra submáquina, apenas verifica a possibilidade e muda para o estado de retorno.
	 * 
	 * @param idSubmaquina
	 * @return 
	 */
	public boolean chamarSubmaquina(int idSubmaquina) {
		int proximoEstado = this.tabelaChamadaSubmaquinas[this.estadoAtual][idSubmaquina];
		
		if(proximoEstado == ESTADO_INVALIDO)
			return false;
		
		this.estadoAtual = proximoEstado;
		return true;
	}
}