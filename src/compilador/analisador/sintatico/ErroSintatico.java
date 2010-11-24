package compilador.analisador.sintatico;

public class ErroSintatico {
	
	private int linha;
	
	private int coluna;
	
	private int classeToken;
	
	public ErroSintatico(int linha, int coluna, int classeToken) {
		this.linha = linha;
		this.coluna = coluna;
		this.classeToken = classeToken;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	public int getClasseToken() {
		return classeToken;
	}
}