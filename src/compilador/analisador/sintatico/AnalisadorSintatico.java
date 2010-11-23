package compilador.analisador.sintatico;

import java.io.IOException;

import compilador.analisador.lexico.AnalisadorLexico;
import compilador.analisador.lexico.Token;

public class AnalisadorSintatico {
	
	/**
	 * O autômato de pilha estruturado do analisador sintático.
	 */
	private AutomatoPilhaEstruturado ape;
	
	/**
	 * O analisador léxico do compilador.
	 */
	private AnalisadorLexico lexico;
	
	public AnalisadorSintatico() {
		// Inicializa o analisador léxico.
		lexico = new AnalisadorLexico();
		lexico.carregarCodigoFonte("src/compilador/testes/source.ling");
		
		// Inicializa o APE.
		ape = new AutomatoPilhaEstruturado();
	}
	
	/**
	 * Processa o código fonte.
	 * 
	 * @return <code>true</code> caso o código-fonte esteja sintaticamente correto. <code>false</code> caso contrário.
	 */
	public boolean processarCodigoFonte() { 
		try{
			Token token;
			
			while(lexico.haMaisTokens()) {
				token = lexico.proximoToken();
				this.ape.consumirToken(token);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return this.ape.estaNoEstadoAceitacao();
	}
	
	public static void main(String[] args) {
		AnalisadorSintatico sintatico = new AnalisadorSintatico();
		boolean resultado = sintatico.processarCodigoFonte();
		
		if(resultado)
			System.out.println("RESULTADO: O programa esta sintaticamente correto.");
		else
			System.out.println("RESULTADO: O programa nao esta sintaticamente correto.");
	}
}