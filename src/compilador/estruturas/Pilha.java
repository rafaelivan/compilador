package compilador.estruturas;

public class Pilha<T> {
	
	/**
	 * O <code>No</code> que est‡ no topo da pilha.
	 */
	private No<T> topo;
	
	/**
	 * O tamanho atual da pilha.
	 */
	private int tamanho;
	
	public Pilha() {
		this.topo = null;
		this.tamanho = 0;
	}
	
	/**
	 * Adiciona um novo elemento na pilha.
	 * 
	 * @param elemento
	 */
	public void push(T elemento) {
		this.topo = new No<T>(elemento, this.topo);
		this.tamanho++;
	}
	
	/**
	 * Retira um elemento da pilha.
	 * 
	 * @return o elemento que estava no topo.
	 */
	public T pop() {
		if(this.vazia()) {
			// Se a pilha estiver vazia, retorna nulo.
			return null;
		}
		
		// H‡ pelo menos um elemento na pilha.
		T elemento = this.topo.getValor();
		
		this.topo = this.topo.proximo();
		this.tamanho--;
		
		return elemento;
	}
	
	/**
	 * Retorno o elemento do topo da pilha, mas n‹o o retira dela.
	 * @return o elemento do topo da pilha. 
	 */
	public T first() {
		if(this.vazia())
			return null;
		
		return this.topo.getValor();
	}
	
	/**
	 * Verifica se a pilha est‡ vazia.
	 * 
	 * @return <code>true</code>, caso a pilha esteja vazia. <code>false</code>, caso contr‡rio.
	 */
	public boolean vazia() {
		return !(this.tamanho > 0);
	}
}