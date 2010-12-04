package compilador.analisador.sintatico;

import compilador.analisador.lexico.Token;
import compilador.analisador.semantico.AnalisadorSemantico;
import compilador.estruturas.ListaLigada;
import compilador.estruturas.Mapa;
import compilador.estruturas.Pilha;
import compilador.estruturas.String;
import compilador.gerador.submaquinas.GeradorSubmaquinas;
import compilador.helper.ArrayHelper;

public class AutomatoPilhaEstruturado {
	
	/**
	 * O nome da submáquina inicial.
	 */
	public static final String SUBMAQUINA_INICIAL = new String("programa".toCharArray());
	
	/**
	 * Array com todas as submáquinas do APE.
	 */
	private Submaquina[] submaquinas;
	
	/**
	 * Utilizado pelo <code>GeradorSubmaquinas</code>. Relaciona o nome da submáquinha com seu identificador.
	 */
	private Mapa<String, Integer> mapa;
	
	/**
	 * A pilha que gerenciará as transições entre submáquinas.
	 */
	private Pilha<Par> pilha;
	
	/**
	 * A submáquina que está atualmente em execução.
	 */
	private Submaquina submaquinaAtual;
	
	/**
	 * O estado atual da submáquina atualmente em execução.
	 */
	private int estadoAtual;
	
	/**
	 * O último erro sintático encontrado.
	 */
	private ErroSintatico ultimoErro;
	
	/**
	 * O analisador semântico do compilador.
	 */
	private AnalisadorSemantico semantico;
	
	public AutomatoPilhaEstruturado() {
		this.inicializaSubmaquinas();
		
		this.submaquinaAtual = this.encontrarSubmaquina(AutomatoPilhaEstruturado.SUBMAQUINA_INICIAL);
		this.estadoAtual = this.submaquinaAtual.getEstadoInicial();
		
		this.pilha = new Pilha<Par>();
		
		// Inicializa o analisador semântico.
		this.semantico = new AnalisadorSemantico();
	}
	
	/**
	 * Consume um token executando as transições necessárias nas submáquinas.
	 * 
	 * @param token o token a ser consumido.
	 */
	public void consumirToken(Token token) {
		this.semantico.executarAcaoSemantica(this.submaquinaAtual.getNome().toString(), this.estadoAtual, token);
		
		int statusTransicao = this.submaquinaAtual.transicao(token);
		
		if(statusTransicao == Submaquina.TRANSICAO_OK) {
			this.estadoAtual = this.submaquinaAtual.getEstadoAtual();
			
		} else if(statusTransicao == Submaquina.TRANSICAO_FALHOU) {
			// Se não conseguir chamar outra submáquina, tenta retornar.
			
			if(!this.chamarSubmaquina(token)) {
				// Se não for possível chamar outra submáquina. 
				if(!this.retornarParaSubmaquina(token)) {
					// Se não for possível retornar.
					
					this.ultimoErro = new ErroSintatico(token.getLinha(), token.getColuna(), token.getClasse());
				}
			}
		}
	}
	
	/**
	 * Localiza uma submáquina a partir do seu nome.
	 * 
	 * @param nome
	 * @return
	 */
	private Submaquina encontrarSubmaquina(String nome) {
		for(int i = 0; i < this.submaquinas.length; i++) {
			if(this.submaquinas[i] != null && this.submaquinas[i].getNome().equals(nome))
				return this.submaquinas[i];
		}
		
		return null;
	}
	
	/**
	 * Chama uma submáquina a partir da submáquina atual.
	 * 
	 * @param token
	 * @return
	 */
	private boolean chamarSubmaquina(Token token) {
		boolean statusChamada = false;
		
		ListaLigada<Integer> possiveisSubmaquinas = this.submaquinaAtual.possiveisSubmaquinas(token);
		
		// Verfica as transições das submáquinas com base no token.
		ListaLigada<Integer> submaquinasComTransicao = new ListaLigada<Integer>();
		for(int i = 0; i < possiveisSubmaquinas.tamanho(); i++)
			if(this.submaquinas[possiveisSubmaquinas.get(i)].haTransicao(token) || this.submaquinas[possiveisSubmaquinas.get(i)].haChamadaSubmaquina())
				submaquinasComTransicao.put(possiveisSubmaquinas.get(i));
		
		// TODO: chamar a submáquina correta. Por enquanto enquanto a primeira é sempre chamada.
		Integer idSubmaquina = submaquinasComTransicao.get(0);
		if(idSubmaquina != null && this.submaquinaAtual.chamarSubmaquina(idSubmaquina)){
			// Empilha a situação atual.
			this.estadoAtual = this.submaquinaAtual.getEstadoAtual();
			Par par = new Par(this.submaquinaAtual, this.estadoAtual);
			this.pilha.push(par);
			
			// Chama a outra submáquina.
			this.submaquinaAtual = this.submaquinas[idSubmaquina];
			this.submaquinaAtual.setEstadoAtual(this.submaquinaAtual.getEstadoInicial());
			this.estadoAtual = this.submaquinaAtual.getEstadoAtual();
			
			// Chama novamente o método que consome o Token.
			this.consumirToken(token);
			statusChamada = true;
		}
		
		return statusChamada;
	}
	
