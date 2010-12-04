package compilador.analisador.semantico;

import java.util.Arrays;

import compilador.estruturas.String;

public class TSLinha {	
	private int[] nome;									// nome do simbolo
	private int tipo;									// tipo do simbolo: int, char, string, boolean, "struct"
	private int categoria;								// categoria do simbolo: VARIAVEL, FUNCAO, VETOR, MATRIZ, PARAMETRO
	private String endereco;							// pode ser HexaDecimal
	private int tamanho;								// numero de bytes do simbolo
	private boolean declarado;							// Se ele já foi declarado antes de ser usado, são setado true se está numa declaração
	private TSLinha[] parametros = new TSLinha[100];
	private String rotulo;								// se for função, terá um rótulo.
	
	public TSLinha(int[] nome)
	{
		this.nome = nome;
		for(int i = 0; i < this.parametros.length; i++)
		{
			this.parametros[i] =  null;
		}
	}
	
	public int[] getNome() {
		return nome;
	}

	public void setNome(int[] nome) {
		this.nome = nome;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public boolean isDeclarado() {
		return declarado;
	}

	public void setDeclarado(boolean declarado) {
		this.declarado = declarado;
	}

	public TSLinha[] getParametros() {
		return parametros;
	}

	public void setParametros(TSLinha[] parametros) {
		this.parametros = parametros;
	}
	
	public String getRotulo() {
		return rotulo;
	}

	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}

	public void addToParametros(TSLinha parametro) {
		for(int i = 0; i < this.parametros.length; i++)
		{
			if(this.parametros[i] == null){
				this.parametros[i] = parametro;
			}
		}
	}
	
	public int numberOfParameters()
	{
		for(int i = 0; i < this.parametros.length; i++)
		{
			if(this.parametros[i] == null){
				return i;
			}
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoria;
		result = prime * result + (declarado ? 1231 : 1237);
		result = prime * result
				+ ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + Arrays.hashCode(nome);
		result = prime * result + Arrays.hashCode(parametros);
		result = prime * result + ((rotulo == null) ? 0 : rotulo.hashCode());
		result = prime * result + tamanho;
		result = prime * result + tipo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TSLinha other = (TSLinha) obj;
		if (categoria != other.categoria)
			return false;
		if (declarado != other.declarado)
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (!Arrays.equals(nome, other.nome))
			return false;
		if (!Arrays.equals(parametros, other.parametros))
			return false;
		if (rotulo == null) {
			if (other.rotulo != null)
				return false;
		} else if (!rotulo.equals(other.rotulo))
			return false;
		if (tamanho != other.tamanho)
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
	
}
