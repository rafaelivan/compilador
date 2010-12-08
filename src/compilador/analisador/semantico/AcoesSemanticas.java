package compilador.analisador.semantico;

import compilador.analisador.lexico.ParametrosAcoesSemanticas;
import compilador.analisador.lexico.Token;
import compilador.estruturas.Int;
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
	 * Contador para variáveis temporárias.
	 */
	private static int CONTADOR_TEMP = 0;
	
	/**
	 * Contador de parâmetros.
	 */
	private static int CONTADOR_PARAMETROS = 0;
	
	private static int CONTADOR_FINAL_WHILE = 0;
	
	/**
	 * Pilha para controlar a compatibilidade de tipos.
	 */
	private static Pilha<Int> PILHA_CONTROLE_TIPOS = new Pilha<Int>();
	
	/**
	 * Pilha para manipular os operandos das expressões.
	 */
	private static Pilha<Object> PILHA_OPERANDOS = new Pilha<Object>();
	
	/**
	 * Pilha para manipular os operadores das expressões.
	 */
	private static Pilha<Int> PILHA_OPERADORES = new Pilha<Int>();
	
	/**
	 * Pilha de rótulos.
	 */
	private static Pilha<String> PILHA_ROTULOS = new Pilha<String>();
	
	private static Pilha<String> PILHA_ROTULO_END_WHILE = new Pilha<String>();
	
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
//		System.out.println("ACAO SEMANTICA: criarEscopo");
		
		TabelaSimbolos ts = new TabelaSimbolos();
		Escopos.putTabelaSimbolos(ts);
	}
	
	/**
	 * Deleta uma tabela de símbolos do contexto de escopos.
	 */
	public static void deletarEscopo() {
//		System.out.println("ACAO SEMANTICA: deletarEscopo");
		
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
	public static void empilharOperando(Object op) {		
		PILHA_OPERANDOS.push(op);
	}
	
	/**
	 * Desempilha o operando no topo da pilha.
	 * @return
	 */
	public static Object desempilharOperando() {
		PILHA_CONTROLE_TIPOS.pop();
		return PILHA_OPERANDOS.pop();
	}
	
	public static void empilharOperador(int op) {
		if(op == (int)'+' || op == (int)'-') {
			if(!PILHA_OPERADORES.vazia() && (PILHA_OPERADORES.first().getValue() == (int)'*' || PILHA_OPERADORES.first().getValue() == (int)'/')) {
				AcoesSemanticas.calcularExpressao();
				AcoesSemanticas.empilharOperador(op);
			} else {
				PILHA_OPERADORES.push(new Int(op));
			}
		} else {
			PILHA_OPERADORES.push(new Int(op));
		}
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
			String dado = new String(("K_"+c+"\tK\t="+c+"\n").toCharArray());
			GERADOR_CODIGO.addAreaDados(dado);
		}
	}
	
	public static String gerarVariavelTemporaria() {
		String var = new String(("TMP_"+CONTADOR_TEMP).toCharArray());
		String dado = new String((var.toString()+"\tK\t=0\n").toCharArray());
		CONTADOR_TEMP++;
		GERADOR_CODIGO.addAreaDados(dado);
		
		return var;
	}
	
	public static void calcularExpressao() {
		Object operando1;
		Object operando2;
		int operador;
		
		operando1 = AcoesSemanticas.desempilharOperando();
		operando2 = AcoesSemanticas.desempilharOperando();
		operador = AcoesSemanticas.desempilharOperador();
		
		if(operando1 instanceof Int && operando2 instanceof Int) {
			switch (operador) {
				case (int)'+':
					AcoesSemanticas.empilharOperando(new Int(((Int)operando2).getValue() + ((Int) operando1).getValue()));
					break;
				case (int)'-':
					AcoesSemanticas.empilharOperando(new Int(((Int)operando2).getValue() - ((Int) operando1).getValue()));
					break;
				case (int)'*':
					AcoesSemanticas.empilharOperando(new Int(((Int)operando2).getValue() * ((Int) operando1).getValue()));
					break;
				case (int)'/':
					AcoesSemanticas.empilharOperando(new Int(((Int)operando2).getValue() / ((Int) operando1).getValue()));
					break;
			}
		} else {
			AcoesSemanticas.gerarCodigoTermo(operando1, operando2, operador);
		}
	}
	
	public static void gerarCodigoTermo(Object operando1, Object operando2, int operador) {
		String codigo = new String(("\tLD\t").toCharArray());
		
		if(operando2 instanceof Int) {
			codigo = codigo.append(("K_" + (((Int) operando2).getValue()) + "\n").toCharArray());
		} else if(operando2 instanceof String) {
			codigo = codigo.append((((String) operando2).toString() + "\n").toCharArray());
		} else {
			System.out.println(operando2);
		}
		
		switch(operador) {
			case (int)'+':
				codigo = codigo.append(("\t+\t").toCharArray());
				break;
			case (int)'-':
				codigo = codigo.append(("\t-\t").toCharArray());
				break;
			case (int)'*':
				codigo = codigo.append(("\t*\t").toCharArray());
				break;
			case (int)'/':	
				codigo = codigo.append(("\t/\t").toCharArray());
				break;
		}
		
		if(operando1 instanceof Int) {
			codigo = codigo.append(("K_" + (((Int) operando1).getValue()) + "\n").toCharArray());
		} else if(operando1 instanceof String) {
			codigo = codigo.append((((String) operando1).toString() + "\n").toCharArray());
		}
		
		String rotulo = AcoesSemanticas.gerarVariavelTemporaria();
		codigo = codigo.append(("\tMM\t"+rotulo.toString()+"\n").toCharArray());
		AcoesSemanticas.empilharOperando(rotulo);
		
		GERADOR_CODIGO.addAreaCodigo(codigo);
	}
	
	public static Object finalizarExpressao() {
		while(!PILHA_OPERADORES.vazia()) {
			AcoesSemanticas.calcularExpressao();
		}
		
		Object resultado = AcoesSemanticas.desempilharOperando();
		if(resultado instanceof Int) {
			AcoesSemanticas.gerarConstante(((Int) resultado).getValue());
		}
		
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
		GERADOR_CODIGO.addAreaCodigo(new String("\nMAIN".toCharArray()));
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
		GERADOR_CODIGO.addAreaDados(new String(("EXT_"+ParametrosAcoesSemanticas.ID_FUNCAO+"\tK\t=0\n").toCharArray()));
		GERADOR_CODIGO.addAreaCodigo(new String(("\tMM\tEXT_"+ParametrosAcoesSemanticas.ID_FUNCAO+"\n").toCharArray()));
		GERADOR_CODIGO.addAreaCodigo(new String(("\tRS\t").toCharArray()));
		GERADOR_CODIGO.addAreaCodigo(Escopos.getSimboloRotulo(ParametrosAcoesSemanticas.ID_FUNCAO).append("\n".toCharArray()));
		
		ParametrosAcoesSemanticas.limparParametros();
		AcoesSemanticas.deletarEscopo();
	}
	
	public static void programa_5_3_125_7() {
		GERADOR_CODIGO.addAreaCodigo(new String("\t#\tMAIN\n".toCharArray()));
		GERADOR_CODIGO.gerarCodigo().imprimir();
		
		ParametrosAcoesSemanticas.limparParametros();
		AcoesSemanticas.deletarEscopo();
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
		Object resultado = AcoesSemanticas.finalizarExpressao();
		
		if(resultado instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\tK_"+((Int) resultado).getValue()+"\n").toCharArray()));
		} else {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+((String) resultado).toString()+"\n").toCharArray()));
		}
		
		GERADOR_CODIGO.addAreaCodigo(new String(("\tMM\t").toCharArray()));
		GERADOR_CODIGO.addAreaCodigo(Escopos.getSimboloRotulo(ParametrosAcoesSemanticas.TOKEN_LADO_ESQUERDO_ATRIB.getID()));
		GERADOR_CODIGO.addAreaCodigo(new String(("\n").toCharArray()));
		
		ParametrosAcoesSemanticas.limparParametros();
	}
	
	public static void comandos_12_3_41_11() {
		AcoesSemanticas.empilharOperando(new String(("EXT_"+ParametrosAcoesSemanticas.ID_FUNCAO).toCharArray()));
		GERADOR_CODIGO.addAreaCodigo(new String(("\tSC\tFUN_"+ParametrosAcoesSemanticas.ID_FUNCAO+"\n").toCharArray()));
	}
	
	public static void comandos_9_2_0_17() {
		if(Escopos.isSimboloDeclarado(ParametrosAcoesSemanticas.TOKEN.getID())) {
			if(Escopos.getSimboloTipo(ParametrosAcoesSemanticas.TOKEN.getID()) == Escopos.getSimboloTipo(ParametrosAcoesSemanticas.TOKEN_LADO_ESQUERDO_ATRIB.getID())) {
				if(Escopos.getSimboloCategoria(ParametrosAcoesSemanticas.TOKEN.getID()) == ParametrosAcoesSemanticas.CATEGORIA_SIMBOLO_FUNCAO)
					ParametrosAcoesSemanticas.TOKEN_FUNCAO = ParametrosAcoesSemanticas.TOKEN;
			}
		}
	}
	
	public static void comandos_17_3_40_10() {
		CONTADOR_PARAMETROS = 0;
	}
	
	public static void comandos_10_2_0_12() {
		TSLinha[] parametros = Escopos.getSimboloParametros(ParametrosAcoesSemanticas.TOKEN_FUNCAO.getID());
		
		if(ParametrosAcoesSemanticas.TOKEN.getClasse() == Token.CLASSE_IDENTIFICADOR) {
			if(parametros[CONTADOR_PARAMETROS].getTipo() == Escopos.getSimboloTipo(ParametrosAcoesSemanticas.TOKEN.getID())) {
				String rotuloParametro = parametros[CONTADOR_PARAMETROS].getRotulo();
				String rotuloArgumento = Escopos.getSimboloRotulo(ParametrosAcoesSemanticas.TOKEN.getID());
				
				GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+rotuloArgumento+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tMM\t"+rotuloParametro+"\n").toCharArray()));
			}
		} else if(ParametrosAcoesSemanticas.TOKEN.getClasse() == Token.CLASSE_NUMERO_INTEIRO) {
			if(parametros[CONTADOR_PARAMETROS].getTipo() == ParametrosAcoesSemanticas.TIPO_SIMBOLO_INT) {
				String rotuloParametro = parametros[CONTADOR_PARAMETROS].getRotulo();
				String rotuloArgumento = new String(("K_"+ParametrosAcoesSemanticas.TOKEN.getID()).toCharArray());
				
				GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+rotuloArgumento+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tMM\t"+rotuloParametro+"\n").toCharArray()));
			}
		} else {
			// TODO
		}
		
		CONTADOR_PARAMETROS++;
	}
	
	public static void comandos_13_1_0_2() {
		AcoesSemanticas.criarEscopo();
	}
	
	public static void comandos_2_3_40_30() {
		
	}
	
	public static void comandos_19_3_62_24() {
		Object operando = AcoesSemanticas.desempilharOperando();
		
		ParametrosAcoesSemanticas.COMPARADOR = ParametrosAcoesSemanticas.COMPARADOR_MAIOR;
		
		if(operando instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\tK_"+((Int) operando).getValue()+"\n").toCharArray()));
		} else if(operando instanceof String) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+operando.toString()+"\n").toCharArray()));
		}
		
		GERADOR_CODIGO.addAreaCodigo(new String(("\t-\t").toCharArray()));
	}
	
	public static void comandos_19_3_60_24() {
		ParametrosAcoesSemanticas.COMPARADOR = ParametrosAcoesSemanticas.COMPARADOR_MENOR;
		
		Object operando = AcoesSemanticas.desempilharOperando();		
		if(operando instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\tK_"+((Int) operando).getValue()+"\n").toCharArray()));
		} else if(operando instanceof String) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+operando.toString()+"\n").toCharArray()));
		}
		
		GERADOR_CODIGO.addAreaCodigo(new String(("\t-\t").toCharArray()));
	}
	
	public static void comandos_19_1_16_24() {
		ParametrosAcoesSemanticas.COMPARADOR = ParametrosAcoesSemanticas.COMPARADOR_IGUAL;
		
		Object operando = AcoesSemanticas.desempilharOperando();		
		if(operando instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\tK_"+((Int) operando).getValue()+"\n").toCharArray()));
		} else if(operando instanceof String) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+operando.toString()+"\n").toCharArray()));
		}
		
		GERADOR_CODIGO.addAreaCodigo(new String(("\t-\t").toCharArray()));
	}
	
	public static void comandos_19_1_17_24() {
		ParametrosAcoesSemanticas.COMPARADOR = ParametrosAcoesSemanticas.COMPARADOR_MENOR_IGUAL;
		
		Object operando = AcoesSemanticas.desempilharOperando();		
		if(operando instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\tK_"+((Int) operando).getValue()+"\n").toCharArray()));
		} else if(operando instanceof String) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+operando.toString()+"\n").toCharArray()));
		}
		
		GERADOR_CODIGO.addAreaCodigo(new String(("\t-\t").toCharArray()));
	}
	
	public static void comandos_19_1_18_24() {
		ParametrosAcoesSemanticas.COMPARADOR = ParametrosAcoesSemanticas.COMPARADOR_MAIOR_IGUAL;
		
		Object operando = AcoesSemanticas.desempilharOperando();		
		if(operando instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\tK_"+((Int) operando).getValue()+"\n").toCharArray()));
		} else if(operando instanceof String) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+operando.toString()+"\n").toCharArray()));
		}
		
		GERADOR_CODIGO.addAreaCodigo(new String(("\t-\t").toCharArray()));
	}

	public static void comandos_19_1_19_24() {
		ParametrosAcoesSemanticas.COMPARADOR = ParametrosAcoesSemanticas.COMPARADOR_DIFERENTE;
		
		Object operando = AcoesSemanticas.desempilharOperando();		
		if(operando instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\tK_"+((Int) operando).getValue()+"\n").toCharArray()));
		} else if(operando instanceof String) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+operando.toString()+"\n").toCharArray()));
		}
		
		GERADOR_CODIGO.addAreaCodigo(new String(("\t-\t").toCharArray()));
	}
	
	public static void comandos_31_3_62_33() {
		Object operando = AcoesSemanticas.desempilharOperando();
		
		ParametrosAcoesSemanticas.COMPARADOR = ParametrosAcoesSemanticas.COMPARADOR_MAIOR;
		
		if(operando instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\tK_"+((Int) operando).getValue()+"\n").toCharArray()));
		} else if(operando instanceof String) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+operando.toString()+"\n").toCharArray()));
		}
		
		GERADOR_CODIGO.addAreaCodigo(new String(("\t-\t").toCharArray()));
	}
	
	public static void comandos_31_3_60_33() {
		ParametrosAcoesSemanticas.COMPARADOR = ParametrosAcoesSemanticas.COMPARADOR_MENOR;
		
		Object operando = AcoesSemanticas.desempilharOperando();		
		if(operando instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\tK_"+((Int) operando).getValue()+"\n").toCharArray()));
		} else if(operando instanceof String) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+operando.toString()+"\n").toCharArray()));
		}
		
		GERADOR_CODIGO.addAreaCodigo(new String(("\t-\t").toCharArray()));
	}
	
	public static void comandos_31_1_16_33() {
		ParametrosAcoesSemanticas.COMPARADOR = ParametrosAcoesSemanticas.COMPARADOR_IGUAL;
		
		Object operando = AcoesSemanticas.desempilharOperando();		
		if(operando instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\tK_"+((Int) operando).getValue()+"\n").toCharArray()));
		} else if(operando instanceof String) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+operando.toString()+"\n").toCharArray()));
		}
		
		GERADOR_CODIGO.addAreaCodigo(new String(("\t-\t").toCharArray()));
	}
	
	public static void comandos_31_1_17_33() {
		ParametrosAcoesSemanticas.COMPARADOR = ParametrosAcoesSemanticas.COMPARADOR_MENOR_IGUAL;
		
		Object operando = AcoesSemanticas.desempilharOperando();		
		if(operando instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\tK_"+((Int) operando).getValue()+"\n").toCharArray()));
		} else if(operando instanceof String) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+operando.toString()+"\n").toCharArray()));
		}
		
		GERADOR_CODIGO.addAreaCodigo(new String(("\t-\t").toCharArray()));
	}
	
	public static void comandos_31_1_18_33() {
		ParametrosAcoesSemanticas.COMPARADOR = ParametrosAcoesSemanticas.COMPARADOR_MAIOR_IGUAL;
		
		Object operando = AcoesSemanticas.desempilharOperando();		
		if(operando instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\tK_"+((Int) operando).getValue()+"\n").toCharArray()));
		} else if(operando instanceof String) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+operando.toString()+"\n").toCharArray()));
		}
		
		GERADOR_CODIGO.addAreaCodigo(new String(("\t-\t").toCharArray()));
	}

	public static void comandos_31_1_19_33() {
		ParametrosAcoesSemanticas.COMPARADOR = ParametrosAcoesSemanticas.COMPARADOR_DIFERENTE;
		
		Object operando = AcoesSemanticas.desempilharOperando();		
		if(operando instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\tK_"+((Int) operando).getValue()+"\n").toCharArray()));
		} else if(operando instanceof String) {
			GERADOR_CODIGO.addAreaCodigo(new String(("\tLD\t"+operando.toString()+"\n").toCharArray()));
		}
		
		GERADOR_CODIGO.addAreaCodigo(new String(("\t-\t").toCharArray()));
	}
	
	public static void comandos_32_3_41_34() {
		Object operando = AcoesSemanticas.desempilharOperando();
		
		if(operando instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("K_"+((Int) operando).getValue()+"\n").toCharArray()));
		} else if(operando instanceof String) {
			GERADOR_CODIGO.addAreaCodigo(new String((operando.toString()+"\n").toCharArray()));
		}
		
		String rotulo = new String(("IF_"+CONTADOR_IF).toCharArray());
		CONTADOR_IF++;
		PILHA_ROTULOS.push(rotulo);
		
		switch (ParametrosAcoesSemanticas.COMPARADOR) {
			case ParametrosAcoesSemanticas.COMPARADOR_MAIOR:
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJZ\t"+rotulo.toString()+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJN\t"+rotulo.toString()+"\n").toCharArray()));
				break;
			case ParametrosAcoesSemanticas.COMPARADOR_MENOR:
				AcoesSemanticas.gerarConstante(0);
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJZ\t"+rotulo.toString()+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaDados(new String(("TEMP_"+CONTADOR_TEMP+"\tK\t=0\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tMM\tTEMP_"+CONTADOR_TEMP+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String("\tLD\tK_0\n".toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\t-\tTEMP_"+CONTADOR_TEMP+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJN\t"+rotulo.toString()+"\n").toCharArray()));
				CONTADOR_TEMP++;
				break;
			case ParametrosAcoesSemanticas.COMPARADOR_MAIOR_IGUAL:
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJN\t"+rotulo.toString()+"\n").toCharArray()));
				break;
			case ParametrosAcoesSemanticas.COMPARADOR_MENOR_IGUAL:
				AcoesSemanticas.gerarConstante(0);
				GERADOR_CODIGO.addAreaDados(new String(("TEMP_"+CONTADOR_TEMP+"\tK\t=0\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tMM\tTEMP_"+CONTADOR_TEMP+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String("\tLD\tK_0\n".toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\t-\tTEMP_"+CONTADOR_TEMP+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJN\t"+rotulo.toString()+"\n").toCharArray()));
				CONTADOR_TEMP++;
				break;
			case ParametrosAcoesSemanticas.COMPARADOR_IGUAL:
				AcoesSemanticas.gerarConstante(0);
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJZ\t"+rotulo.toString()+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaDados(new String(("TEMP_"+CONTADOR_TEMP+"\tK\t=0\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tMM\tTEMP_"+CONTADOR_TEMP+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String("\tLD\tK_0\n".toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\t-\tTEMP_"+CONTADOR_TEMP+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJN\t"+rotulo.toString()+"\n").toCharArray()));
				CONTADOR_TEMP++;
				break;
			case ParametrosAcoesSemanticas.COMPARADOR_DIFERENTE:
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJZ\t"+rotulo.toString()+"\n").toCharArray()));
				break;
		}
	}
	
	public static void comandos_20_3_41_21() {
		Object operando = AcoesSemanticas.desempilharOperando();
		
		if(operando instanceof Int) {
			GERADOR_CODIGO.addAreaCodigo(new String(("K_"+((Int) operando).getValue()+"\n").toCharArray()));
		} else if(operando instanceof String) {
			GERADOR_CODIGO.addAreaCodigo(new String((operando.toString()+"\n").toCharArray()));
		}
		
		String rotulo = new String(("END_"+CONTADOR_FINAL_WHILE).toCharArray());
		CONTADOR_FINAL_WHILE++;
		PILHA_ROTULO_END_WHILE.push(rotulo);
		
		switch (ParametrosAcoesSemanticas.COMPARADOR) {
			case ParametrosAcoesSemanticas.COMPARADOR_MAIOR:
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJZ\t"+rotulo.toString()+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJN\t"+rotulo.toString()+"\n").toCharArray()));
				break;
			case ParametrosAcoesSemanticas.COMPARADOR_MENOR:
				AcoesSemanticas.gerarConstante(0);
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJZ\t"+rotulo.toString()+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaDados(new String(("TEMP_"+CONTADOR_TEMP+"\tK\t=0\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tMM\tTEMP_"+CONTADOR_TEMP+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String("\tLD\tK_0\n".toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\t-\tTEMP_"+CONTADOR_TEMP+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJN\t"+rotulo.toString()+"\n").toCharArray()));
				CONTADOR_TEMP++;
				break;
			case ParametrosAcoesSemanticas.COMPARADOR_MAIOR_IGUAL:
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJN\t"+rotulo.toString()+"\n").toCharArray()));
				break;
			case ParametrosAcoesSemanticas.COMPARADOR_MENOR_IGUAL:
				AcoesSemanticas.gerarConstante(0);
				GERADOR_CODIGO.addAreaDados(new String(("TEMP_"+CONTADOR_TEMP+"\tK\t=0\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tMM\tTEMP_"+CONTADOR_TEMP+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String("\tLD\tK_0\n".toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\t-\tTEMP_"+CONTADOR_TEMP+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJN\t"+rotulo.toString()+"\n").toCharArray()));
				CONTADOR_TEMP++;
				break;
			case ParametrosAcoesSemanticas.COMPARADOR_IGUAL:
				AcoesSemanticas.gerarConstante(0);
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJZ\t"+rotulo.toString()+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaDados(new String(("TEMP_"+CONTADOR_TEMP+"\tK\t=0\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tMM\tTEMP_"+CONTADOR_TEMP+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String("\tLD\tK_0\n".toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\t-\tTEMP_"+CONTADOR_TEMP+"\n").toCharArray()));
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJN\t"+rotulo.toString()+"\n").toCharArray()));
				CONTADOR_TEMP++;
				break;
			case ParametrosAcoesSemanticas.COMPARADOR_DIFERENTE:
				GERADOR_CODIGO.addAreaCodigo(new String(("\tJZ\t"+rotulo.toString()+"\n").toCharArray()));
				break;
		}
	}
	
	public static void comandos_34_3_123_35() {
		
	}
	
	public static void comandos_35_3_125_36() {
		String rotulo = PILHA_ROTULOS.pop();
		
		GERADOR_CODIGO.addAreaCodigo(new String((rotulo.toString()).toCharArray()));
		
		ParametrosAcoesSemanticas.limparParametros();
		AcoesSemanticas.deletarEscopo();
	}
	
	public static void comandos_13_1_2_3() {
		AcoesSemanticas.criarEscopo();
		
		String rotulo = new String(("WHL_"+CONTADOR_WHILE).toCharArray());
		PILHA_ROTULOS.push(rotulo);
		CONTADOR_WHILE++;
		
		GERADOR_CODIGO.addAreaCodigo(new String((rotulo.toString()).toCharArray()));
	}
	
	public static void comandos_22_3_125_13() {
		String rotulo = PILHA_ROTULOS.pop();
		GERADOR_CODIGO.addAreaCodigo(new String(("\tJP\t"+rotulo+"\n").toCharArray()));
		
		rotulo = PILHA_ROTULO_END_WHILE.pop();
		GERADOR_CODIGO.addAreaCodigo(new String((rotulo.toString()).toCharArray()));
	}
	
	// Submáquina EXPRESSÃO
	
	public static void expressao_0_4_0_3() {
		if(AcoesSemanticas.empilharTipo(ParametrosAcoesSemanticas.TIPO_SIMBOLO_INT)) {
			Int numero = new Int(ParametrosAcoesSemanticas.TOKEN.getID());
			
			AcoesSemanticas.gerarConstante(numero.getValue());
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
	
	public static void expressao_0_2_0_2() {
		AcoesSemanticas.empilharOperando(Escopos.getSimboloRotulo(ParametrosAcoesSemanticas.TOKEN.getID()));
	}
	
	public static void expressao_0_3_43_1() {
		if(AcoesSemanticas.empilharTipo(ParametrosAcoesSemanticas.TIPO_SIMBOLO_INT)) {
			AcoesSemanticas.empilharOperando(new Int(0));
			AcoesSemanticas.empilharOperador(ParametrosAcoesSemanticas.TOKEN.getID());
		}
	}
	
	public static void expressao_0_3_45_1() {
		if(AcoesSemanticas.empilharTipo(ParametrosAcoesSemanticas.TIPO_SIMBOLO_INT)) {
			AcoesSemanticas.empilharOperando(new Int(0));
			AcoesSemanticas.empilharOperador(ParametrosAcoesSemanticas.TOKEN.getID());
		}
	}
	
	public static void expressao_1_2_0_2() {
		if(AcoesSemanticas.empilharTipo(ParametrosAcoesSemanticas.TIPO_SIMBOLO_INT))
			AcoesSemanticas.empilharOperando(Escopos.getSimboloRotulo(ParametrosAcoesSemanticas.TOKEN.getID()));
	}
	
	public static void expressao_2_3_45_0() {
		AcoesSemanticas.empilharOperador(ParametrosAcoesSemanticas.TOKEN.getID());
	}
	
	public static void expressao_2_3_42_0() {
		AcoesSemanticas.empilharOperador(ParametrosAcoesSemanticas.TOKEN.getID());
	}
	
	public static void expressao_2_3_43_0() {
		AcoesSemanticas.empilharOperador(ParametrosAcoesSemanticas.TOKEN.getID());
	}
	
	public static void expressao_2_3_47_0() {
		AcoesSemanticas.empilharOperador(ParametrosAcoesSemanticas.TOKEN.getID());
	}
}