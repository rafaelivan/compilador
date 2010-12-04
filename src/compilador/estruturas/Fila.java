package compilador.estruturas;

public class Fila<T> {
	
	/**
	 * O <code>No</code> que est‡ no in’cio da fila.
	 */
	private No<T> inicio;
	
	/**
	 * O <code>No</code> que est‡ no fim da fila.
	 */
	private No<T> fim;
	
	/**
	 * O tamanho da fila.
	 */
	private int tamanho;
	
	public Fila() {
		this.inicio = null;
		this.fim = null;
		this.tamanho = 0;
	}
	
	/**
	 * Retira um elemento do in’cio da fila.
	 * @return
	 */
	public T poll() {
		if(this.tamanho > 0) {
			No<T> no = this.inicio;
			this.inicio = this.inicio.proximo();
			this.tamanho--;
			return no.getValor();
		}
		
		return null;
	}
	
	/**
	 * Retorna o elemento no in’cio da fila, mas n‹o o retira dela.
	 * @return
	 */
	public T peek() {
		return this.inicio.getValor();
	}
	
	/**
	 * Adiciona um elemento ao final da fila.
	 * @param elemento
	 */
	public void add(T elemento) {
		No<T> no = new No<T>(elemento, null);
		
		if(this.tamanho == 0) {
			this.inicio = no;
			this.fim = no;
		} else {
			this.fim.setProximo(no);
			this.fim = no;
		}
		
		this.tamanho++;
	}
	
	/**
	 * @return o tamanho da fila.
	 */
	public int tamanho() {
		return this.tamanho;
	}
}
