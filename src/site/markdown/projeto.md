# OptParams
Projeto da disciplina Paradigmas de Linguagens de Programação (IN1007) - Cin/UFPE

## Equipe
* Cleber Tavares de Moura | ctm@cin.ufpe.br
* João Tiago Ferreira Soares Pessoa | jtfsp@cin.ufpe.br

## Contextualização
Um Função com parâmetros opcionais deve ser executada mesmo que parte dos seus argumentos de entrada não tenha sido passada na chamada da mesma.
No caso em que um argumento não é passado, a Função assume um valor default e segue a sua execução.

## Escopo do Projeto
**Estender a Linguagem Funcional 1** apresentada na disciplina para que a declaração de funções ofereça a possibilidade de **tornar um parâmetro opcional e definir um valor default para o mesmo**.

```
let fun soma a(1) b(2) = a + b
```

Os blocos ``(1)`` e ``(2)`` tornam os parâmetros da função opcionais e definem o valor default caso um valor não seja expressamente informado na chamada da função.