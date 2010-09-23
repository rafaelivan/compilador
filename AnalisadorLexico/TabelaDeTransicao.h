typedef struct 
{
    int estados;
    int **matriz;
}_TabelaDeTransicao;

typedef _TabelaDeTransicao* TabelaDeTransicao;

TabelaDeTransicao CarregaTabela();