	/**
	 * Faz um retorno a partir da submáquina atual.
	 * 
	 * @param token
	 * @return
	 */
	private boolean retornarParaSubmaquina(Token token) {
		boolean statusRetorno = false;
		
		// Verifica se o estado atual é final.
		if(ArrayHelper.elementoNoVetor(this.submaquinaAtual.getEstadosFinais(), this.submaquinaAtual.getEstadoAtual())) {
			// Volta a submáquina que terminou a execução para o estado inicial.
			this.submaquinaAtual.setEstadoAtual(this.submaquinaAtual.getEstadoInicial());
			
			// Desempilha a situação para a qual o retorno será feito.
			Par par = this.pilha.pop();
			this.submaquinaAtual = par.getSubmaquina();
			this.estadoAtual = par.getEstado();
			this.submaquinaAtual.setEstadoAtual(this.estadoAtual);
			
			// Chama novamente o método que consome o Token.
			this.consumirToken(token);
			statusRetorno = true;
		}
		
		return statusRetorno;
	}
	
	/**
	 * Verifica se o APE está no estado de aceitação da submáquina principal.
	 * 
	 * @return <code>true<code>, caso esteja no estado de aceitação da submáquina principal. <code>false</code>, caso contrário.
	 */
	public boolean estaNoEstadoAceitacao() {
		if(this.submaquinaAtual.getNome().equals(SUBMAQUINA_INICIAL) 
				&& ArrayHelper.elementoNoVetor(this.submaquinaAtual.getEstadosFinais(), this.submaquinaAtual.getEstadoAtual()))
			return true;
		
		return false;
	}
	
	/**
	 * Verifica se a pilha do APE está vazia.
	 * 
	 * @return <code>true<code>, caso a pilha esteja vazia. <code>false</code>, caso contrário.
	 */
	public boolean pilhaVazia() {
		return this.pilha.vazia();
	}
	
	/**
	 * @return o último erro sintático encontrado.
	 */
	public ErroSintatico getUltimoErroSintatico() {
		ErroSintatico erro = this.ultimoErro;
		this.ultimoErro = null;
		return erro;
	}
	
	/**
	 * Inicializa as submáquinas do APE.
	 */
	private void inicializaSubmaquinas() {
		this.mapa = new Mapa<String, Integer>();
		this.submaquinas = new Submaquina[10];
		int indice = 0;
		
		GeradorSubmaquinas geradorSubmaquinas = new GeradorSubmaquinas(this.mapa);
		Submaquina submaquina;
		
		geradorSubmaquinas.parseXML("src/compilador/config/programa.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		indice++;
		
		geradorSubmaquinas.parseXML("src/compilador/config/parametros.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		indice++;
		
		geradorSubmaquinas.parseXML("src/compilador/config/declaracoes.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		indice++;
		
		geradorSubmaquinas.parseXML("src/compilador/config/comandos.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		indice++;
		
		geradorSubmaquinas.parseXML("src/compilador/config/expressaoBooleana.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		indice++;
		
		geradorSubmaquinas.parseXML("src/compilador/config/expressao.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		indice++;
		
		geradorSubmaquinas.parseXML("src/compilador/config/texto.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		indice++;
		
		// Ordena o array de submáquinas para poder ser indexador pelos valores do mapa de submáquinas.;
		Submaquina[] temporario = new Submaquina[10]; 
		
		ListaLigada<String> chaves = this.mapa.chaves();
		for(int i = 0; i < chaves.tamanho(); i++) {
			String chave = chaves.get(i);
			
			for(int j = 0; j < this.submaquinas.length; j++)
				if(this.submaquinas[j] != null && this.submaquinas[j].getNome().equals(chave))
					temporario[mapa.get(chave).intValue()] = this.submaquinas[j];
		}
		
		this.submaquinas = temporario;
	}
	
}