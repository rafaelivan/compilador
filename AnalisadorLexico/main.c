#include <stdio.h>
#include <stdlib.h>
#include "TabelaDeTransicao.h"

int main(int argc, char *argv[])
{
	TabelaDeTransicao tab = CarregaTabela();
	int i;
	int j;			
	for(i=0;i<(*tab).estados;i++)
	{
		for(j=0;j<256;j++)
        {
			printf("\n %d = %d ", j, (*tab).matriz[i][j]);
		}
		printf("\n");
	system("PAUSE");
	}	
	return 0;
}
