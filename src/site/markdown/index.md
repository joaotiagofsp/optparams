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

### Exemplos de uso

1. Função ``soma`` com 3 parâmetros: ``x``, ``y`` e ``z``, sendo ``y`` e ``z`` opcionais e com valores padrões: 

```
let fun soma x y?(0) z?(2) = x + y + z in soma(1, 3)

OptParam Parser Parser v.0.0.1:  Programa analisado com sucesso
OptParam Parser v.0.0.1:  Resultado do programa = 6
```

2. Testando a checagem de Tipo na função ``soma`` com 3 parâmetros: ``x``, ``y`` e ``z``, sendo ``y`` e ``z`` opcionais e com valores padrões. 

```
let fun soma x y?(0) z?(2) = x + y + z in soma(1, "teste")

OptParam Parser Parser v.0.0.1:  Programa analisado com sucesso
OptParam Parser v.0.0.1: Erro de tipo
```

3. Função ``concat`` com 2 parâmetros: ``x`` e ``y``, sendo ``y`` opcional e com valor padrão. 

```
let fun concat x y?("-padrao") = x ++ y in concat("1")

OptParam Parser Parser v.0.0.1:  Programa analisado com sucesso
OptParam Parser v.0.0.1:  Resultado do programa = "1-padrao"
```