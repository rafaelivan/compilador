package compilador.analisador.semantico;

import java.lang.reflect.Method;

import compilador.analisador.lexico.ParametrosAcoesSemanticas;
import compilador.analisador.lexico.Token;
import compilador.estruturas.Mapa;

public class AnalisadorSemantico {
	
	/**
	 * Mapa que relaciona o nome de cada submáquina com a sua tabela de chamadas de ações semânticas.
	 */
	private Mapa<String, String[][][]> mapaAcoesSemanticas = new Mapa<String, String[][][]>();
	
	public AnalisadorSemantico() {
		String[][][] tabelaAcoesSemanticas;
		// Submáquina programa
		tabelaAcoesSemanticas = new String[16][7][256];
		tabelaAcoesSemanticas[5][3][125] = "programa_5_3_125_7";
		tabelaAcoesSemanticas[0][1][5] = "programa_0_1_5_1";
		tabelaAcoesSemanticas[0][1][3] = "programa_0_1_3_1";
		tabelaAcoesSemanticas[0][1][4] = "programa_0_1_4_1";
		tabelaAcoesSemanticas[0][1][6] = "programa_0_1_6_1";
		tabelaAcoesSemanticas[0][1][9] = "programa_0_1_9_2";
		tabelaAcoesSemanticas[0][1][15] = "programa_0_1_15_3";
		tabelaAcoesSemanticas[11][1][3] = "programa_11_1_3_1";
		tabelaAcoesSemanticas[11][1][4] = "programa_11_1_4_1";
		tabelaAcoesSemanticas[11][1][6] = "programa_11_1_6_1";
		tabelaAcoesSemanticas[11][1][10] = "programa_11_1_10_12";
		tabelaAcoesSemanticas[1][2][0] = "programa_1_2_0_4";
		tabelaAcoesSemanticas[12][2][0] = "programa_12_2_0_13";
		tabelaAcoesSemanticas[13][3][123] = "programa_13_3_123_14";
		tabelaAcoesSemanticas[2][2][0] = "programa_2_2_0_11";
		tabelaAcoesSemanticas[8][3][41] = "programa_8_3_41_9";
		tabelaAcoesSemanticas[3][3][123] = "programa_3_3_123_5";
		tabelaAcoesSemanticas[9][3][123] = "programa_9_3_123_10";
		tabelaAcoesSemanticas[15][3][125] = "programa_15_3_125_1";
		tabelaAcoesSemanticas[4][3][40] = "programa_4_3_40_6";
		tabelaAcoesSemanticas[10][3][125] = "programa_10_3_125_0";
		this.mapaAcoesSemanticas.put("programa", tabelaAcoesSemanticas);

		// Submáquina parametros
		tabelaAcoesSemanticas = new String[15][7][256];
		tabelaAcoesSemanticas[5][4][0] = "parametros_5_4_0_10";
		tabelaAcoesSemanticas[0][1][3] = "parametros_0_1_3_1";
		tabelaAcoesSemanticas[0][1][4] = "parametros_0_1_4_1";
		tabelaAcoesSemanticas[0][1][6] = "parametros_0_1_6_1";
		tabelaAcoesSemanticas[0][1][9] = "parametros_0_1_9_2";
		tabelaAcoesSemanticas[11][3][91] = "parametros_11_3_91_12";
		tabelaAcoesSemanticas[11][3][44] = "parametros_11_3_44_0";
		tabelaAcoesSemanticas[6][2][0] = "parametros_6_2_0_7";
		tabelaAcoesSemanticas[1][2][0] = "parametros_1_2_0_3";
		tabelaAcoesSemanticas[12][4][0] = "parametros_12_4_0_13";
		tabelaAcoesSemanticas[7][3][123] = "parametros_7_3_123_8";
		tabelaAcoesSemanticas[13][3][93] = "parametros_13_3_93_14";
		tabelaAcoesSemanticas[2][2][0] = "parametros_2_2_0_4";
		tabelaAcoesSemanticas[14][3][44] = "parametros_14_3_44_0";
		tabelaAcoesSemanticas[3][3][91] = "parametros_3_3_91_5";
		tabelaAcoesSemanticas[3][3][44] = "parametros_3_3_44_0";
		tabelaAcoesSemanticas[9][3][125] = "parametros_9_3_125_1";
		tabelaAcoesSemanticas[4][1][3] = "parametros_4_1_3_1";
		tabelaAcoesSemanticas[4][1][4] = "parametros_4_1_4_1";
		tabelaAcoesSemanticas[4][1][6] = "parametros_4_1_6_1";
		tabelaAcoesSemanticas[4][1][10] = "parametros_4_1_10_6";
		tabelaAcoesSemanticas[10][3][93] = "parametros_10_3_93_11";
		this.mapaAcoesSemanticas.put("parametros", tabelaAcoesSemanticas);

		// Submáquina declaracoes
		tabelaAcoesSemanticas = new String[16][7][256];
		tabelaAcoesSemanticas[5][3][91] = "declaracoes_5_3_91_7";
		tabelaAcoesSemanticas[5][3][44] = "declaracoes_5_3_44_2";
		tabelaAcoesSemanticas[5][3][59] = "declaracoes_5_3_59_1";
		tabelaAcoesSemanticas[11][3][93] = "declaracoes_11_3_93_12";
		tabelaAcoesSemanticas[0][1][11] = "declaracoes_0_1_11_1";
		tabelaAcoesSemanticas[0][1][3] = "declaracoes_0_1_3_2";
		tabelaAcoesSemanticas[0][1][4] = "declaracoes_0_1_4_2";
		tabelaAcoesSemanticas[0][1][6] = "declaracoes_0_1_6_2";
		tabelaAcoesSemanticas[0][1][9] = "declaracoes_0_1_9_3";
		tabelaAcoesSemanticas[6][2][0] = "declaracoes_6_2_0_8";
		tabelaAcoesSemanticas[1][1][3] = "declaracoes_1_1_3_2";
		tabelaAcoesSemanticas[1][1][4] = "declaracoes_1_1_4_2";
		tabelaAcoesSemanticas[1][1][6] = "declaracoes_1_1_6_2";
		tabelaAcoesSemanticas[1][1][9] = "declaracoes_1_1_9_3";
		tabelaAcoesSemanticas[12][3][91] = "declaracoes_12_3_91_13";
		tabelaAcoesSemanticas[12][3][44] = "declaracoes_12_3_44_2";
		tabelaAcoesSemanticas[12][3][59] = "declaracoes_12_3_59_1";
		tabelaAcoesSemanticas[7][4][0] = "declaracoes_7_4_0_11";
		tabelaAcoesSemanticas[2][2][0] = "declaracoes_2_2_0_5";
		tabelaAcoesSemanticas[13][4][0] = "declaracoes_13_4_0_14";
		tabelaAcoesSemanticas[8][3][123] = "declaracoes_8_3_123_9";
		tabelaAcoesSemanticas[3][2][0] = "declaracoes_3_2_0_4";
		tabelaAcoesSemanticas[14][3][93] = "declaracoes_14_3_93_15";
		tabelaAcoesSemanticas[15][3][44] = "declaracoes_15_3_44_2";
		tabelaAcoesSemanticas[15][3][59] = "declaracoes_15_3_59_1";
		tabelaAcoesSemanticas[4][1][3] = "declaracoes_4_1_3_2";
		tabelaAcoesSemanticas[4][1][4] = "declaracoes_4_1_4_2";
		tabelaAcoesSemanticas[4][1][6] = "declaracoes_4_1_6_2";
		tabelaAcoesSemanticas[4][1][10] = "declaracoes_4_1_10_6";
		tabelaAcoesSemanticas[10][3][125] = "declaracoes_10_3_125_2";
		this.mapaAcoesSemanticas.put("declaracoes", tabelaAcoesSemanticas);

		// Submáquina comandos
		tabelaAcoesSemanticas = new String[37][7][256];
		tabelaAcoesSemanticas[27][3][93] = "comandos_27_3_93_28";
		tabelaAcoesSemanticas[5][3][40] = "comandos_5_3_40_14";
		tabelaAcoesSemanticas[16][3][41] = "comandos_16_3_41_11";
		tabelaAcoesSemanticas[11][3][59] = "comandos_11_3_59_13";
		tabelaAcoesSemanticas[0][2][0] = "comandos_0_2_0_1";
		tabelaAcoesSemanticas[0][1][0] = "comandos_0_1_0_2";
		tabelaAcoesSemanticas[0][1][2] = "comandos_0_1_2_3";
		tabelaAcoesSemanticas[0][1][13] = "comandos_0_1_13_4";
		tabelaAcoesSemanticas[0][1][12] = "comandos_0_1_12_5";
		tabelaAcoesSemanticas[0][1][14] = "comandos_0_1_14_6";
		tabelaAcoesSemanticas[22][3][125] = "comandos_22_3_125_13";
		tabelaAcoesSemanticas[17][3][40] = "comandos_17_3_40_10";
		tabelaAcoesSemanticas[28][3][61] = "comandos_28_3_61_9";
		tabelaAcoesSemanticas[1][3][46] = "comandos_1_3_46_7";
		tabelaAcoesSemanticas[1][3][91] = "comandos_1_3_91_8";
		tabelaAcoesSemanticas[1][3][61] = "comandos_1_3_61_9";
		tabelaAcoesSemanticas[1][3][40] = "comandos_1_3_40_10";
		tabelaAcoesSemanticas[23][3][93] = "comandos_23_3_93_25";
		tabelaAcoesSemanticas[34][3][123] = "comandos_34_3_123_35";
		tabelaAcoesSemanticas[12][3][44] = "comandos_12_3_44_15";
		tabelaAcoesSemanticas[12][3][41] = "comandos_12_3_41_11";
		tabelaAcoesSemanticas[29][3][46] = "comandos_29_3_46_7";
		tabelaAcoesSemanticas[29][3][61] = "comandos_29_3_61_9";
		tabelaAcoesSemanticas[2][3][40] = "comandos_2_3_40_30";
		tabelaAcoesSemanticas[35][3][125] = "comandos_35_3_125_36";
		tabelaAcoesSemanticas[13][2][0] = "comandos_13_2_0_1";
		tabelaAcoesSemanticas[13][1][0] = "comandos_13_1_0_2";
		tabelaAcoesSemanticas[13][1][2] = "comandos_13_1_2_3";
		tabelaAcoesSemanticas[13][1][13] = "comandos_13_1_13_4";
		tabelaAcoesSemanticas[13][1][12] = "comandos_13_1_12_5";
		tabelaAcoesSemanticas[13][1][14] = "comandos_13_1_14_6";
		tabelaAcoesSemanticas[8][4][0] = "comandos_8_4_0_23";
		tabelaAcoesSemanticas[19][1][16] = "comandos_19_1_16_24";
		tabelaAcoesSemanticas[19][3][62] = "comandos_19_3_62_24";
		tabelaAcoesSemanticas[19][3][60] = "comandos_19_3_60_24";
		tabelaAcoesSemanticas[19][1][17] = "comandos_19_1_17_24";
		tabelaAcoesSemanticas[19][1][18] = "comandos_19_1_18_24";
		tabelaAcoesSemanticas[19][1][19] = "comandos_19_1_19_24";
		tabelaAcoesSemanticas[25][3][91] = "comandos_25_3_91_26";
		tabelaAcoesSemanticas[25][3][61] = "comandos_25_3_61_9";
		tabelaAcoesSemanticas[3][3][40] = "comandos_3_3_40_18";
		tabelaAcoesSemanticas[36][2][0] = "comandos_36_2_0_1";
		tabelaAcoesSemanticas[36][1][0] = "comandos_36_1_0_2";
		tabelaAcoesSemanticas[36][1][1] = "comandos_36_1_1_21";
		tabelaAcoesSemanticas[36][1][2] = "comandos_36_1_2_3";
		tabelaAcoesSemanticas[36][1][13] = "comandos_36_1_13_4";
		tabelaAcoesSemanticas[36][1][12] = "comandos_36_1_12_5";
		tabelaAcoesSemanticas[36][1][14] = "comandos_36_1_14_6";
		tabelaAcoesSemanticas[20][3][41] = "comandos_20_3_41_21";
		tabelaAcoesSemanticas[31][1][16] = "comandos_31_1_16_33";
		tabelaAcoesSemanticas[31][3][62] = "comandos_31_3_62_33";
		tabelaAcoesSemanticas[31][3][60] = "comandos_31_3_60_33";
		tabelaAcoesSemanticas[31][1][17] = "comandos_31_1_17_33";
		tabelaAcoesSemanticas[31][1][18] = "comandos_31_1_18_33";
		tabelaAcoesSemanticas[31][1][19] = "comandos_31_1_19_33";
		tabelaAcoesSemanticas[9][2][0] = "comandos_9_2_0_17";
		tabelaAcoesSemanticas[15][2][0] = "comandos_15_2_0_12";
		tabelaAcoesSemanticas[15][4][0] = "comandos_15_4_0_12";
		tabelaAcoesSemanticas[26][4][0] = "comandos_26_4_0_27";
		tabelaAcoesSemanticas[4][3][40] = "comandos_4_3_40_16";
		tabelaAcoesSemanticas[10][2][0] = "comandos_10_2_0_12";
		tabelaAcoesSemanticas[10][4][0] = "comandos_10_4_0_12";
		tabelaAcoesSemanticas[10][3][41] = "comandos_10_3_41_11";
		tabelaAcoesSemanticas[32][3][41] = "comandos_32_3_41_34";
		tabelaAcoesSemanticas[21][3][123] = "comandos_21_3_123_22";
		this.mapaAcoesSemanticas.put("comandos", tabelaAcoesSemanticas);

		// Submáquina expressaoBooleana
		tabelaAcoesSemanticas = new String[13][7][256];
		tabelaAcoesSemanticas[0][1][7] = "expressaoBooleana_0_1_7_1";
		tabelaAcoesSemanticas[0][1][8] = "expressaoBooleana_0_1_8_1";
		tabelaAcoesSemanticas[0][3][40] = "expressaoBooleana_0_3_40_4";
		tabelaAcoesSemanticas[0][3][33] = "expressaoBooleana_0_3_33_5";
		tabelaAcoesSemanticas[7][1][16] = "expressaoBooleana_7_1_16_11";
		tabelaAcoesSemanticas[7][3][62] = "expressaoBooleana_7_3_62_11";
		tabelaAcoesSemanticas[7][3][60] = "expressaoBooleana_7_3_60_11";
		tabelaAcoesSemanticas[7][1][17] = "expressaoBooleana_7_1_17_11";
		tabelaAcoesSemanticas[7][1][18] = "expressaoBooleana_7_1_18_11";
		tabelaAcoesSemanticas[7][1][19] = "expressaoBooleana_7_1_19_11";
		tabelaAcoesSemanticas[2][1][16] = "expressaoBooleana_2_1_16_6";
		tabelaAcoesSemanticas[2][3][62] = "expressaoBooleana_2_3_62_6";
		tabelaAcoesSemanticas[2][3][60] = "expressaoBooleana_2_3_60_6";
		tabelaAcoesSemanticas[2][1][17] = "expressaoBooleana_2_1_17_6";
		tabelaAcoesSemanticas[2][1][18] = "expressaoBooleana_2_1_18_6";
		tabelaAcoesSemanticas[2][1][19] = "expressaoBooleana_2_1_19_6";
		tabelaAcoesSemanticas[8][3][41] = "expressaoBooleana_8_3_41_1";
		tabelaAcoesSemanticas[3][3][41] = "expressaoBooleana_3_3_41_9";
		tabelaAcoesSemanticas[3][3][41] = "expressaoBooleana_3_3_41_9";
		tabelaAcoesSemanticas[10][1][16] = "expressaoBooleana_10_1_16_12";
		tabelaAcoesSemanticas[10][3][62] = "expressaoBooleana_10_3_62_12";
		tabelaAcoesSemanticas[10][3][60] = "expressaoBooleana_10_3_60_12";
		tabelaAcoesSemanticas[10][1][17] = "expressaoBooleana_10_1_17_12";
		tabelaAcoesSemanticas[10][1][18] = "expressaoBooleana_10_1_18_12";
		tabelaAcoesSemanticas[10][1][19] = "expressaoBooleana_10_1_19_12";
		this.mapaAcoesSemanticas.put("expressaoBooleana", tabelaAcoesSemanticas);

		// Submáquina expressao
		tabelaAcoesSemanticas = new String[16][7][256];
		tabelaAcoesSemanticas[5][3][41] = "expressao_5_3_41_3";
		tabelaAcoesSemanticas[0][3][45] = "expressao_0_3_45_1";
		tabelaAcoesSemanticas[0][3][43] = "expressao_0_3_43_1";
		tabelaAcoesSemanticas[0][2][0] = "expressao_0_2_0_2";
		tabelaAcoesSemanticas[0][4][0] = "expressao_0_4_0_3";
		tabelaAcoesSemanticas[0][1][7] = "expressao_0_1_7_3";
		tabelaAcoesSemanticas[0][1][8] = "expressao_0_1_8_3";
		tabelaAcoesSemanticas[0][3][40] = "expressao_0_3_40_4";
		tabelaAcoesSemanticas[11][3][93] = "expressao_11_3_93_12";
		tabelaAcoesSemanticas[1][2][0] = "expressao_1_2_0_2";
		tabelaAcoesSemanticas[1][4][0] = "expressao_1_4_0_3";
		tabelaAcoesSemanticas[1][1][7] = "expressao_1_1_7_3";
		tabelaAcoesSemanticas[1][1][8] = "expressao_1_1_8_3";
		tabelaAcoesSemanticas[12][3][45] = "expressao_12_3_45_0";
		tabelaAcoesSemanticas[12][3][43] = "expressao_12_3_43_0";
		tabelaAcoesSemanticas[12][3][91] = "expressao_12_3_91_13";
		tabelaAcoesSemanticas[12][3][42] = "expressao_12_3_42_0";
		tabelaAcoesSemanticas[12][3][47] = "expressao_12_3_47_0";
		tabelaAcoesSemanticas[7][4][0] = "expressao_7_4_0_11";
		tabelaAcoesSemanticas[2][3][45] = "expressao_2_3_45_0";
		tabelaAcoesSemanticas[2][3][43] = "expressao_2_3_43_0";
		tabelaAcoesSemanticas[2][3][46] = "expressao_2_3_46_6";
		tabelaAcoesSemanticas[2][3][91] = "expressao_2_3_91_7";
		tabelaAcoesSemanticas[2][3][40] = "expressao_2_3_40_8";
		tabelaAcoesSemanticas[2][3][42] = "expressao_2_3_42_0";
		tabelaAcoesSemanticas[2][3][47] = "expressao_2_3_47_0";
		tabelaAcoesSemanticas[13][4][0] = "expressao_13_4_0_14";
		tabelaAcoesSemanticas[8][2][0] = "expressao_8_2_0_9";
		tabelaAcoesSemanticas[8][4][0] = "expressao_8_4_0_9";
		tabelaAcoesSemanticas[8][3][41] = "expressao_8_3_41_3";
		tabelaAcoesSemanticas[3][3][45] = "expressao_3_3_45_0";
		tabelaAcoesSemanticas[3][3][43] = "expressao_3_3_43_0";
		tabelaAcoesSemanticas[3][3][42] = "expressao_3_3_42_0";
		tabelaAcoesSemanticas[3][3][47] = "expressao_3_3_47_0";
		tabelaAcoesSemanticas[14][3][93] = "expressao_14_3_93_3";
		tabelaAcoesSemanticas[9][3][44] = "expressao_9_3_44_10";
		tabelaAcoesSemanticas[9][3][41] = "expressao_9_3_41_3";
		tabelaAcoesSemanticas[15][3][45] = "expressao_15_3_45_0";
		tabelaAcoesSemanticas[15][3][43] = "expressao_15_3_43_0";
		tabelaAcoesSemanticas[15][3][46] = "expressao_15_3_46_6";
		tabelaAcoesSemanticas[15][3][42] = "expressao_15_3_42_0";
		tabelaAcoesSemanticas[15][3][47] = "expressao_15_3_47_0";
		tabelaAcoesSemanticas[10][2][0] = "expressao_10_2_0_9";
		tabelaAcoesSemanticas[10][4][0] = "expressao_10_4_0_9";
		this.mapaAcoesSemanticas.put("expressao", tabelaAcoesSemanticas);

		// Submáquina texto
		tabelaAcoesSemanticas = new String[13][7][256];
		tabelaAcoesSemanticas[5][2][0] = "texto_5_2_0_6";
		tabelaAcoesSemanticas[5][4][0] = "texto_5_4_0_6";
		tabelaAcoesSemanticas[5][3][41] = "texto_5_3_41_1";
		tabelaAcoesSemanticas[11][3][93] = "texto_11_3_93_1";
		tabelaAcoesSemanticas[0][6][0] = "texto_0_6_0_1";
		tabelaAcoesSemanticas[0][2][0] = "texto_0_2_0_2";
		tabelaAcoesSemanticas[6][3][44] = "texto_6_3_44_7";
		tabelaAcoesSemanticas[6][3][41] = "texto_6_3_41_1";
		tabelaAcoesSemanticas[12][3][46] = "texto_12_3_46_3";
		tabelaAcoesSemanticas[12][3][43] = "texto_12_3_43_0";
		tabelaAcoesSemanticas[1][3][43] = "texto_1_3_43_0";
		tabelaAcoesSemanticas[7][2][0] = "texto_7_2_0_6";
		tabelaAcoesSemanticas[7][4][0] = "texto_7_4_0_6";
		tabelaAcoesSemanticas[2][3][46] = "texto_2_3_46_3";
		tabelaAcoesSemanticas[2][3][91] = "texto_2_3_91_4";
		tabelaAcoesSemanticas[2][3][40] = "texto_2_3_40_5";
		tabelaAcoesSemanticas[2][3][43] = "texto_2_3_43_0";
		tabelaAcoesSemanticas[8][3][93] = "texto_8_3_93_9";
		tabelaAcoesSemanticas[9][3][91] = "texto_9_3_91_10";
		tabelaAcoesSemanticas[9][3][43] = "texto_9_3_43_0";
		tabelaAcoesSemanticas[4][4][0] = "texto_4_4_0_8";
		tabelaAcoesSemanticas[10][4][0] = "texto_10_4_0_11";
		this.mapaAcoesSemanticas.put("texto", tabelaAcoesSemanticas);
	}
	
