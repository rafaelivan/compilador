package compilador.helper;

/**
 * Classe com métodos de auxílio para trabalhar com vetores. 
 * 
 * @author rafaelivan
 *
 */
public class ArrayHelper {
	
	/**
	 * Aloca um vetor de inteiros com o tamanho mínimo necessário, de acordo com o vetor recebido.
	 * O final do vetor recebido é composto por uma série de valores -1, que são desconsiderados.
	 * 
	 * @param vetor
	 * @return um vetor alocado na memória com tamanho mínimo.
	 */
	public static int[] alocarVetor(int[] vetor) {
		int tamanho = 0;
		
		for(int i = 0; i < vetor.length; i++) {
			if(vetor[i] == -1) {
				tamanho = i;
				break;
			}
		}
		
		int[] novoVetor = new int[tamanho];
		
		for(int i = 0; i < novoVetor.length; i++)
			novoVetor[i] = vetor[i];
		
		return novoVetor;
	}
	
	/**
	 * Compara dois vetores de inteiros.
	 * 
	 * @param vetor1
	 * @param vetor2
	 * @return <code>true</code>, caso os vetores sejam iguais. <code>false</code>, caso contrário.
	 */
	public static boolean compararVetoresInt(int[] vetor1, int[] vetor2) {		
		// Compara os tamanhos.
		if(vetor1.length != vetor2.length)
			return false;
		
		// Compara cada valor.
		for(int i = 0; i < vetor1.length; i++)
			if(vetor1[i] != vetor2[i])
				return false;
		
		return true;
	}
	
	/**
	 * Compara dois vetores de chars.
	 * 
	 * @param vetor1
	 * @param vetor2
	 * @return <code>true</code>, caso os vetores sejam iguais. <code>false</code>, caso contrário.
	 */
	public static boolean compararVetoresChar(char[] vetor1, char[] vetor2) {
		// Transforma o primeiro vetor.
		int[] v1 = new int[vetor1.length];
		for(int i = 0; i < v1.length; i++)
			v1[i] = (int)vetor1[i];
		
		// Transforma o segundo vetor.
		int[] v2 = new int[vetor2.length];
		for(int i = 0; i < v2.length; i++)
			v2[i] = (int)vetor2[i];
		
		return ArrayHelper.compararVetoresInt(v1, v2);
	}
	
	/**
	 * Verifica se um elemento está em um vetor.
	 * 
	 * @param vetor
	 * @param elemento
	 * @return <code>true</code>, caso o elemento esteja no vetor. <code>false</code>, caso contrário.
	 */
	public static boolean elementoNoVetor(int[] vetor, int elemento) {
		for(int i = 0; i < vetor.length; i++)
			if(elemento == vetor[i])
				return true;
		
		return false;
	}
	
	/**
	 * Concatena dois vetores.
	 * 
	 * @param vetor1
	 * @param vetor2
	 * @return retorna o vetor resultante da concatenação.
	 */
	public static char[] concatenarVetoresChar(char[] v1, char[] v2) {
		char[] resultado = new char[v1.length + v2.length];
		
		for(int i = 0; i < v1.length; i++)
			resultado[i] = v1[i];
		
		for(int i = 0; i < v2.length; i++)
			resultado[i + v1.length] = v2[i];
		
		return resultado;
	}
}