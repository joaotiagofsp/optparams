# OptParams
Projeto da disciplina Paradigmas de Linguagens de Programação (IN1007) - Cin/UFPE

## Equipe
* Cleber Tavares de Moura | ctm@cin.ufpe.br
* João Tiago Ferreira Soares Pessoa | jtfsp@cin.ufpe.br

## Contextualização
Um Função com parâmetros opcionais deve ser executada mesmo que parte dos seus argumentos de entrada não tenha sido passada na chamada da mesma.
No caso em que um argumento não é passado, a Função assume um valor default e segue a sua execução.

## Escopo do Projeto
**Estender a Linguagem Funcional 1** apresentada na disciplina para que a declaração de funções ofereça a possibilidade de **tornar um parâmetro opcional e indicar um valor default para quando não for informado**.

```
let fun soma a(1) b(2) = a + b in soma()
```

Os blocos ``(1)`` e ``(2)`` tornam os parâmetros da função opcionais e define o valor default caso um valor não seja expressamente informado na chamada da função.
