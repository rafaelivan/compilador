package compilador.analisador.sintatico;


public class Par {
	
	/**
	 * A subm‡quina a ser empilhada
	 */
	private Submaquina submaquina;
	
	/**
	 * O estado a partir do qual a subm‡quina precisa continuar a executar.
	 */
	private int estado;
	
	public Par(Submaquina submaquina, int estado) {
		this.submaquina = submaquina;
		this.estado = estado;
	}
	
	public Submaquina getSubmaquina() {
		return submaquina;
	}

	public int getEstado() {
		return estado;
	}
}