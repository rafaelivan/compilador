package compilador.estruturas;

public class Mapa<C, V> {
	
	/**
	 * Capacidade padr‹o do mapa.
	 */
	private static final int CAPACIDADE_PADRAO = 100;
	
	/**
	 * Array que armazena os itens do mapa.
	 */
	private ItemMapa<C, V>[] itens;
	
	/**
	 * O tamanho atual do mapa.
	 */
	private int tamanho;
	
	public Mapa() {
		this.itens = new ItemMapa[Mapa.CAPACIDADE_PADRAO];
		this.tamanho = 0;
	}
	
	/**
	 * Recupera um item do mapa.
	 * 
	 * @param chave
	 * @return o item correspondente a chave passada.
	 */
	public V get(C chave) {
		for(int i = 0; i < this.tamanho; i++)
			if(this.itens[i].getChave().equals(chave))
				return this.itens[i].getValor();
		
		return null;
	}
	
	/**
	 * Insere um item no mapa.
	 * 
	 * @param chave
	 * @param valor
	 */
	public void put(C chave, V valor) {
		boolean inserir = true;
		
		for(int i = 0; i < this.tamanho; i++) {
			if(this.itens[i].getChave().equals(chave)) {
				this.itens[i].setValor(valor);
				inserir = false;
			}
		}
		
		if(inserir) {
			this.itens[this.tamanho] = new ItemMapa<C, V>(chave, valor);
			this.tamanho++;
		}
		
	}
	
	/**
	 * Remove do mapa o item correspondete a chave passada.
	 * 
	 * @param chave
	 * @return
	 */
	public boolean remove(C chave) {
		for(int i = 0; i < this.tamanho; i++) {
			if(this.itens[i].getChave().equals(chave)) {
				this.itens[i] = null;
				this.condensarArray();
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @return o tamanho atual do mapa.
	 */
	public int tamanho() {
		return this.tamanho;
	}
	
	/**
	 * Recupera as chaves no mapa.
	 * @return uma lista ligada contendo as chaves.
	 */
	public ListaLigada<C> chaves() {
		ListaLigada<C> chaves = new ListaLigada<C>();
		
		for(ItemMapa<C, V> item : this.itens)
			if(item != null)
				chaves.put(item.getChave());
		
		return chaves;
	}
	
	private void condensarArray() {
		for(int i = 0; i < this.tamanho; i++) {
			if(this.itens[i] == null) {
				for(int j = i; j < this.tamanho; j++) {
					this.itens[j] = this.itens[j+1];
				}
				this.tamanho--;
				
				break;
			}
		}
	}
}