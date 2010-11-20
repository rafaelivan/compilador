package compilador.analisador.sintatico;

import compilador.estruturas.Mapa;
import compilador.estruturas.Pilha;
import compilador.estruturas.String;

public class AutomatoPilhaEstruturado {
	
	/**
	 * Array com todas as submáquinas do APE.
	 */
	private Submaquina[] submaquinas;
	
	/**
	 * 
	 */
	private Mapa<String, Integer> mapa;
	
	/**
	 * A pilha que gerenciará as transições entre submáquinas.
	 */
	private Pilha<Par> pilha;
	
	
	public AutomatoPilhaEstruturado() {
		this.inicializaSubmaquinas();
		this.pilha = new Pilha<Par>();
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
		
		geradorSubmaquinas.parseXML("src/compilador/config/parametros.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		
		geradorSubmaquinas.parseXML("src/compilador/config/declaracoes.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		
		geradorSubmaquinas.parseXML("src/compilador/config/comandos.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		
		geradorSubmaquinas.parseXML("src/compilador/config/expressaoBooleana.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		
		geradorSubmaquinas.parseXML("src/compilador/config/expressao.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
		
		geradorSubmaquinas.parseXML("src/compilador/config/texto.xml");
		submaquina = geradorSubmaquinas.gerarSubmaquina();
		this.submaquinas[indice] = submaquina;
	}
}