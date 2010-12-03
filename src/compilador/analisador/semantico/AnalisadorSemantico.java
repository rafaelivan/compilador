package compilador.analisador.semantico;

import java.lang.reflect.Method;

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
		tabelaAcoesSemanticas[5][3][125] = "deletarEscopo";
		tabelaAcoesSemanticas[0][1][5] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][3] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][4] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][6] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][9] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][15] = "acaoPadrao";
		tabelaAcoesSemanticas[11][1][3] = "acaoPadrao";
		tabelaAcoesSemanticas[11][1][4] = "acaoPadrao";
		tabelaAcoesSemanticas[11][1][6] = "acaoPadrao";
		tabelaAcoesSemanticas[11][1][10] = "acaoPadrao";
		tabelaAcoesSemanticas[1][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[12][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[13][3][123] = "criarEscopo";
		tabelaAcoesSemanticas[2][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[8][3][41] = "acaoPadrao";
		tabelaAcoesSemanticas[3][3][123] = "criarEscopo";
		tabelaAcoesSemanticas[9][3][123] = "criarEscopo";
		tabelaAcoesSemanticas[15][3][125] = "deletarEscopo";
		tabelaAcoesSemanticas[4][3][40] = "acaoPadrao";
		tabelaAcoesSemanticas[10][3][125] = "deletarEscopo";
		this.mapaAcoesSemanticas.put("programa", tabelaAcoesSemanticas);

		// Submáquina parametros
		tabelaAcoesSemanticas = new String[15][7][256];
		tabelaAcoesSemanticas[5][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][3] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][4] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][6] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][9] = "acaoPadrao";
		tabelaAcoesSemanticas[11][3][91] = "acaoPadrao";
		tabelaAcoesSemanticas[11][3][44] = "acaoPadrao";
		tabelaAcoesSemanticas[6][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[1][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[12][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[7][3][123] = "criarEscopo";
		tabelaAcoesSemanticas[13][3][93] = "acaoPadrao";
		tabelaAcoesSemanticas[2][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[14][3][44] = "acaoPadrao";
		tabelaAcoesSemanticas[3][3][91] = "acaoPadrao";
		tabelaAcoesSemanticas[3][3][44] = "acaoPadrao";
		tabelaAcoesSemanticas[9][3][125] = "deletarEscopo";
		tabelaAcoesSemanticas[4][1][3] = "acaoPadrao";
		tabelaAcoesSemanticas[4][1][4] = "acaoPadrao";
		tabelaAcoesSemanticas[4][1][6] = "acaoPadrao";
		tabelaAcoesSemanticas[4][1][10] = "acaoPadrao";
		tabelaAcoesSemanticas[10][3][93] = "acaoPadrao";
		this.mapaAcoesSemanticas.put("parametros", tabelaAcoesSemanticas);

		// Submáquina declaracoes
		tabelaAcoesSemanticas = new String[16][7][256];
		tabelaAcoesSemanticas[5][3][91] = "acaoPadrao";
		tabelaAcoesSemanticas[5][3][44] = "acaoPadrao";
		tabelaAcoesSemanticas[5][3][59] = "acaoPadrao";
		tabelaAcoesSemanticas[11][3][93] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][11] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][3] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][4] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][6] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][9] = "acaoPadrao";
		tabelaAcoesSemanticas[6][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[1][1][3] = "acaoPadrao";
		tabelaAcoesSemanticas[1][1][4] = "acaoPadrao";
		tabelaAcoesSemanticas[1][1][6] = "acaoPadrao";
		tabelaAcoesSemanticas[1][1][9] = "acaoPadrao";
		tabelaAcoesSemanticas[12][3][91] = "acaoPadrao";
		tabelaAcoesSemanticas[12][3][44] = "acaoPadrao";
		tabelaAcoesSemanticas[12][3][59] = "acaoPadrao";
		tabelaAcoesSemanticas[7][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[2][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[13][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[8][3][123] = "criarEscopo";
		tabelaAcoesSemanticas[3][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[14][3][93] = "acaoPadrao";
		tabelaAcoesSemanticas[15][3][44] = "acaoPadrao";
		tabelaAcoesSemanticas[15][3][59] = "acaoPadrao";
		tabelaAcoesSemanticas[4][1][3] = "acaoPadrao";
		tabelaAcoesSemanticas[4][1][4] = "acaoPadrao";
		tabelaAcoesSemanticas[4][1][6] = "acaoPadrao";
		tabelaAcoesSemanticas[4][1][10] = "acaoPadrao";
		tabelaAcoesSemanticas[10][3][125] = "deletarEscopo";
		this.mapaAcoesSemanticas.put("declaracoes", tabelaAcoesSemanticas);

		// Submáquina comandos
		tabelaAcoesSemanticas = new String[37][7][256];
		tabelaAcoesSemanticas[27][3][93] = "acaoPadrao";
		tabelaAcoesSemanticas[5][3][40] = "acaoPadrao";
		tabelaAcoesSemanticas[16][3][41] = "acaoPadrao";
		tabelaAcoesSemanticas[11][3][59] = "acaoPadrao";
		tabelaAcoesSemanticas[0][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][0] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][2] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][13] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][12] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][14] = "acaoPadrao";
		tabelaAcoesSemanticas[22][3][125] = "deletarEscopo";
		tabelaAcoesSemanticas[17][3][40] = "acaoPadrao";
		tabelaAcoesSemanticas[28][3][61] = "acaoPadrao";
		tabelaAcoesSemanticas[1][3][46] = "acaoPadrao";
		tabelaAcoesSemanticas[1][3][91] = "acaoPadrao";
		tabelaAcoesSemanticas[1][3][61] = "acaoPadrao";
		tabelaAcoesSemanticas[1][3][40] = "acaoPadrao";
		tabelaAcoesSemanticas[23][3][93] = "acaoPadrao";
		tabelaAcoesSemanticas[34][3][123] = "criarEscopo";
		tabelaAcoesSemanticas[12][3][44] = "acaoPadrao";
		tabelaAcoesSemanticas[12][3][41] = "acaoPadrao";
		tabelaAcoesSemanticas[29][3][46] = "acaoPadrao";
		tabelaAcoesSemanticas[29][3][61] = "acaoPadrao";
		tabelaAcoesSemanticas[2][3][40] = "acaoPadrao";
		tabelaAcoesSemanticas[35][3][125] = "deletarEscopo";
		tabelaAcoesSemanticas[13][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[13][1][0] = "acaoPadrao";
		tabelaAcoesSemanticas[13][1][2] = "acaoPadrao";
		tabelaAcoesSemanticas[13][1][13] = "acaoPadrao";
		tabelaAcoesSemanticas[13][1][12] = "acaoPadrao";
		tabelaAcoesSemanticas[13][1][14] = "acaoPadrao";
		tabelaAcoesSemanticas[8][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[19][1][16] = "acaoPadrao";
		tabelaAcoesSemanticas[19][3][62] = "acaoPadrao";
		tabelaAcoesSemanticas[19][3][60] = "acaoPadrao";
		tabelaAcoesSemanticas[19][1][17] = "acaoPadrao";
		tabelaAcoesSemanticas[19][1][18] = "acaoPadrao";
		tabelaAcoesSemanticas[19][1][19] = "acaoPadrao";
		tabelaAcoesSemanticas[25][3][91] = "acaoPadrao";
		tabelaAcoesSemanticas[25][3][61] = "acaoPadrao";
		tabelaAcoesSemanticas[3][3][40] = "acaoPadrao";
		tabelaAcoesSemanticas[36][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[36][1][0] = "acaoPadrao";
		tabelaAcoesSemanticas[36][1][1] = "acaoPadrao";
		tabelaAcoesSemanticas[36][1][2] = "acaoPadrao";
		tabelaAcoesSemanticas[36][1][13] = "acaoPadrao";
		tabelaAcoesSemanticas[36][1][12] = "acaoPadrao";
		tabelaAcoesSemanticas[36][1][14] = "acaoPadrao";
		tabelaAcoesSemanticas[20][3][41] = "acaoPadrao";
		tabelaAcoesSemanticas[31][1][16] = "acaoPadrao";
		tabelaAcoesSemanticas[31][3][62] = "acaoPadrao";
		tabelaAcoesSemanticas[31][3][60] = "acaoPadrao";
		tabelaAcoesSemanticas[31][1][17] = "acaoPadrao";
		tabelaAcoesSemanticas[31][1][18] = "acaoPadrao";
		tabelaAcoesSemanticas[31][1][19] = "acaoPadrao";
		tabelaAcoesSemanticas[9][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[15][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[15][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[26][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[4][3][40] = "acaoPadrao";
		tabelaAcoesSemanticas[10][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[10][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[10][3][41] = "acaoPadrao";
		tabelaAcoesSemanticas[32][3][41] = "acaoPadrao";
		tabelaAcoesSemanticas[21][3][123] = "criarEscopo";
		this.mapaAcoesSemanticas.put("comandos", tabelaAcoesSemanticas);

		// Submáquina expressaoBooleana
		tabelaAcoesSemanticas = new String[13][7][256];
		tabelaAcoesSemanticas[0][1][7] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][8] = "acaoPadrao";
		tabelaAcoesSemanticas[0][3][40] = "acaoPadrao";
		tabelaAcoesSemanticas[0][3][33] = "acaoPadrao";
		tabelaAcoesSemanticas[7][1][16] = "acaoPadrao";
		tabelaAcoesSemanticas[7][3][62] = "acaoPadrao";
		tabelaAcoesSemanticas[7][3][60] = "acaoPadrao";
		tabelaAcoesSemanticas[7][1][17] = "acaoPadrao";
		tabelaAcoesSemanticas[7][1][18] = "acaoPadrao";
		tabelaAcoesSemanticas[7][1][19] = "acaoPadrao";
		tabelaAcoesSemanticas[2][1][16] = "acaoPadrao";
		tabelaAcoesSemanticas[2][3][62] = "acaoPadrao";
		tabelaAcoesSemanticas[2][3][60] = "acaoPadrao";
		tabelaAcoesSemanticas[2][1][17] = "acaoPadrao";
		tabelaAcoesSemanticas[2][1][18] = "acaoPadrao";
		tabelaAcoesSemanticas[2][1][19] = "acaoPadrao";
		tabelaAcoesSemanticas[8][3][41] = "acaoPadrao";
		tabelaAcoesSemanticas[3][3][41] = "acaoPadrao";
		tabelaAcoesSemanticas[3][3][41] = "acaoPadrao";
		tabelaAcoesSemanticas[10][1][16] = "acaoPadrao";
		tabelaAcoesSemanticas[10][3][62] = "acaoPadrao";
		tabelaAcoesSemanticas[10][3][60] = "acaoPadrao";
		tabelaAcoesSemanticas[10][1][17] = "acaoPadrao";
		tabelaAcoesSemanticas[10][1][18] = "acaoPadrao";
		tabelaAcoesSemanticas[10][1][19] = "acaoPadrao";
		this.mapaAcoesSemanticas.put("expressaoBooleana", tabelaAcoesSemanticas);

		// Submáquina expressao
		tabelaAcoesSemanticas = new String[16][7][256];
		tabelaAcoesSemanticas[5][3][41] = "acaoPadrao";
		tabelaAcoesSemanticas[0][3][45] = "acaoPadrao";
		tabelaAcoesSemanticas[0][3][43] = "acaoPadrao";
		tabelaAcoesSemanticas[0][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[0][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][7] = "acaoPadrao";
		tabelaAcoesSemanticas[0][1][8] = "acaoPadrao";
		tabelaAcoesSemanticas[0][3][40] = "acaoPadrao";
		tabelaAcoesSemanticas[11][3][93] = "acaoPadrao";
		tabelaAcoesSemanticas[1][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[1][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[1][1][7] = "acaoPadrao";
		tabelaAcoesSemanticas[1][1][8] = "acaoPadrao";
		tabelaAcoesSemanticas[12][3][45] = "acaoPadrao";
		tabelaAcoesSemanticas[12][3][43] = "acaoPadrao";
		tabelaAcoesSemanticas[12][3][91] = "acaoPadrao";
		tabelaAcoesSemanticas[12][3][42] = "acaoPadrao";
		tabelaAcoesSemanticas[12][3][47] = "acaoPadrao";
		tabelaAcoesSemanticas[7][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[2][3][45] = "acaoPadrao";
		tabelaAcoesSemanticas[2][3][43] = "acaoPadrao";
		tabelaAcoesSemanticas[2][3][46] = "acaoPadrao";
		tabelaAcoesSemanticas[2][3][91] = "acaoPadrao";
		tabelaAcoesSemanticas[2][3][40] = "acaoPadrao";
		tabelaAcoesSemanticas[2][3][42] = "acaoPadrao";
		tabelaAcoesSemanticas[2][3][47] = "acaoPadrao";
		tabelaAcoesSemanticas[13][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[8][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[8][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[8][3][41] = "acaoPadrao";
		tabelaAcoesSemanticas[3][3][45] = "acaoPadrao";
		tabelaAcoesSemanticas[3][3][43] = "acaoPadrao";
		tabelaAcoesSemanticas[3][3][42] = "acaoPadrao";
		tabelaAcoesSemanticas[3][3][47] = "acaoPadrao";
		tabelaAcoesSemanticas[14][3][93] = "acaoPadrao";
		tabelaAcoesSemanticas[9][3][44] = "acaoPadrao";
		tabelaAcoesSemanticas[9][3][41] = "acaoPadrao";
		tabelaAcoesSemanticas[15][3][45] = "acaoPadrao";
		tabelaAcoesSemanticas[15][3][43] = "acaoPadrao";
		tabelaAcoesSemanticas[15][3][46] = "acaoPadrao";
		tabelaAcoesSemanticas[15][3][42] = "acaoPadrao";
		tabelaAcoesSemanticas[15][3][47] = "acaoPadrao";
		tabelaAcoesSemanticas[10][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[10][4][0] = "acaoPadrao";
		this.mapaAcoesSemanticas.put("expressao", tabelaAcoesSemanticas);

		// Submáquina texto
		tabelaAcoesSemanticas = new String[13][7][256];
		tabelaAcoesSemanticas[5][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[5][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[5][3][41] = "acaoPadrao";
		tabelaAcoesSemanticas[11][3][93] = "acaoPadrao";
		tabelaAcoesSemanticas[0][6][0] = "acaoPadrao";
		tabelaAcoesSemanticas[0][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[6][3][44] = "acaoPadrao";
		tabelaAcoesSemanticas[6][3][41] = "acaoPadrao";
		tabelaAcoesSemanticas[12][3][46] = "acaoPadrao";
		tabelaAcoesSemanticas[12][3][43] = "acaoPadrao";
		tabelaAcoesSemanticas[1][3][43] = "acaoPadrao";
		tabelaAcoesSemanticas[7][2][0] = "acaoPadrao";
		tabelaAcoesSemanticas[7][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[2][3][46] = "acaoPadrao";
		tabelaAcoesSemanticas[2][3][91] = "acaoPadrao";
		tabelaAcoesSemanticas[2][3][40] = "acaoPadrao";
		tabelaAcoesSemanticas[2][3][43] = "acaoPadrao";
		tabelaAcoesSemanticas[8][3][93] = "acaoPadrao";
		tabelaAcoesSemanticas[9][3][91] = "acaoPadrao";
		tabelaAcoesSemanticas[9][3][43] = "acaoPadrao";
		tabelaAcoesSemanticas[4][4][0] = "acaoPadrao";
		tabelaAcoesSemanticas[10][4][0] = "acaoPadrao";
		this.mapaAcoesSemanticas.put("texto", tabelaAcoesSemanticas);
	}
	
	public void executarAcaoSemantica(String nomeSubmaquina, int estado, Token token) {
		String[][][] tabelaAcoesSemanticas = this.mapaAcoesSemanticas.get(nomeSubmaquina);
		
		int valor = token.getID(); // Arrumar.
		
		//System.out.println(nomeSubmaquina + " - " + estado + " - " + token.getClasse()+" - "+token.getID());
		
		String m = tabelaAcoesSemanticas[estado][token.getClasse()][valor];
		
		if(m != null) {
			try {
				// Executa a ação semântica usando Reflection.
				Class<?> classe = Class.forName("compilador.analisador.semantico.AcoesSemanticas");
				Method metodo = classe.getMethod(m, new Class[0]);
				metodo.invoke(null, new Object[0]);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
}