package compilador.helper;

/**
 * Classe com métodos de auxílio para trabalhar com vetores. 
 * 
 * @author rafaelivan
 *
 */
public class ArrayHelper {
	
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
}