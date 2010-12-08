package compilador.analisador.sintatico;

import java.io.IOException;

import compilador.analisador.lexico.AnalisadorLexico;
import compilador.analisador.lexico.Token;
import compilador.analisador.semantico.AcoesSemanticas;
import compilador.analisador.semantico.AnalisadorSemantico;
import compilador.analisador.semantico.Escopos;
import compilador.analisador.semantico.TabelaSimbolos;
import compilador.estruturas.ListaLigada;

public class AnalisadorSintatico {
	
	/**
	 * O autômato de pilha estruturado do analisador sintático.
	 */
	private AutomatoPilhaEstruturado ape;
	
	/**
	 * O analisador léxico do compilador.
	 */
	private AnalisadorLexico lexico;
	
	/**
	 * Lista que armazena os erros sintáticos do código-fonte.
	 */
	private ListaLigada<ErroSintatico> erros;
	
	public AnalisadorSintatico() {
		// Inicializa o analisador léxico.
		this.lexico = new AnalisadorLexico();
		this.lexico.carregarCodigoFonte("src/compilador/testes/source.ling");
		
		// Inicializa o APE.
		this.ape = new AutomatoPilhaEstruturado();
		
		// Inicializa a lista de erros sintáticos.
		this.erros = new ListaLigada<ErroSintatico>();
		
		// Inicializa a primeira tabela de símbolos.
		AcoesSemanticas.criarEscopo();
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
				AnalisadorSemantico.armazenarToken(token);
				this.ape.consumirToken(token);
				
				// Verifica se houve erro sintático no autômato de pilha.
				ErroSintatico erro = this.ape.getUltimoErroSintatico(); 
				if(erro != null)
					this.erros.put(erro);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		// Condição para que o código esteja sintaticamente correto.
		if(this.ape.estaNoEstadoAceitacao() && this.ape.pilhaVazia() && this.erros.vazia())
			return true;
		
		// Imprime os erros encontrados.
		for(int i = this.erros.tamanho() - 1; i >= 0; i--) {
			System.out.print("\nToken ");
			Token.getClasseTokenString(this.erros.get(i).getClasseToken()).imprimir();
			System.out.println(" inesperado. Linha " + this.erros.get(i).getLinha() + " - Coluna: " + this.erros.get(i).getColuna());
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		AnalisadorSintatico sintatico = new AnalisadorSintatico();
		boolean resultado = sintatico.processarCodigoFonte();
		
//		if(resultado)
//			System.out.println("\nRESULTADO: O programa esta sintaticamente correto.");
//		else
//			System.out.println("\nRESULTADO: O programa nao esta sintaticamente correto.");
	}
}