	public void executarAcaoSemantica(String nomeSubmaquina, int estado, Token token) {
		String[][][] tabelaAcoesSemanticas = this.mapaAcoesSemanticas.get(nomeSubmaquina);
		
		int valor;
		int classeToken = token.getClasse();
		
		// Ajusta o valor de acordo com a classe do Token.
		if(classeToken == Token.CLASSE_IDENTIFICADOR || classeToken == Token.CLASSE_NUMERO_INTEIRO || classeToken == Token.CLASSE_STRING)
			valor = 0;
		else
			valor = token.getID();
		
		try {
			String m = tabelaAcoesSemanticas[estado][token.getClasse()][valor];
			if(m != null) {
				// Executa a ação semântica usando Reflection.
				Class<?> classe = Class.forName("compilador.analisador.semantico.AcoesSemanticas");
				Method metodo = classe.getMethod(m, new Class[0]);
				metodo.invoke(null, new Object[0]);
			}
		} catch(Exception e) {
			
		}
	}
	
	public static void armazenarToken(Token token) {
		ParametrosAcoesSemanticas.TOKEN = token;
		
		if(token.getClasse() == Token.CLASSE_IDENTIFICADOR)
			ParametrosAcoesSemanticas.TOKEN_ID_ANTERIOR = token;
	}
}