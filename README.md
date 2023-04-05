# OptParams
Projeto da disciplina Paradigmas de Linguagens de Programação (IN1007) - Cin/UFPE

## Equipe
* Cleber Tavares de Moura | ctm@cin.ufpe.br
* João Tiago Ferreira Soares Pessoa | jtfsp@cin.ufpe.br

## Contextualização
Um Função com parâmetros opcionais deve ser executada mesmo que parte dos seus argumentos de entrada não tenha sido passada na chamada da mesma.
No caso em que um argumento não é passado, a Função assume um valor default e segue a sua execução.

## Escopo do Projeto
**Estender a Linguagem Funcional 1** apresentada na disciplina para que a declaração de funções ofereça a possibilidade de **tornar um parâmetro opcional**.
```
let fun soma a(0) b(0) = a + b
```
O bloco ```(0)``` modifica o parâmetro da função.

A string ```(0)``` define o valor default assumido caso um valor não seja expressamente informado na chamada da função.

## Extensões realizadas em LF1
1. Implementação da classe ```ParametroFuncao```

```
public class ParametroFuncao {
	private Id id;
	private Valor valorDefault;

	public ParametroFuncao(Id id, Valor valorDefault) {
		this.id = id;
		this.valorDefault = valorDefault;
	}

	@Override
	public String toString() {
		return "ParametroFuncao [id=" + id + ", valorDefault=" + valorDefault + "]";
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public Valor getValorDefault() {
		return valorDefault;
	}

	public void setValorDefault(Valor valorDefault) {
		this.valorDefault = valorDefault;
	}
	
	public ParametroFuncao clone() {
		return this;
	}

}
```
3. Modificação nas classes ```DefFuncao```, ```TipoFuncao```, ```Aplicacao``` e ```DecFuncao```
4. Modificação das regras de produção ```PListaId()```, ```PDeclFuncao()``` e ```PAplicacao()```

```
List PListaId() :
{
  Id id;
  ParametroFuncao param;
  Valor valorDefault;
  List < ParametroFuncao > retorno = null;
}
{
  (
    ///////////////////////////////////////////////////////////////////////////////
    // ALTERADA A ESTRUTURA DA DECLARACAO DA FUNCAO PARA INCLUIR VALORES DEFAULT //
    ///////////////////////////////////////////////////////////////////////////////
    id = PId() < LPAREN > valorDefault = PValor() < RPAREN >
    {
      if (retorno == null)
      {
        retorno = new ArrayList < ParametroFuncao > ();
        retorno.add(new ParametroFuncao(id, valorDefault));
      }
      else
      {
        retorno.add(new ParametroFuncao(id, valorDefault));
      }
    }
  )*
  {
    return retorno;
  }
}
```
```
DeclaracaoFuncional PDeclFuncao() :
{
  Id id;
  Expressao expressao;
  DeclaracaoFuncional retorno;
  /////////////////////////////////////////////////
  ///   ALTERADO O TIPO DOS ELEMENTOS DA LISTA  ///
  /////////////////////////////////////////////////
  List < ParametroFuncao > lista;
}
{
  (
    < FUNC > id = PId() lista = PListaId() < ASSIGN > expressao = PExpressao()
  )
  {
    return new DecFuncao(id, lista, expressao);
  }
}
```
```
Expressao PAplicacao() :
{
  List expressoes = null;
  Expressao expressao;
  Id id;
}
{
  id = PId() < LPAREN >
  (
     expressao = PExpressao()
    {
      expressoes = new ArrayList();
      expressoes.add(expressao);
    }
    (
      < COMMA > expressao = PExpressao()
      {
        expressoes.add(expressao);
      }
    )*
    
  )*
  < RPAREN >
  {
    return new Aplicacao(id, expressoes);
  }
}
```
