package compilador.estruturas;

public class No<T> {
	
	/**
	 * Referência para o próximo nó.
	 */
	private No<T> proximo;
	
	/**
	 * Valor armazenado por este nó.
	 */
	private T valor;
	
	public No(T valor, No<T> proximo) {
		this.valor = valor;
		this.proximo = proximo;
	}
	
	/**
	 * @return o próximo <code>No</code>.
	 */
	public No<T> proximo() {
		return this.proximo;
	}
	
	/**
	 * Seta o próximo <code>No</code>.
	 * 
	 * @param proximo
	 */
	public void setProximo(No<T> proximo) {
		this.proximo = proximo;
	}
	
	/**
	 * @return o valor armazenado por este <code>No</code>.
	 */
	public T getValor() {
		return this.valor;
	}
}