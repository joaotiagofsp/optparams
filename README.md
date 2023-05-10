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
Ainda em construção...
```
Programa ::= Expressao

Expressao ::= Valor
	| ExpUnaria
	| ExpBinaria
	| ExpDeclaracao
	| Id
	| Aplicacao
	| IfThenElse

ExpUnaria ::= "-" Expressao 
		| "not" Expressao 
		| "length" Expressao

ExpBinaria ::= Expressao "+" Expressao
		| Expressao "-" Expressao
		| Expressao "and" Expressao
		| Expressao "or" Expressao
		| Expressao "==" Expressao
		| Expressao "++" Expressao

ExpDeclaracao ::= "let" DeclaracaoFuncional "in" Expressao

Valor ::= ValorConcreto

ValorConcreto ::= ValorInteiro 
		| ValorBooleano 
		| ValorString
      		| ValorFuncao	<<--------------------------- ALTERAÇÃO

ValorFuncao ::= "fn" ListParametro "." Expressao  <<--------- ALTERAÇÃO

DeclaracaoFuncional ::= DecVariavel
			| DecFuncao
			| DecComposta

DecVariavel ::= "var" Id "=" Expressao

DecFuncao ::= "fun" ListParametro "=" Expressao	<<----------- ALTERAÇÃO

DecComposta ::= DeclaracaoFuncional "," DeclaracaoFuncional

Parametro ::= ParametroObrigatorio   <<---------------------- ALTERAÇÃO 
		| ParametroOpcional

ParametroObrigatorio ::= Id   <<----------------------------- ALTERAÇÃO

ParametroOpcional ::= Id "?" "(" Expressao ")"	<<----------- ALTERAÇÃO

ListId ::= Id  
	|  Id ListId

Aplicacao:= Id"(" ListExp ")"

ListExp ::= Expressao  
	|  Expressao, ListExp

ListParametro ::= Parametro	<<--------------------------- ALTERAÇÃO
		|  Parametro ListParametro

IfThenElse ::= "if" Expressao "then" Expressao "else" Expressao
```

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