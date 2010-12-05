package compilador.gerador.codigo;

import java.io.File;
import java.io.FileOutputStream;

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
	 * @return uma <code>compilador.estruturas.String</code> contendo todo o código-objeto gerado.
	 */
	public String gerarCodigo() {
		String codigo = new String(("@ /"+GeradorCodigo.ENDERECO_AREA_CODIGO+"\n\tJP\tMAIN\n\n").toCharArray());
		
		while(!this.bufferCodigo.estaVazio())
			codigo = new String(ArrayHelper.concatenarVetoresChar(codigo.toCharArray(), this.bufferCodigo.proximo().toCharArray()));
		
		codigo = new String(ArrayHelper.concatenarVetoresChar(codigo.toCharArray(), ("\n@ /"+GeradorCodigo.ENDERECO_AREA_DADOS+"\n").toCharArray()));
		
		while(!this.bufferDados.estaVazio())
			codigo = new String(ArrayHelper.concatenarVetoresChar(codigo.toCharArray(), this.bufferDados.proximo().toCharArray()));
		
		this.gerarArquivoObjeto(codigo);
		
		return codigo;
	}
	
	private void gerarArquivoObjeto(String codigo) {
		try {
			FileOutputStream fos = new FileOutputStream(new File("src/compilador/testes/source.asm"));
			fos.write(codigo.toString().getBytes());
			fos.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}