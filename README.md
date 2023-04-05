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
```
public class DefFuncao {
	
	// TIPO DA LISTA ATUALIZADO
	protected List<ParametroFuncao> args;

	protected Expressao exp;

	public DefFuncao(List<ParametroFuncao> args, Expressao exp) {
		this.args = args;
		this.exp = exp;
	}

	public List<ParametroFuncao> getListaParametros() {
		return args;
	}

	public Expressao getExp() {
		return exp;
	}

	public int getAridade() {
		return args.size();
	}

	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		for (ParametroFuncao arg : args) {
			ambiente.map(arg.getId(), new TipoPolimorfico());
		}

		boolean result = exp.checaTipo(ambiente);

		ambiente.restaura();

		return result;
	}

	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		for (ParametroFuncao arg : args) {
			ambiente.map(arg.getId(), new TipoPolimorfico());
		}
		exp.checaTipo(ambiente);

		// Compoe o tipo desta funcao do resultado para o primeiro parametro.
		Tipo result = exp.getTipo(ambiente);

		// Obtem o tipo inferido de cada parametro.
		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		Tipo argTipo;
		for (int i = 0; i < getAridade(); i++) {
			argTipo = ((TipoPolimorfico) ambiente.get(args.get(i).getId())).inferir();
			params.add(argTipo);
		}
		result = new TipoFuncao(params, result);

		ambiente.restaura();

		return result;
	}

	public DefFuncao clone() {
		List<ParametroFuncao> novaLista = new ArrayList<ParametroFuncao>(this.args.size());

		for (ParametroFuncao arg : this.args) {
			novaLista.add(arg.clone());
		}

		return new DefFuncao(novaLista, this.exp.clone());
	}
}
```
```
public class TipoFuncao implements Tipo {

	private List<Tipo> dominio;

	private Tipo imagem;

	public TipoFuncao(List<Tipo> dominio, Tipo imagem) {
		this.dominio = dominio;
		this.imagem = imagem;
	}

	public String getNome() {
		return String.format("(%s) -> %s", ToStringProvider.listToString(dominio, " x"), imagem);
	}

	public List<Tipo> getDominio() {
		return dominio;
	}

	public Tipo getImagem() {
		return imagem;
	}

	public boolean eBooleano() {
		return imagem.eBooleano();
	}

	public boolean eInteiro() {
		return imagem.eInteiro();
	}

	public boolean eString() {
		return imagem.eString();
	}

	public boolean eValido() {
		boolean ret = dominio != null;
		for (Tipo t : this.dominio) {
			ret &= t.eValido();
		}
		ret &= imagem != null && imagem.eValido();
		return ret;
	}

	public boolean eIgual(Tipo tipo) {
		boolean ret = true;
		if (tipo instanceof TipoPolimorfico)
			return tipo.eIgual(this);

		if (tipo instanceof TipoFuncao) {
			TipoFuncao tipoFuncao = (TipoFuncao) tipo;
			if (this.dominio.size() != tipoFuncao.dominio.size())
				return false;
			Iterator<Tipo> it = this.dominio.iterator();
			for (Tipo t : tipoFuncao.dominio) {
				ret &= t.eIgual(it.next());
			}
			return ret && this.imagem.eIgual(tipoFuncao.imagem);
		}

		return ret;
	}

	public Tipo intersecao(Tipo outroTipo) {
		if (outroTipo.eIgual(this))
			return this;
		else
			return null;
	}

	@Override
	public String toString() {
		return getNome();
	}

	private void limparTiposCuringas() {
		for (Tipo tDom : getDominio()) {
			if (tDom instanceof TipoPolimorfico) {
				((TipoPolimorfico) tDom).limpar();
			}

		}
		if (getImagem() instanceof TipoPolimorfico) {
			((TipoPolimorfico) getImagem()).limpar();
		}
	}

	/**
	 * METODO ALTERADO PARA NAO CONSIDERAR ERRO QDO O NUMERO DE ARGUMENTOS NA
	 * CHAMADA DA FUNCAO DIFERE DO NUMERO DE PARAMETROS DA SUA DECLARACAO
	 * 
	 * @param parametrosFormais
	 * @return
	 */
	private boolean checkArgumentListSize(List<? extends Expressao> parametrosFormais) {

		// return getDominio().size() == parametrosFormais.size();
		// AQUI AINDA CABE MELHORIAS
		return true;
	}

	private boolean checkArgumentTypes(AmbienteCompilacao ambiente, List<? extends Expressao> parametrosFormais)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		boolean result = true;

		Iterator<Tipo> it = getDominio().iterator();
		Tipo tipoArg;
		if (parametrosFormais != null) {
			for (Expressao valorReal : parametrosFormais) {
	
				result &= valorReal.checaTipo(ambiente);
	
				tipoArg = valorReal.getTipo(ambiente);
				Tipo tipoDom = it.next();
	
				result &= tipoArg.eIgual(tipoDom);
			}
		}
		return result;
	}

	public boolean checaTipo(AmbienteCompilacao ambiente, List<? extends Expressao> parametrosFormais) {
		boolean result = checkArgumentListSize(parametrosFormais) && checkArgumentTypes(ambiente, parametrosFormais);
		limparTiposCuringas();
		return result;
	}

	public Tipo getTipo(AmbienteCompilacao ambiente, List<? extends Expressao> parametrosFormais) {
		// Infere os parametros
		Iterator<Tipo> it = getDominio().iterator();
		Tipo tipoArg;
		for (Expressao valorReal : parametrosFormais) {
			tipoArg = valorReal.getTipo(ambiente);
			tipoArg.eIgual(it.next());
		}

		// Obtem o resultado.
		Tipo ret = getImagem();
		// Caso seja um tipo polimorfico procura a mais especifica instanciacao.
		while (ret instanceof TipoPolimorfico) {
			if (((TipoPolimorfico) ret).getTipoInstanciado() == null) {
				break;
			}
			ret = ((TipoPolimorfico) ret).getTipoInstanciado();
		}

		limparTiposCuringas();
		return ret;
	}

}
```

5. Modificação das regras de produção ```PListaId()```, ```PDeclFuncao()``` e ```PAplicacao()```

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
