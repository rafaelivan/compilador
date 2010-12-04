package compilador.analisador.semantico;

import compilador.analisador.lexico.ParametrosAcoesSemanticas;
import compilador.estruturas.Int;
import compilador.estruturas.Pilha;
import compilador.gerador.codigo.GeradorCodigo;


public class AcoesSemanticas {
	
	/**
	 * Gerador de código.
	 */
	private static GeradorCodigo GERADOR_CODIGO = new GeradorCodigo();
	
	/**
	 * Contador para criação dos rótulos dos IF.
	 */
	private static int CONTADOR_IF = 0;
	
	/**
	 * Contador para criação dos rótulos dos WHILE.
	 */
	private static int CONTADOR_WHILE = 0;

	/**
	 * Contador para criação dos rótulos das funções.
	 */
	private static int CONTADOR_FUNCAO = 0;
	
	/**
	 * Contador para criação dos rótulos das variáveis.
	 */
	private static int CONTADOR_VARIAVEL = 0;
	
	/**
	 * Pilha para controlar a compatibilidade de tipos.
	 */
	private static Pilha<Int> PILHA_CONTROLE_TIPOS = new Pilha<Int>();
	
	/**
	 * Pilha para manipular os operandos das expressões.
	 */
	private static Pilha<Int> PILHA_OPERANDOS = new Pilha<Int>();
	
	/**
	 * Pilha para manipular os operadores das expressões.
	 */
	private static Pilha<Int> PILHA_OPERADORES = new Pilha<Int>();
	
	/* ************************ */
	/* AÇÕES SEMÂNTICAS GLOBAIS */
	/* ************************ */
	
	public static void acaoPadrao() {
		// System.out.println("ACAO SEMANTICA PADRAO");
	}
	
	/**
	 * Cria uma nova tabela de símbolos no contexto de escopos.
	 */
	public static void criarEscopo() {
		System.out.println("ACAO SEMANTICA: criarEscopo");
		
		TabelaSimbolos ts = new TabelaSimbolos();
		Escopos.putTabelaSimbolos(ts);
	}
	
	/**
	 * Deleta uma tabela de símbolos do contexto de escopos.
	 */
	public static void deletarEscopo() {
		System.out.println("ACAO SEMANTICA: deletarEscopo");
		
		Escopos.popTabelaSimbolos();
	}
	
	/**
	 * Empilha um tipo na pila de tipos.
	 * @param t
	 */
	public static void empilharTipo(int t) {
		Int tipo = new Int(t);
		
		if(PILHA_CONTROLE_TIPOS.vazia() || PILHA_CONTROLE_TIPOS.first() == tipo) {
			PILHA_CONTROLE_TIPOS.push(tipo);
		} else {
			System.out.println("TIPO INCOMPATIVEL.");
		}
	}
	
	/**
	 * Empilha um operando na pilha de operandos
	 * @param op
	 */
	public static void empilharOperando(int op) {
		
	}
	
	public static void empilharOperador(int op) {
		
	}
	
	/* **************************** */
	/* AÇÕES SEMÂNTICAS ESPECÍFICAS */
	/* **************************** */
	
	// Submáquina PROGRAMA
	
	public static void programa_0_1_3_1() {
		ParametrosAcoesSemanticas.TIPO = ParametrosAcoesSemanticas.TIPO_SIMBOLO_INT;
		ParametrosAcoesSemanticas.CATEGORIA = ParametrosAcoesSemanticas.CATEGORIA_SIMBOLO_FUNCAO;
		ParametrosAcoesSemanticas.DECLARADO = true;
	}
	
	public static void programa_1_2_0_4() {
		int id = ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID();
		compilador.estruturas.String rotulo = new compilador.estruturas.String(("FUNCAO_"+CONTADOR_FUNCAO).toCharArray());
		
		Escopos.setSimboloTipo(id, ParametrosAcoesSemanticas.TIPO);
		Escopos.setSimboloCategoria(id, ParametrosAcoesSemanticas.CATEGORIA);
		Escopos.setSimboloDeclarado(id, ParametrosAcoesSemanticas.DECLARADO);
		Escopos.setSimboloRotulo(id, rotulo);
		CONTADOR_FUNCAO++;
		
		GERADOR_CODIGO.addAreaCodigo(rotulo.append(("\tJP\t/000\n").toCharArray()));
		
		ParametrosAcoesSemanticas.limparParametros();
		ParametrosAcoesSemanticas.ID_FUNCAO = id;
	}
	
