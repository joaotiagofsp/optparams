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
