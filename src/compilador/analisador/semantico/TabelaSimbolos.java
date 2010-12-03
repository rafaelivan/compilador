package compilador.analisador.semantico;

import compilador.estruturas.Int;
import compilador.estruturas.ListaLigada;
import compilador.estruturas.Mapa;
import compilador.helper.ArrayHelper;


public class TabelaSimbolos {
	
	private Mapa<Int,TSLinha> linhas;
	private static int PROXIMA_CHAVE = 0;
	
	public TabelaSimbolos()
	{
		this.linhas = new Mapa<Int, TSLinha>();
	}
	
	public int recuperarChave(int[] buffer) {
		ListaLigada<Int> chaves = this.linhas.chaves();
		for(int i = 0; i< chaves.tamanho(); i++)
		{
			if(linhas.get(new Int(i)) != null && ArrayHelper.compararVetoresInt(linhas.get(new Int(i)).getNome(), buffer))
			{
				return i;
			}
		}
		return -1;
	}
	
	public int inserirSimbolo(int[] nome)
	{
		int chave =  PROXIMA_CHAVE; 
		this.linhas.put(new Int(PROXIMA_CHAVE), new TSLinha(nome));
		PROXIMA_CHAVE++;
		return chave;
	}
}