	public static void programa_4_3_40_6() {
		AcoesSemanticas.criarEscopo();
		ParametrosAcoesSemanticas.CATEGORIA = ParametrosAcoesSemanticas.CATEGORIA_SIMBOLO_PARAMETRO;
	}
	
	public static void programa_8_3_41_9() {
		ParametrosAcoesSemanticas.limparParametros();
	}
	
	public static void programa_9_3_123_10() {
		// Leu "{"
	}
	
	// Submáquina PARÂMETROS
	
	public static void parametros_0_1_3_1() {
		ParametrosAcoesSemanticas.TIPO = ParametrosAcoesSemanticas.TIPO_SIMBOLO_INT;
		ParametrosAcoesSemanticas.DECLARADO = true;
		ParametrosAcoesSemanticas.TAMANHO = 2;
	}
	
	public static void parametros_1_2_0_3() {
		compilador.estruturas.String rotulo = new compilador.estruturas.String(("VAR_"+CONTADOR_VARIAVEL).toCharArray());
		CONTADOR_VARIAVEL++;
		
		Escopos.setSimboloTipo(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), ParametrosAcoesSemanticas.TIPO);
		Escopos.setSimboloCategoria(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), ParametrosAcoesSemanticas.CATEGORIA);
		Escopos.setSimboloDeclarado(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), ParametrosAcoesSemanticas.DECLARADO);
		Escopos.setSimboloTamanho(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), ParametrosAcoesSemanticas.TAMANHO);
		Escopos.setSimboloRotulo(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), rotulo);		
		Escopos.addSimboloParametro(ParametrosAcoesSemanticas.ID_FUNCAO, Escopos.getTabelaSimbolosAtual().getTSLinha(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID()));
		
		GERADOR_CODIGO.addAreaDados(rotulo.append(("\tK\t=0\n").toCharArray()));
	}
	
	// Submáquina DECLARAÇÕES
	
	public static void declaracoes_0_1_3_2() {
		ParametrosAcoesSemanticas.TIPO = ParametrosAcoesSemanticas.TIPO_SIMBOLO_INT;
		ParametrosAcoesSemanticas.DECLARADO = true;
		ParametrosAcoesSemanticas.TAMANHO = 2;
	}
	
	public static void declaracoes_2_2_0_5() {
		compilador.estruturas.String rotulo = new compilador.estruturas.String(("VAR_"+CONTADOR_VARIAVEL).toCharArray());
		CONTADOR_VARIAVEL++;
		
		ParametrosAcoesSemanticas.ROTULO = rotulo;
		ParametrosAcoesSemanticas.CATEGORIA = ParametrosAcoesSemanticas.CATEGORIA_SIMBOLO_VARIAVEL;
	}
	
	public static void declaracoes_5_3_59_1() {
		Escopos.setSimboloTipo(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), ParametrosAcoesSemanticas.TIPO);
		Escopos.setSimboloCategoria(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), ParametrosAcoesSemanticas.CATEGORIA);
		Escopos.setSimboloDeclarado(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), ParametrosAcoesSemanticas.DECLARADO);
		Escopos.setSimboloTamanho(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), ParametrosAcoesSemanticas.TAMANHO);
		Escopos.setSimboloRotulo(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), ParametrosAcoesSemanticas.ROTULO);
		
		GERADOR_CODIGO.addAreaDados(ParametrosAcoesSemanticas.ROTULO.append(("\tK\t=0\n").toCharArray()));
		
		ParametrosAcoesSemanticas.limparParametros();
	}
	
	// Submáquina COMANDOS
	
	public static void comandos_0_2_0_1() {
		int chave = ParametrosAcoesSemanticas.TOKEN.getID();
		
		if(Escopos.isSimboloDeclarado(chave)) {
			AcoesSemanticas.empilharTipo(Escopos.getSimboloTipo(chave));
		}
	}
	
	public static void comandos_1_3_61_9() {
		// Leu "="
	}
	
	public static void comandos_0_1_0_2() {
		// Rotulo a ser impresso no código
		String rotulo = "IF"+CONTADOR_IF;
		CONTADOR_IF++;
	}
	
	// Submáquina EXPRESSÃO
	
	public static void expressao_0_4_0_3() {
		
	}
}