package compilador.estruturas;

public class ItemMapa<C, V> {
	
	/**
	 * A chave deste item do mapa.
	 */
	private final C chave;
	
	/**
	 * O valor deste item do mapa.
	 */
	private V valor;
	
	public ItemMapa(C chave, V valor) {
		this.chave = chave;
		this.valor = valor;
	}
	
	public V getValor() {
		return valor;
	}

	public void setValor(V valor) {
		this.valor = valor;
	}

	public C getChave() {
		return chave;
	}
}