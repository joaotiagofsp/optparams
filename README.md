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
[Programa](src/main/java/plp/lf1/Programa.java) ::= Expressao

[Expressao](src/main/java/plp/le1/expressoes/Expressao.java) ::= Valor <br />
	| ExpUnaria <br />
	| ExpBinaria <br />
	| ExpDeclaracao <br />
	| Id <br />
	| Aplicacao <br />
	| IfThenElse <br />

[ExpUnaria](src/main/java/plp/le1/expressoes/ExpUnaria.java) ::= "-" Expressao <br />
		| "not" Expressao <br />
		| "length" Expressao <br />

[ExpBinaria](src/main/java/plp/le1/expressoes/ExpBinaria.java) ::= Expressao "+" Expressao <br />
		| Expressao "-" Expressao <br />
		| Expressao "and" Expressao <br />
		| Expressao "or" Expressao <br />
		| Expressao "==" Expressao <br />
		| Expressao "++" Expressao <br />

[ExpDeclaracao](src/main/java/plp/lf1/expressoes/ExpDeclaracao.java) ::= "let" DeclaracaoFuncional "in" Expressao

[Valor](src/main/java/plp/le1/expressoes/Valor.java) ::= ValorConcreto

[ValorConcreto](src/main/java/plp/le1/expressoes/ValorConcreto.java) ::= ValorInteiro <br />
		| ValorBooleano <br />
		| ValorString <br />
      		| ValorFuncao	<<--------------------------- ALTERAÇÃO

[ValorFuncao](src/main/java/plp/le2/expressoes/ValorFuncao.java) ::= "fn" ListParametro "." Expressao  <<--------- ALTERAÇÃO

[DeclaracaoFuncional](src/main/java/plp/lf1/expressoes/DeclaracaoFuncional.java) ::= DecVariavel <br />
			| DecFuncao <br />
			| DecComposta

[DecVariavel](src/main/java/plp/lf1/expressoes/DecVariavel.java) ::= "var" Id "=" Expressao

[DecFuncao](src/main/java/plp/optparam/expressoes/DecFuncao.java) ::= "fun" ListParametro "=" Expressao	<<----------- ALTERAÇÃO

[DecComposta](src/main/java/plp/lf1/expressoes/DecComposta.java) ::= DeclaracaoFuncional "," DeclaracaoFuncional

[Parametro](src/main/java/plp/le1/expressoes/Parametro.java) ::= ParametroObrigatorio   <<---------------------- ALTERAÇÃO <br />
		| ParametroOpcional

[ParametroObrigatorio](src/main/java/plp/le1/expressoes/ParametroObrigatorio.java) ::= Id   <<----------------------------- ALTERAÇÃO

[ParametroOpcional](src/main/java/plp/le1/expressoes/ParametroOpcional.java) ::= Id "?" "(" Expressao ")"	<<----------- ALTERAÇÃO

ListId ::= Id  <br />
	|  Id ListId

[Aplicacao](src/main/java/plp/optparam/expressoes/Aplicacao.java) ::= Id"(" ListExp ")"

ListExp ::= Expressao  <br />
	|  Expressao, ListExp

ListParametro ::= Parametro	<<--------------------------- ALTERAÇÃO <br />
		|  Parametro ListParametro

IfThenElse ::= "if" Expressao "then" Expressao "else" Expressao

## Extensões realizadas em LF1
1. Reutilização do código base de classes da LF2:
* Interface ```ValorAbstrato```
* Classe ```ValorFuncao```
2. Implementação da classe abstrata ```Parametro```
3. Implementação das classes ```ParametroObrigatorio``` e ```ParametroOpcional```
4. Implementação da classe ```ParametroObrigatorioAposOpcionalException```
5. Alterações na classe ```ValorFuncao```: métodos ```reduzir( )``` e ```clone( )```
6. Alterações na classe ```DefFuncao```: método ```checaTipo( )``` e ```checaTipoParametrosOpcionais( )```
7. Alterações diversas na classe ```TipoFuncao```
8. Alterações na classe ```Aplicacao```: método ```avaliar( )```, ```checaTipo( )```, ```resolveParametersBindings( )```, ```getFuncType( )``` e ```reduzir( )```
9. Alterações na Gramática (arquivo .jj):
* ```PValor( )```
* ```PValorFuncao( )```
* ```PDeclFuncao( )```
* ```PAplicacao( )```
* ```PParametro( )```
* ```PParametroObrigatorio( )```
* ```PParametroOpcional( )```
* ```PListaParametro( )```
* ```PListaExpr( )```
* ```PListaExpBinaria( )```

## Slides da apresentação

Disponível neste [LINK](https://docs.google.com/presentation/d/1Pb0hrHNTp-KSgiCw7io2ZlQeYakQCfIfzsrDbpknP34/edit?usp=sharing)

## Publicando o site

Para publicação do site, execute o seguinte comando:

```
clean site site:stage scm-publish:publish-scm
```

O site será publicado no endereço: [https://joaotiagofsp.github.io/optparams](https://joaotiagofsp.github.io/optparams)
