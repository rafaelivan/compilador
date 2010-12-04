package compilador.gerador.submaquinas;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import compilador.analisador.lexico.Token;
import compilador.analisador.semantico.TabelaPalavrasReservadas;
import compilador.analisador.sintatico.Submaquina;
import compilador.estruturas.Mapa;
import compilador.helper.ArrayHelper;

public class GeradorSubmaquinas {
	
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	
	private Mapa<compilador.estruturas.String, Integer> submaquinas;
	
	
	public GeradorSubmaquinas(Mapa<compilador.estruturas.String, Integer> submaquinas) {
		this.submaquinas = submaquinas;
	}
	
	public void parseXML(String filename) {
		File file = new File(filename);
		
		try {
			this.dbf = DocumentBuilderFactory.newInstance();
			this.db = this.dbf.newDocumentBuilder();
			this.doc = this.db.parse(file);
			this.doc.getDocumentElement().normalize();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Submaquina gerarSubmaquina() {
		return new Submaquina(this.nomeSubmaquina(), this.estadoInicial(), this.estadosFinais(), this.tabelaTransicao(), this.tabelaChamadaSubmaquinas());
	}
	
	private compilador.estruturas.String nomeSubmaquina() {
		NodeList nodeList = doc.getElementsByTagName("name");
		String nome = nodeList.item(0).getChildNodes().item(0).getNodeValue();
		
		Integer idSubmaquina = this.submaquinas.get(new compilador.estruturas.String(nome.toCharArray()));
		if(idSubmaquina == null) {
			this.submaquinas.put(new compilador.estruturas.String(nome.toCharArray()), this.submaquinas.tamanho());
		}
		
		return new compilador.estruturas.String(nome.toCharArray());
	}
	
	private int estadoInicial() {
		NodeList nodeList = doc.getElementsByTagName("initial");
		String estadoInicial = nodeList.item(0).getParentNode().getAttributes().getNamedItem("id").getNodeValue();
		
		return Integer.parseInt(estadoInicial);
	}
	
	private int[] estadosFinais() {
		int[] estadosFinais = new int[10]; // FIXME
		int indice = 0;
		for(int i = 0; i < estadosFinais.length; i++)
			estadosFinais[i] = -1;
		
		
		NodeList nodeList = doc.getElementsByTagName("final");
		
		String estadoFinal;
		for(int i = 0; i < nodeList.getLength(); i++) {
			estadoFinal = nodeList.item(i).getParentNode().getAttributes().getNamedItem("id").getNodeValue();
			estadosFinais[indice] = Integer.parseInt(estadoFinal);
			indice++;
		}
		
		return ArrayHelper.alocarVetor(estadosFinais);
	}
	
	private int[][][] tabelaTransicao() {
		NodeList nodeList = doc.getElementsByTagName("state");
		
		int[][][] tabelaTransicao = new int[nodeList.getLength()][7][256]; // 7 = total de classe de tokens.
		for(int i = 0; i < tabelaTransicao.length; i++)
			for(int j = 0; j < tabelaTransicao[i].length; j++)
				for(int k = 0; k < tabelaTransicao[i][j].length; k++)
					tabelaTransicao[i][j][k] = -1;
		
		int estadoOrigem = -1;
		int estadoDestino = -1;
		int classeToken = -1;
		int valorToken = -1;
		
		nodeList = doc.getElementsByTagName("transition");
		
		NodeList child;
		for(int i = 0; i < nodeList.getLength(); i++) {
			child = nodeList.item(i).getChildNodes();
			
			for(int j = 0; j < child.getLength(); j++) {
				if(child.item(j).getNodeName().equals("from")) {
					estadoOrigem = Integer.parseInt(child.item(j).getChildNodes().item(0).getNodeValue());
				} else if(child.item(j).getNodeName().equals("to")) {
					estadoDestino = Integer.parseInt(child.item(j).getChildNodes().item(0).getNodeValue());
				} else if(child.item(j).getNodeName().equals("read")) {
					String valor = child.item(j).getChildNodes().item(0).getNodeValue();
					
					if(valor.startsWith("\"")) {
						// Consumo de token.
						
						if(valor.equals("\"id\"")) {
							classeToken = Token.CLASSE_IDENTIFICADOR;
							
							for(int k = 0; k < tabelaTransicao[estadoOrigem][classeToken].length; k++)
								tabelaTransicao[estadoOrigem][classeToken][k] = estadoDestino;
						} else if(valor.equals("\"inteiro\"")) {
							classeToken = Token.CLASSE_NUMERO_INTEIRO;
							
							for(int k = 0; k < tabelaTransicao[estadoOrigem][classeToken].length; k++)
								tabelaTransicao[estadoOrigem][classeToken][k] = estadoDestino;
						} else if(valor.equals("\"string\"")) {
							classeToken = Token.CLASSE_STRING;
							
							for(int k = 0; k < tabelaTransicao[estadoOrigem][classeToken].length; k++)
								tabelaTransicao[estadoOrigem][classeToken][k] = estadoDestino;
						} else if(valor.length() == 3) {
							classeToken = Token.CLASSE_CARACTER_ESPECIAL;
							valorToken = (int)valor.charAt(1);
							tabelaTransicao[estadoOrigem][classeToken][valorToken] = estadoDestino;
						} else {
							char[] charArray = valor.substring(1, valor.length() - 1).toCharArray();
							int[] aux = new int[charArray.length];
							for(int k = 0; k < charArray.length; k++) {
								aux[k] = (int)charArray[k];
							}
							
							int chave = TabelaPalavrasReservadas.getInstance().recuperarChave(aux); 
							if(chave != -1) {
								classeToken = Token.CLASSE_PALAVRA_RESERVADA;
								valorToken = chave;
							}
							
							tabelaTransicao[estadoOrigem][classeToken][valorToken] = estadoDestino;
						}
					}	
				}
			}
		}
		
		return tabelaTransicao;
	}
	
	private int [][] tabelaChamadaSubmaquinas() {
		NodeList nodeList = doc.getElementsByTagName("state");
		
		int[][] tabelaChamadaSubmaquina = new int[nodeList.getLength()][10];
		for(int i = 0; i < tabelaChamadaSubmaquina.length; i++)
			for(int j = 0; j < tabelaChamadaSubmaquina[i].length; j++)
				tabelaChamadaSubmaquina[i][j] = -1;
		
		int estadoOrigem = -1;
		int estadoDestino = -1;
		Integer idSubmaquina = -1;
		
		nodeList = doc.getElementsByTagName("transition");
		
		NodeList child;
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			child = nodeList.item(i).getChildNodes();
			
			for(int j = 0; j < child.getLength(); j++) {
				if(child.item(j).getNodeName().equals("from")) {
					estadoOrigem = Integer.parseInt(child.item(j).getChildNodes().item(0).getNodeValue());
				} else if(child.item(j).getNodeName().equals("to")) {
					estadoDestino = Integer.parseInt(child.item(j).getChildNodes().item(0).getNodeValue());
				} else if(child.item(j).getNodeName().equals("read")) {
					String valor = child.item(j).getChildNodes().item(0).getNodeValue();
					
					if(!valor.startsWith("\"")) {
						// Chamada de subm‡quina.
						idSubmaquina = submaquinas.get(new compilador.estruturas.String(valor.toCharArray()));

						
						if(idSubmaquina == null) {
							idSubmaquina = submaquinas.chaves().tamanho();
							submaquinas.put(new compilador.estruturas.String(valor.toCharArray()), idSubmaquina);
						}
						
						tabelaChamadaSubmaquina[estadoOrigem][idSubmaquina] = estadoDestino;
					}
				}
			}
		}
		
		return tabelaChamadaSubmaquina;
	}
	
	public void teste() {
		NodeList nodeList = doc.getElementsByTagName("name");
	}
	
	public static void main(String[] args) {
		Mapa<compilador.estruturas.String, Integer> mapa = new Mapa<compilador.estruturas.String, Integer>();
		
		GeradorSubmaquinas ge = new GeradorSubmaquinas(mapa);
		ge.parseXML("src/compilador/config/programa.xml");
		ge.teste();	
	}	
}