package compilador.analisador.semantico;

import compilador.estruturas.Int;
import compilador.estruturas.ListaLigada;

public class TabelaNumeros {
	
	private ListaLigada<Int> numeros;
	
	/**
	 * Inst‰ncia do Singleton.
	 */
	private volatile static TabelaNumeros INSTANCE;
	
	private TabelaNumeros() {
		this.numeros = new ListaLigada<Int>();
	}
	
	/**
	 * @return a inst‰ncia Singleton.
	 */
	public static TabelaNumeros getInstance() {
		if(INSTANCE == null) {
			synchronized (TabelaNumeros.class) {
				if(INSTANCE == null) {
					INSTANCE = new TabelaNumeros();
				}
			}
		}
		
		return INSTANCE;
	}
	
	public void inserirNumero(int n) {
		if(!this.numeroNaLista(n))
			this.numeros.put(new Int(n));
	}
	
	public boolean numeroNaLista(int n) {		
		int chave = this.numeros.localiza(new Int(n)); 
		return !(chave == -1);
	}
	
	public ListaLigada<Int> getNumeros() {
		return this.numeros;
	}
}