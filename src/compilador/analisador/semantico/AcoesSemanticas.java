package compilador.analisador.semantico;

import compilador.analisador.lexico.ParametrosAcoesSemanticas;
import compilador.estruturas.Int;
import compilador.estruturas.ListaLigada;
import compilador.estruturas.Pilha;
import compilador.estruturas.String;
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
	 * Contador para a criação de constantes.
	 */
	private static int CONTADOR_CONSTANTE = 0;
	
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
	 * @return
	 */
	public static boolean empilharTipo(int t) {
		Int tipo = new Int(t);
		
		if(PILHA_CONTROLE_TIPOS.vazia() || PILHA_CONTROLE_TIPOS.first().equals(tipo)) {
			PILHA_CONTROLE_TIPOS.push(tipo);
			return true;
		} else {
			System.out.println("TIPO INCOMPATIVEL.");
			return false;
		}
	}
	
	/**
	 * Desempilha o tipo no topo da pilha.
	 * @return
	 */
	public static int desempilharTipo() {
		return PILHA_CONTROLE_TIPOS.pop().getValue();
	}
	
	/**
	 * Empilha um operando na pilha de operandos
	 * @param op
	 */
	public static void empilharOperando(int op) {
		Int operando = new Int(op);
		PILHA_OPERANDOS.push(operando);
	}
	
	/**
	 * Desempilha o operando no topo da pilha.
	 * @return
	 */
	public static int desempilharOperando() {
		PILHA_CONTROLE_TIPOS.pop();
		return PILHA_OPERANDOS.pop().getValue();
	}
	
	public static void empilharOperador(int op) {
		if(op == (int)'+' || op == (int)'-')
			if(!PILHA_OPERADORES.vazia() && (PILHA_OPERADORES.first().getValue() == (int)'*' || PILHA_OPERADORES.first().getValue() == (int)'/'))
				AcoesSemanticas.calcularExpressao();
		
		PILHA_OPERADORES.push(new Int(op));
	}
	
	/**
	 * Desempilha o operador no topo da pilha.
	 * @return
	 */
	public static int desempilharOperador() {
		return PILHA_OPERADORES.pop().getValue();
	}
	
	public static void gerarConstante(int c) {
		if(!TabelaNumeros.getInstance().numeroNaLista(c)) {
			TabelaNumeros.getInstance().inserirNumero(c);
			compilador.estruturas.String dado = new compilador.estruturas.String(("K_"+c+"\tK\t="+c+"\n").toCharArray());
			GERADOR_CODIGO.addAreaDados(dado);
		}
	}
	
	public static void calcularExpressao() {
		int operando1;
		int operando2;
		int operador;
		
		while(!PILHA_OPERADORES.vazia()) {
			operando1 = AcoesSemanticas.desempilharOperando();
			operando2 = AcoesSemanticas.desempilharOperando();
			operador = AcoesSemanticas.desempilharOperador();
			
			switch (operador) {
				case (int)'+':
					AcoesSemanticas.empilharOperando(operando2+operando1);
					break;
				case (int)'-':
					AcoesSemanticas.empilharOperando(operando2-operando1);
					break;
				case (int)'*':
					AcoesSemanticas.empilharOperando(operando2*operando1);
					break;
				case (int)'/':
					AcoesSemanticas.empilharOperando(operando2/operando1);
					break;
			}
		}
	}
	
	public static int finalizarExpressao() {
		if(!PILHA_OPERADORES.vazia())
			AcoesSemanticas.calcularExpressao();
		
		int resultado = AcoesSemanticas.desempilharOperando();
		PILHA_CONTROLE_TIPOS.pop();
		AcoesSemanticas.gerarConstante(resultado);
		
		return resultado;
		
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
	
	public static void programa_0_1_5_1() {
		
	}
	
	public static void programa_0_1_4_1() {
		
	}
	
	public static void programa_0_1_6_1() {
		
	}
	
	public static void programa_0_1_9_2() {
		
	}
	
	public static void programa_0_1_15_3() {
		GERADOR_CODIGO.addAreaCodigo(new String("\nMAIN\n".toCharArray()));
		AcoesSemanticas.criarEscopo();
	}
	
	public static void programa_1_2_0_4() {
		int id = ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID();
		compilador.estruturas.String rotulo = new compilador.estruturas.String(("FUN_"+CONTADOR_FUNCAO).toCharArray());
		
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
	
	
	public static void programa_10_3_125_0() {
		GERADOR_CODIGO.addAreaCodigo(new String(("\tRS\t").toCharArray()));
		GERADOR_CODIGO.addAreaCodigo(Escopos.getSimboloRotulo(ParametrosAcoesSemanticas.ID_FUNCAO).append("\n".toCharArray()));
	}
	
	public static void programa_5_3_125_7() {
		GERADOR_CODIGO.addAreaCodigo(new String("\t#\tMAIN\n".toCharArray()));
		GERADOR_CODIGO.gerarCodigo().imprimir();
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
	
	public static void declaracoes_1_1_3_2() {
		ParametrosAcoesSemanticas.TIPO = ParametrosAcoesSemanticas.TIPO_SIMBOLO_INT;
		ParametrosAcoesSemanticas.DECLARADO = true;
		ParametrosAcoesSemanticas.TAMANHO = 2;
	}
	
	public static void declaracoes_5_3_44_2() {
		Escopos.setSimboloTipo(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), ParametrosAcoesSemanticas.TIPO);
		Escopos.setSimboloCategoria(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), ParametrosAcoesSemanticas.CATEGORIA);
		Escopos.setSimboloDeclarado(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), ParametrosAcoesSemanticas.DECLARADO);
		Escopos.setSimboloTamanho(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), ParametrosAcoesSemanticas.TAMANHO);
		Escopos.setSimboloRotulo(ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR.getID(), ParametrosAcoesSemanticas.ROTULO);
		
		GERADOR_CODIGO.addAreaDados(ParametrosAcoesSemanticas.ROTULO.append(("\tK\t=0\n").toCharArray()));
		
		ParametrosAcoesSemanticas.limparParametros();
		
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
			ParametrosAcoesSemanticas.TOKEN_LADO_ESQUERDO_ATRIB = ParametrosAcoesSemanticas.TOKEN;
		} else {
			System.out.println("SIMBOLO NAO DECLARADO");
		}
	}
	
	public static void comandos_13_2_0_1() {
		int chave = ParametrosAcoesSemanticas.TOKEN.getID();
				
		if(Escopos.isSimboloDeclarado(chave)) {
			AcoesSemanticas.empilharTipo(Escopos.getSimboloTipo(chave));
			ParametrosAcoesSemanticas.TOKEN_LADO_ESQUERDO_ATRIB = ParametrosAcoesSemanticas.TOKEN;
		} else {
			System.out.println("SIMBOLO NAO DECLARADO");
		}
	}
	
	public static void comandos_1_3_61_9() {
		// Leu "="
	}
	
	public static void comandos_0_1_0_2() {
		// Rótulo a ser impresso no código
//		String rotulo = "IF"+CONTADOR_IF;
//		CONTADOR_IF++;
	}
	
	public static void comandos_11_3_59_13() {
		int resultado = AcoesSemanticas.finalizarExpressao();
		
		GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\tK_"+resultado+"\n").toCharArray()));
		GERADOR_CODIGO.addAreaCodigo(new String(("\tMM\t").toCharArray()));
		GERADOR_CODIGO.addAreaCodigo(Escopos.getSimboloRotulo(ParametrosAcoesSemanticas.TOKEN_LADO_ESQUERDO_ATRIB.getID()));
		GERADOR_CODIGO.addAreaCodigo(new String(("\n").toCharArray()));
		
		ParametrosAcoesSemanticas.limparParametros();
	}
	
	// Submáquina EXPRESSÃO
	
	public static void expressao_0_4_0_3() {
		if(AcoesSemanticas.empilharTipo(ParametrosAcoesSemanticas.TIPO_SIMBOLO_INT)) {
			int numero = ParametrosAcoesSemanticas.TOKEN.getID();
			
			AcoesSemanticas.gerarConstante(numero);
			AcoesSemanticas.empilharOperando(numero);
		}
	}
	
	public static void expressao_3_3_42_0() {
		AcoesSemanticas.empilharOperador(ParametrosAcoesSemanticas.TOKEN.getID());
	}
	
	public static void expressao_3_3_43_0() {
		AcoesSemanticas.empilharOperador(ParametrosAcoesSemanticas.TOKEN.getID());
	}
	
	public static void expressao_3_3_45_0() {
		AcoesSemanticas.empilharOperador(ParametrosAcoesSemanticas.TOKEN.getID());
	}
	
	public static void expressao_3_3_47_0() {
		AcoesSemanticas.empilharOperador(ParametrosAcoesSemanticas.TOKEN.getID());
	}
}