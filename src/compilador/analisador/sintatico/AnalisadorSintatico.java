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
	
	public void teste() {
		try{
			Token token;
			
			while(lexico.haMaisTokens()) {
				token = lexico.proximoToken();
				this.ape.consumirToken(token);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		AnalisadorSintatico sintatico = new AnalisadorSintatico();
		sintatico.teste();
	}
	
}