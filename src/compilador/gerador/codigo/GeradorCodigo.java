package compilador.gerador.codigo;

import compilador.estruturas.String;
import compilador.helper.ArrayHelper;

public class GeradorCodigo {
	
	/**
	 * Buffer com as informações a serem inseridas na área de dados do código-objeto.
	 */
	private BufferDados<String> bufferDados;
	
	/**
	 * Buffer com as informações a serem inseridas na área de código do código-objeto.
	 */
	private BufferCodigo<String> bufferCodigo;
	
	/**
	 * Endereço (hexadecimal) do início da área de código.
	 */
	private static final int ENDERECO_AREA_CODIGO = 0;
	
	/**
	 * Endereço (hexadecimal) do início da área de dados.
	 */
	private static final int ENDERECO_AREA_DADOS = 200;
	
	public GeradorCodigo() {
		this.bufferCodigo = new BufferCodigo<String>();
		this.bufferDados = new BufferDados<String>();
	}
	
	/**
	 * Adiciona uma entrada na área de dados.
	 * @param str
	 */
	public void addAreaDados(String str) {
		this.bufferDados.add(str);
	}
	
	/**
	 * Adiciona uma entrada na área de código.
	 * @param str
	 */
	public void addAreaCodigo(String str) {
		this.bufferCodigo.add(str);
	}
	
	/**
	 * Gera o código-objeto completo.
	 * @return
	 */
	public String gerarCodigo() {
		String codigo = new String(("@ /"+GeradorCodigo.ENDERECO_AREA_CODIGO+"\n").toCharArray());
		
		while(!this.bufferCodigo.estaVazio())
			codigo = new String(ArrayHelper.concatenarVetoresChar(codigo.toCharArray(), this.bufferCodigo.proximo().toCharArray()));
		
		codigo = new String(ArrayHelper.concatenarVetoresChar(codigo.toCharArray(), ("@ /"+GeradorCodigo.ENDERECO_AREA_DADOS+"\n").toCharArray()));
		
		while(!this.bufferDados.estaVazio())
			codigo = new String(ArrayHelper.concatenarVetoresChar(codigo.toCharArray(), this.bufferDados.proximo().toCharArray()));
		
		return codigo;
	}
}