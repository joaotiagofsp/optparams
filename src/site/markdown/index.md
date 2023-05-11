## OptParams

### Disciplina

(IN1007) Paradigmas de Linguagens de Programação - Cin/UFPE

### Professor

Augusto Sampaio

### Equipe

* Cleber Tavares de Moura | ctm@cin.ufpe.br
* João Tiago Ferreira Soares Pessoa | jtfsp@cin.ufpe.br

### Contextualização

Uma função com parâmetros opcionais deve ser executada mesmo que parte dos seus argumentos de entrada não tenha sido passada na chamada da mesma.

No caso em que um argumento não é passado, a função assume um valor padrão e segue a sua execução.

### Escopo do Projeto

**Estender a Linguagem Funcional 1** apresentada na disciplina para que a declaração de funções ofereça a possibilidade de **tornar um parâmetro opcional e definir um valor default para o mesmo**.

```
let fun soma a b?(1) = a + b in soma(1)
```

O bloco ``?(1)`` define o parâmetro ``b`` da função ``soma`` como <em>opcional</em>.

A string dentro dos parêntesis ``(1)`` define o valor padrão atribuído ao parâmetro, caso um valor não seja expressamente informado na chamada da função.