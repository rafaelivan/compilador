typedef struct 
{
    int classeMAX;
    int **matriz;
}_TabelaDeClasses;

typedef _TabelaDeClasses* TabelaDeClasses;

TabelaDeClasses CarregaTabelaDeClasses();

//Retorna -1 se o estado não for final, isto é não está na tabela
int ClasseDoEstado(TabelaDeClasses tabClasses, int estado);

