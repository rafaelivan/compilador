package compilador.analisador.lexico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import compilador.analisador.semantico.TabelaPalavrasReservadas;
import compilador.analisador.semantico.TabelaSimbolos;
import compilador.helper.ArrayHelper;

public class AnalisadorLexico {
	
	/**
	 * Autômato que reconhece os tokens da linguagem.
	 */
	private Transdutor transdutor;
	
	/**
	 * Aponta para o caracter atualmente a ser processo.
	 */
	private int ch;
	
	/**
	 * Aponta para o caracter seguinte ao atualmente em processamento.
	 */
	private int lookahead;
	
	/**
	 * Buffer para armazenar a representação do token.
	 */
	private int[] buffer;
	
	/**
	 * Indica se o armazenamento de caracteres no buffer está ativado.
	 */
	private boolean bufferAtivo;
	
	/**
	 * Indica se os Tokens do arquivo já se esgotaram.
	 */
	private boolean haMaisTokens; 
	
	/**
	 * FileInputStream que aponta para o arquivo do código-fonte.
	 */
	private FileInputStream arquivoCondigoFonte;
	
	/**
	 * Indica se o final do arquivo foi atingido.
	 */
	private boolean finalDoArquivo;
	
	public AnalisadorLexico() {
		this.buffer = new int[100];
		this.transdutor = new Transdutor();
		this.haMaisTokens = true;
		this.finalDoArquivo = false;
		this.bufferAtivo = true;
		this.ch = -1;
		this.lookahead = -1;
	}
	
	/**
	 * Ativa ou desativa o armazenamento de caracteres em buffer, dependendo da situação do compilador.
	 */
	private void gerenciarAtividadeBuffer() {
		if(this.transdutor.estaNoEstadoComentario() 
				|| this.ch == (int)' ' 
				|| this.ch == (int)'%' 
				|| this.ch == (int)'\n' 
				|| this.ch == (int)'\t') {
			this.bufferAtivo = false;
		} else {
			this.bufferAtivo = true;
		}
	}
	
	/**
	 * Abre o arquivo que contém o código-fonte.
	 */
	public void carregarCodigoFonte(String arquivo) {
		try {
			this.arquivoCondigoFonte = new FileInputStream(new File(arquivo));
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lê o código-fonte buscando o próximo Token.
	 * 
	 * @return o próximo Token encontrado.
	 */
	public Token proximoToken() throws IOException {
		// inicializa o buffer.
		for(int i = 0; i < 100; i++)
			this.buffer[i] = -1;
		
		int classe; // Armazenará a saída do autômato para uma dada transição.
		int chave; // Chave do Token na tabela correspondente à sua classe.
		int indice = 0; // Indexa o buffer.
		Token token; // Armazenará o token reconhecido.
		int[] bufferMinimo;
		
		// Coloca o primeiro carater no lookahead, pois ainda não iniciou o processamento.
		// Executa somente na primeira vez.
		if(this.lookahead == -1) {
			this.lookahead = arquivoCondigoFonte.read();
			
			if(this.lookahead == -1) {
				// Atingiu o final do arquivo.
				this.finalDoArquivo = true;
			}
		}
		
		while(true) {
			if(this.finalDoArquivo && this.ch == -1) {
				// Atingiu o final do arquivo e terminou de processar os caracteres.
				this.haMaisTokens = false;
				//break;
			}
			
			if(this.ch == -1) {
				this.ch = this.lookahead;
				this.lookahead = arquivoCondigoFonte.read();
				
				if(this.lookahead == -1)
					this.finalDoArquivo = true;
			}
			
			// Executa uma transição no autômato.
			classe = transdutor.transicao(this.ch);
			
			// Verifica se é necessário ativar ou desativar o armazenamento em buffer.
			this.gerenciarAtividadeBuffer();
			
			switch(classe) {
				case Token.CLASSE_TOKEN_NAO_FINALIZADO:
					// Lê mais caracteres do código-fonte.
					if(this.bufferAtivo) {
						// Se o armazenamento estiver ativo, guarda o caracter no buffer.
						this.buffer[indice] = this.ch;
						indice++;
					}
					this.ch = -1; // Sinaliza que já processou o caracter.
					break;
				case Token.CLASSE_PALAVRA_RESERVADA:
					bufferMinimo = ArrayHelper.alocarVetor(this.buffer);
					
					chave = TabelaPalavrasReservadas.recuperarChave(bufferMinimo);
					token = new Token(Token.CLASSE_PALAVRA_RESERVADA, chave);
					return token;
				case Token.CLASSE_IDENTIFICADOR:
					bufferMinimo = ArrayHelper.alocarVetor(this.buffer);
					
					chave = TabelaPalavrasReservadas.recuperarChave(bufferMinimo);
					
					if(chave == -1) {
						// Não está na tabela de palavras reservadas.
						
						chave = TabelaSimbolos.recuperarChave(bufferMinimo);
						if(chave == -1) {
							// Não está na tabela de símbolos.
							chave = TabelaSimbolos.inserirSimbolo(bufferMinimo);
						}
						
						token = new Token(Token.CLASSE_IDENTIFICADOR, chave);
					} else {
						// É uma palavra reservada.
						token = new Token(Token.CLASSE_PALAVRA_RESERVADA, chave);
					}
					
					return token;
				case Token.CLASSE_CARACTER_ESPECIAL:
					token = new Token(Token.CLASSE_CARACTER_ESPECIAL, this.buffer[0]);
					return token;
				case Token.CLASSE_NUMERO_INTEIRO:
					int numero = 0;
					
					for(int i = 0; i < this.buffer.length && this.buffer[i] != -1; i++) {
						numero = 10*numero + Integer.valueOf(String.valueOf(this.buffer[i]));
					}
					
					token = new Token(Token.CLASSE_NUMERO_INTEIRO, numero);
					return token;
			}
			
			if(!this.haMaisTokens)
				break;
		}
		
		return new Token(Token.CLASSE_TOKEN_INVALIDO, -1);
	}
	
	/**
	 * Verifica se há mais tokens no código-fonte.
	 * @return <code>true</code>, caso haja mais Tokens. <code>false</code>, caso contrário.
	 */
	public boolean haMaisTokens() {
		return this.haMaisTokens;
	}
	
	public static void main(String[] args) {
		AnalisadorLexico lexico = new AnalisadorLexico();
		lexico.carregarCodigoFonte("src/compilador/testes/source.ling");
		
		try{
			Token token;
			
			while(lexico.haMaisTokens()) {
				token = lexico.proximoToken();
				System.out.println("Token.classe: " + token.getClasse());
				System.out.println("Token.representacao: " + token.getID() + "\n");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}