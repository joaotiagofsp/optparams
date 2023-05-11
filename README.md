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

## BNF
[Programa](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/lf1/Programa.java) ::= [Expressao](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/Expressao.java)

Expressao ::= [Valor](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/Valor.java) <br />
>	| [ExpUnaria](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ExpUnaria.java) <br />
>	| [ExpBinaria](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ExpBinaria.java) <br />
>	| [ExpDeclaracao](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/lf1/expressoes/ExpDeclaracao.java) <br />
>	| [Id](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le2/expressoes/Id.java) <br />
>	| [Aplicacao](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/optparam/expressoes/Aplicacao.java) <br />
>	| [IfThenElse](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/lf1/expressoes/IfThenElse.java) <br />

Valor ::= [ValorConcreto](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ValorConcreto.java)

ValorConcreto ::= [ValorInteiro](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ValorInteiro.java) | [ValorBooleano](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ValorBooleano.java) | [ValorString](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ValorString.java) <br />

ExpUnaria ::= "-" Expressao <br />
>	| "not" Expressao <br />
>	| "length" Expressao <br />

ExpBinaria ::= Expressao "+" Expressao <br />
>	| Expressao "-" Expressao <br />
>	| Expressao "and" Expressao <br />
>	| Expressao "or" Expressao <br />
>	| Expressao "==" Expressao <br />
>	| Expressao "++" Expressao <br />

ExpDeclaracao ::= "let" [DeclaracaoFuncional](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/lf1/expressoes/DeclaracaoFuncional.java) "in" Expressao


DeclaracaoFuncional ::= [DecVariavel](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/lf1/expressoes/DecVariavel.java) <br />
>	| [DecFuncao](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/optparam/expressoes/DecFuncao.java) <br />
>	| [DecComposta](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/lf1/expressoes/DecComposta.java)

DecVariavel ::= "var" Id "=" Expressao

DecFuncao ::= "fun" ListaParametro "=" Expressao <img src="https://icones.pro/wp-content/uploads/2021/04/nouveau-symbole-vert.png" width="18" height="18"/>

DecComposta ::= DeclaracaoFuncional "," DeclaracaoFuncional

ListaParametro ::= [Parametro](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/Parametro.java) <br />
>	| Parametro ListaParametro

Parametro ::= [ParametroObrigatorio](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ParametroObrigatorio.java) <br />
>	| [ParametroOpcional](https://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ParametroOpcional.java)

ParametroObrigatorio ::= Id

ParametroOpcional ::= Id "?" "(" Expressao ")"

Aplicacao ::= Id"(" ListExp ")"

ListExp ::= Expressao <br />
>	| Expressao, ListExp

IfThenElse ::= "if" Expressao "then" Expressao "else" Expressao


## Extensões realizadas em LF1
1. Reutilização do código base de classes da LF1:
2. Implementação da classe abstrata ```Parametro```
3. Implementação das classes concretas ```ParametroObrigatorio``` e ```ParametroOpcional``` que herdam da classe ```Parametro```
4. Implementação da classe ```ParametroObrigatorioAposOpcionalException```
6. Alterações na classe ```DefFuncao```:
* Lista de parâmetros com tipo genérico ```Parametro```;
* método ```checaTipo( )``` e ```checaTipoParametrosOpcionais( )``` para suportar os parâmetros opcionais;
7. Alterações diversas na classe ```TipoFuncao```
8. Alterações na classe ```Aplicacao```: método ```avaliar( )```, ```checaTipo( )``` e ```resolveParametersBindings( )```
9. Alterações na Gramática (arquivo .jj):
* ```PValor( )```
* ```PDeclFuncao( )```
* ```PAplicacao( )```
* ```PParametro( )```
* ```PParametroObrigatorio( )```
* ```PParametroOpcional( )```
* ```PListaParametro( )```
* ```PListaExpr( )```

## Slides da apresentação

Disponível [aqui](https://docs.google.com/presentation/d/1Pb0hrHNTp-KSgiCw7io2ZlQeYakQCfIfzsrDbpknP34/edit?usp=sharing)

## Publicando o site

Para publicação do site, execute o seguinte comando:

```
mvn clean site site:stage scm-publish:publish-scm
```

O site será publicado no endereço: [https://joaotiagofsp.github.io/optparams](https://joaotiagofsp.github.io/optparams)
