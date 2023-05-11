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

DecFuncao ::= "fun" ListaParametro "=" Expressao 

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
