## BNF
[Programa](src/main/java/plp/lf1/Programa.java) ::= Expressao

[Expressao](src/main/java/plp/le1/expressoes/Expressao.java) ::= Valor <br />
>	| ExpUnaria <br />
>	| ExpBinaria <br />
>	| ExpDeclaracao <br />
>	| [Id](src/main/java/plp/le2/expressoes/Id.java) <br />
>	| Aplicacao <br />
>	| IfThenElse <br />

[ExpUnaria](src/main/java/plp/le1/expressoes/ExpUnaria.java) ::= "-" Expressao <br />
>	| "not" Expressao <br />
>	| "length" Expressao <br />

[ExpBinaria](src/main/java/plp/le1/expressoes/ExpBinaria.java) ::= Expressao "+" Expressao <br />
>	| Expressao "-" Expressao <br />
>	| Expressao "and" Expressao <br />
>	| Expressao "or" Expressao <br />
>	| Expressao "==" Expressao <br />
>	| Expressao "++" Expressao <br />

[ExpDeclaracao](src/main/java/plp/lf1/expressoes/ExpDeclaracao.java) ::= "let" DeclaracaoFuncional "in" Expressao

[Valor](src/main/java/plp/le1/expressoes/Valor.java) ::= ValorConcreto

[ValorConcreto](src/main/java/plp/le1/expressoes/ValorConcreto.java) ::= [ValorInteiro](src/main/java/plp/le1/expressoes/ValorInteiro.java) <br />
>	| [ValorBooleano](src/main/java/plp/le1/expressoes/ValorBooleano.java) <br />
>	| [ValorString](src/main/java/plp/le1/expressoes/ValorString.java) <br />
> | ValorFuncao	<img src="https://icones.pro/wp-content/uploads/2021/04/nouveau-symbole-vert.png" width="18" height="18"/>

[ValorFuncao](src/main/java/plp/le2/expressoes/ValorFuncao.java) ::= "fn" ListParametro "." Expressao <img src="https://icones.pro/wp-content/uploads/2021/04/nouveau-symbole-vert.png" width="18" height="18"/>

[DeclaracaoFuncional](src/main/java/plp/lf1/expressoes/DeclaracaoFuncional.java) ::= DecVariavel <br />
>	| DecFuncao <br />
>	| DecComposta

[DecVariavel](src/main/java/plp/lf1/expressoes/DecVariavel.java) ::= "var" Id "=" Expressao

[DecFuncao](src/main/java/plp/optparam/expressoes/DecFuncao.java) ::= "fun" ListParametro "=" Expressao <img src="https://icones.pro/wp-content/uploads/2021/04/nouveau-symbole-vert.png" width="18" height="18"/>

[DecComposta](src/main/java/plp/lf1/expressoes/DecComposta.java) ::= DeclaracaoFuncional "," DeclaracaoFuncional

[Parametro](src/main/java/plp/le1/expressoes/Parametro.java) ::= ParametroObrigatorio <img src="https://icones.pro/wp-content/uploads/2021/04/nouveau-symbole-vert.png" width="18" height="18"/> <br />
>	| ParametroOpcional

[ParametroObrigatorio](src/main/java/plp/le1/expressoes/ParametroObrigatorio.java) ::= Id <img src="https://icones.pro/wp-content/uploads/2021/04/nouveau-symbole-vert.png" width="18" height="18"/> 

[ParametroOpcional](src/main/java/plp/le1/expressoes/ParametroOpcional.java) ::= Id "?" "(" Expressao ")"	<img src="https://icones.pro/wp-content/uploads/2021/04/nouveau-symbole-vert.png" width="18" height="18"/>

ListId ::= Id <br />
>	| Id ListId

[Aplicacao](src/main/java/plp/optparam/expressoes/Aplicacao.java) ::= Id"(" ListExp ")"

ListExp ::= Expressao <br />
>	| Expressao, ListExp

ListParametro ::= Parametro	<img src="https://icones.pro/wp-content/uploads/2021/04/nouveau-symbole-vert.png" width="18" height="18"/> <br />
>	| Parametro ListParametro

[IfThenElse](src/main/java/plp/lf1/expressoes/IfThenElse.java) ::= "if" Expressao "then" Expressao "else" Expressao