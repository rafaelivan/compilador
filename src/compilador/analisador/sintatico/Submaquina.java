package compilador.analisador.sintatico;

public class Submaquina {
	
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
	
	public Submaquina(int estadoInicial, int[] estadosFinais, int[][][] tabelaTransicao) {
		this.estadoInicial = this.estadoAtual = estadoInicial;
		this.estadosFinais = estadosFinais;
		this.tabelaTransicao = tabelaTransicao;
		this.numeroEstados = this.tabelaTransicao.length;
	}
	
}