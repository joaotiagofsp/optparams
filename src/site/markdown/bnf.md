## BNF
[Programa](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/lf1/Programa.java) ::= [Expressao](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/Expressao.java)

Expressao ::= [Valor](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/Valor.java) <br />
>	| [ExpUnaria](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ExpUnaria.java) <br />
>	| [ExpBinaria](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ExpBinaria.java) <br />
>	| [ExpDeclaracao](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/lf1/expressoes/ExpDeclaracao.java) <br />
>	| [Id](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le2/expressoes/Id.java) <br />
>	| [Aplicacao](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/optparam/expressoes/Aplicacao.java) <br />
>	| [IfThenElse](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/lf1/expressoes/IfThenElse.java) <br />

Valor ::= [ValorConcreto](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ValorConcreto.java)

ValorConcreto ::= [ValorInteiro](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ValorInteiro.java) | [ValorBooleano](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ValorBooleano.java) | [ValorString](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ValorString.java) <br />

ExpUnaria ::= "-" Expressao <br />
>	| "not" Expressao <br />
>	| "length" Expressao <br />

ExpBinaria ::= Expressao "+" Expressao <br />
>	| Expressao "-" Expressao <br />
>	| Expressao "and" Expressao <br />
>	| Expressao "or" Expressao <br />
>	| Expressao "==" Expressao <br />
>	| Expressao "++" Expressao <br />

ExpDeclaracao ::= "let" [DeclaracaoFuncional](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/lf1/expressoes/DeclaracaoFuncional.java) "in" Expressao


DeclaracaoFuncional ::= [DecVariavel](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/lf1/expressoes/DecVariavel.java) <br />
>	| [DecFuncao](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/optparam/expressoes/DecFuncao.java) <br />
>	| [DecComposta](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/lf1/expressoes/DecComposta.java)

DecVariavel ::= "var" Id "=" Expressao

DecFuncao ::= "fun" ListaParametro "=" Expressao 

DecComposta ::= DeclaracaoFuncional "," DeclaracaoFuncional

ListaParametro ::= [Parametro](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/Parametro.java) <br />
>	| Parametro ListaParametro

Parametro ::= [ParametroObrigatorio](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ParametroObrigatorio.java) <br />
>	| [ParametroOpcional](http://raw.githubusercontent.com/joaotiagofsp/optparams/main/src/main/java/plp/le1/expressoes/ParametroOpcional.java)

ParametroObrigatorio ::= Id

ParametroOpcional ::= Id "?" "(" Expressao ")"

Aplicacao ::= Id"(" ListExp ")"

ListExp ::= Expressao <br />
>	| Expressao, ListExp

IfThenElse ::= "if" Expressao "then" Expressao "else" Expressao
