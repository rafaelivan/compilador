package compilador.analisador.semantico;

import compilador.estruturas.Int;
import compilador.estruturas.ListaLigada;
import compilador.estruturas.Mapa;
import compilador.helper.ArrayHelper;


public class TabelaSimbolos {
	
	private Mapa<Int,TSLinha> linhas;
	
	public TabelaSimbolos()
	{
		this.linhas = new Mapa<Int, TSLinha>();
	}
	
	public int recuperarChave(int[] buffer) {
		ListaLigada<Int> chaves = this.linhas.chaves();
		
		for(int i = 0; i < chaves.tamanho(); i++) {
			if(linhas.get(chaves.get(i)) != null && ArrayHelper.compararVetoresInt(linhas.get(chaves.get(i)).getNome(), buffer)) {
				return chaves.get(i).getValue();
			}
		}
		return -1;
	}
	
	public void inserirSimbolo(int chave, int[] nome)
	{
		this.linhas.put(new Int(chave), new TSLinha(nome));
	}
	
	public TSLinha getTSLinha(int chave) {
		return this.linhas.get(new Int(chave));
	}
}
