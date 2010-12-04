package compilador.gerador.acoes;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import compilador.analisador.lexico.Token;
import compilador.analisador.semantico.TabelaPalavrasReservadas;

public class GeradorAcoesSemanticas {

	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	
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
	
	public void gerar() {
		NodeList nodeList = doc.getElementsByTagName("name");
		String nome = nodeList.item(0).getChildNodes().item(0).getNodeValue();
		
		nodeList = doc.getElementsByTagName("transition");
		
		System.out.println("// Subm‡quina "+nome);
		System.out.println("tabelaAcoesSemanticas = new String["+doc.getElementsByTagName("state").getLength()+"][7][256];");
		
		int estadoOrigem = -1;
		int estadoDestino = -1;
		int classeToken = -1;
		int valorToken = -1;
		
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
							
							System.out.println("tabelaAcoesSemanticas["+estadoOrigem+"]["+classeToken+"][0] = \""+nome+"_"+estadoOrigem+"_"+classeToken+"_0_"+estadoDestino+"\";");
						} else if(valor.equals("\"inteiro\"")) {
							classeToken = Token.CLASSE_NUMERO_INTEIRO;
							
							System.out.println("tabelaAcoesSemanticas["+estadoOrigem+"]["+classeToken+"][0] = \""+nome+"_"+estadoOrigem+"_"+classeToken+"_0_"+estadoDestino+"\";");
						} else if(valor.equals("\"string\"")) {
							classeToken = Token.CLASSE_STRING;
							
							System.out.println("tabelaAcoesSemanticas["+estadoOrigem+"]["+classeToken+"][0] = \""+nome+"_"+estadoOrigem+"_"+classeToken+"_0_"+estadoDestino+"\";");
						} else if(valor.length() == 3) {
							classeToken = Token.CLASSE_CARACTER_ESPECIAL;
							valorToken = (int)valor.charAt(1);
							System.out.println("tabelaAcoesSemanticas["+estadoOrigem+"]["+classeToken+"]["+valorToken+"] = \""+nome+"_"+estadoOrigem+"_"+classeToken+"_"+valorToken+"_"+estadoDestino+"\";");
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
							
							System.out.println("tabelaAcoesSemanticas["+estadoOrigem+"]["+classeToken+"]["+valorToken+"] = \""+nome+"_"+estadoOrigem+"_"+classeToken+"_"+valorToken+"_"+estadoDestino+"\";");
						}
					}	
				}
			}
		}
		
		System.out.println("this.mapaAcoesSemanticas.put(\""+nome+"\", tabelaAcoesSemanticas);");
		System.out.println("");
	}
	
	public static void main(String[] args) {
		GeradorAcoesSemanticas g = new GeradorAcoesSemanticas();
		
		System.out.println("String[][][] tabelaAcoesSemanticas;");
		
		g.parseXML("src/compilador/config/programa.xml");
		g.gerar();
		
		g.parseXML("src/compilador/config/parametros.xml");
		g.gerar();
		
		g.parseXML("src/compilador/config/declaracoes.xml");
		g.gerar();
		
		g.parseXML("src/compilador/config/comandos.xml");
		g.gerar();
		
		g.parseXML("src/compilador/config/expressaoBooleana.xml");
		g.gerar();
		
		g.parseXML("src/compilador/config/expressao.xml");
		g.gerar();
		
		g.parseXML("src/compilador/config/texto.xml");
		g.gerar();
	}
}