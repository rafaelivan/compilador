#include "TabelaDeTransicao.h"
#include <stdio.h>

TabelaDeTransicao CarregaTabela()
{
	TabelaDeTransicao tabTrans = (TabelaDeTransicao)malloc(sizeof(_TabelaDeTransicao));
	FILE *tabela = fopen("tabela.txt","r");
	fscanf(tabela,"%d",&((*tabTrans).estados));
	(*tabTrans).matriz = (int**)malloc(sizeof(int*)*(*tabTrans).estados);
	int i;
	for(i=0;i<(*tabTrans).estados;i++)
	{
		(*tabTrans).matriz[i] = (int*)malloc(sizeof(int)*256);
	}
	int j;
	for(i=0;i<(*tabTrans).estados;i++)
	{
		for(j=0;j<256;j++)
        {
			(*tabTrans).matriz[i][j] = -1;
		}
	}
	char coluna = ' ';
	int proxEstado = -1;
	int estadoAtual = -1;
	while(fscanf(tabela,"%c", &coluna) != EOF)
	{
		if(coluna == '\n')
		{
			estadoAtual++;
		}
		else
		{
            if(coluna != ' ')
            {
    			if(fscanf(tabela,"=%d", &proxEstado)<=0)
    			{
    				printf("Erro na leitura do proximo estado do automato");
    		        system("PAUSE");	
    				exit(1);
    			}
    			else
    			{
    				(*tabTrans).matriz[estadoAtual][(int)coluna] = proxEstado;
    			}
            }
		}	
	}
	fclose(tabela);
	return tabTrans;
}
