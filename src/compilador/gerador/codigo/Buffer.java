package compilador.gerador.codigo;

import compilador.estruturas.Fila;

public class Buffer<T> {
	
	/**
	 * Fila que armazena os itens do buffer.
	 */
	private Fila<T> buffer;
	
	public Buffer() {
		this.buffer = new Fila<T>();
	}
	
	/**
	 * Adiciona um item no buffer.
	 * @param item
	 */
	public void add(T item) {
		this.buffer.add(item);
	}
	
	/**
	 * Remove item no in’cio do buffer.
	 * @return
	 */
	public T proximo() {
		return this.buffer.poll();
	}
	
	/**
	 * Verifica se o buffer est‡ vazio.
	 * @return <code>true</code> caso o buffe esteja vazio. <code>false</code>, caso contr‡rio.
	 */
	public boolean estaVazio() {
		return (this.buffer.tamanho() == 0);
	}
}