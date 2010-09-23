#include "TabelaDeClasses.h"
#include <stdio.h>

TabelaDeClasses CarregaTabelaDeClasses()
{
	TabelaDeClasses tabClasses = (TabelaDeClasses)malloc(sizeof(_TabelaDeClasses));
	FILE *tabela = fopen("tabelaDeClasses.txt","r");
	fscanf(tabela,"%d",&((*tabClasses).classeMAX));
	(*tabClasses).matriz = (int**)malloc(sizeof(int*)*((*tabClasses).classeMAX+1));
	int i;
	for(i=0;i<(*tabClasses).classeMAX+1;i++)
	{
		(*tabClasses).matriz[i] = (int*)malloc(sizeof(int));
	}
	for(i=0;i<(*tabClasses).classeMAX+1;i++)
	{
		(*tabClasses).matriz[i][0] = -1;
	}
	char entrada = ' ';
	int classe = 0;
	int estadoAtual = 0;
	while(fscanf(tabela,"%c", &entrada) != EOF)
	{
        if(entrada != ' ' && entrada != '\n')
        {
            estadoAtual = (int)entrada-48;       
			if(fscanf(tabela,"=%d", &classe)<=0)
			{
				printf("Erro na leitura da classe : %d", estadoAtual);
		        system("PAUSE");	
				exit(1);
			}
			else
			{
				(*tabClasses).matriz[estadoAtual][0] = classe;
			}
        }
	}
	fclose(tabela);
	return tabClasses;
}

int ClasseDoEstado(TabelaDeClasses tabClasses, int estado)
{
    return (*tabClasses).matriz[estado][0];
}
