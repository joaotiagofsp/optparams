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
Ainda em construção. Abaixo segue a versão original da LF1:
```
Programa ::= Expressao

Expressao ::= Valor
	| ExpUnaria
	| ExpBinaria
	| ExpDeclaracao
	| Id
	| Aplicacao
	| IfThenElse


Valor ::= ValorConcreto

ValorConcreto ::= ValorInteiro 
		| ValorBooleano 
		| ValorString
      --->>	| ValorFuncao

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

DeclaracaoFuncional ::= DecVariavel
			| DecFuncao
			| DecComposta

DecVariavel ::= "var" Id "=" Expressao

DecFuncao ::= "fun" ListId "=" Expressao

DecComposta ::= DeclaracaoFuncional "," DeclaracaoFuncional

ListId ::= Id  
	|  Id ListId

Aplicacao:= Id"(" ListExp ")"

ListParametro ::= Parametro  <<---
		|  Parametro ListParametro

ListExp ::= Expressao  
	|  Expressao, ListExp

IfThenElse ::= "if" Expressao "then" Expressao "else" Expressao
```

## Extensões realizadas em LF1
1. Implementação da classe ```ParametroFuncao```

```java
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
```java
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

		for (*ParametroFuncao* arg : args) {
			ambiente.map(*arg.getId()*, new TipoPolimorfico());
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
```java
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
	 * METODO ALTERADO PARA ACEITAR UMA QUANTIDADE DE ARGUMENTOS NA
	 * CHAMADA DA FUNCAO MENOR OU IGUAL AO NUMERO DE PARAMETROS DA SUA DECLARACAO
	 * 
	 * @param parametrosFormais
	 * @return
	 */
	private boolean checkArgumentListSize(List<? extends Expressao> parametrosFormais) {
		// ALTERADO PARA PERMITIR QUE A FUNÇÃO RECEBA MENOS ARGUMENTOS DO QUE CONSTA NA DEFINIÇÃO
		return getDominio().size() >= parametrosFormais.size();
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
```java
public class Aplicacao implements Expressao {

	private Id func;
	private List<? extends Expressao> argsExpressao;

	public Aplicacao(Id f, Expressao... expressoes) {
		this(f, Arrays.asList(expressoes));
	}

	public Aplicacao(Id f, List<? extends Expressao> expressoes) {
		func = f;
		argsExpressao = expressoes;
	}

	public Valor avaliar(AmbienteExecucao ambiente) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		AmbienteExecucaoFuncional ambienteFuncional = (AmbienteExecucaoFuncional) ambiente;

		DefFuncao funcao;
		try {
			funcao = ambienteFuncional.getFuncao(func);
		} catch (IdentificadorNaoDeclaradoException e) {
			throw new VariavelJaDeclaradaException(func);
		}

		Map<Id, Valor> mapIdValor = resolveParametersBindings(ambiente, funcao);

		ambiente.incrementa();

		includeValueBindings(ambiente, mapIdValor);

		Valor vresult = funcao.getExp().avaliar(ambiente);
		ambiente.restaura();
		return vresult;
	}

	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		boolean result;
		Tipo aux = ambiente.get(func);

		if (aux instanceof TipoFuncao) {
			TipoFuncao tipoFuncao = (TipoFuncao) aux;

			result = tipoFuncao.checaTipo(ambiente, argsExpressao);
		} else {
			// A funcao func nao foi declarada.

			// TODO: lancar uma excecao ou separar variaveis de funcoes no
			// contexto de compilacao.

			result = false;
		}
		return result;
	}

	public List<? extends Expressao> getArgsExpressao() {
		return argsExpressao;
	}

	public Id getFunc() {
		return func;
	}

	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		TipoFuncao tipoFuncao = (TipoFuncao) ambiente.get(func);
		return tipoFuncao.getTipo(ambiente, argsExpressao);
	}

	private void includeValueBindings(AmbienteExecucao ambiente, Map<Id, Valor> mapIdValor)
			throws VariavelJaDeclaradaException {
		for (Map.Entry<Id, Valor> mapeamento : mapIdValor.entrySet()) {
			Id id = mapeamento.getKey();
			Valor valor = mapeamento.getValue();
			ambiente.map(id, valor);
		}
	}

	/**
	 * METODO ALTERADO PARA COLOCAR NO MAP OS VALORES DEFAULT DOS PARAMETROS QDO OS
	 * ARGUMENTOS NAO SAO PASSADOS
	 * 
	 * @param ambiente
	 * @param funcao
	 * @return
	 * @throws VariavelNaoDeclaradaException
	 * @throws VariavelJaDeclaradaException
	 */
	private Map<Id, Valor> resolveParametersBindings(AmbienteExecucao ambiente, DefFuncao funcao)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		List<ParametroFuncao> parametrosId = funcao.getListaParametros();
		List<? extends Expressao> expressoesValorReal = argsExpressao;

		Map<Id, Valor> mapIdValor = new HashMap<Id, Valor>();

		if (expressoesValorReal == null) {
			for (ParametroFuncao param : parametrosId) {
				mapIdValor.put(param.getId(), param.getValorDefault().avaliar(ambiente));
			}
		} else {
			Iterator<? extends Expressao> iterExpressoesValor = expressoesValorReal.iterator();
			for (ParametroFuncao param : parametrosId) {
				if (iterExpressoesValor.hasNext()) {
					mapIdValor.put(param.getId(), iterExpressoesValor.next().avaliar(ambiente));
				} else {
					mapIdValor.put(param.getId(), param.getValorDefault().avaliar(ambiente));
				}
			}
		}

		return mapIdValor;
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", func, ToStringProvider.listToString(argsExpressao, ","));
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		// TODO Auto-generated method stub
		return null;
	}

	public Aplicacao clone() {
		Aplicacao retorno;
		ArrayList<Expressao> novaLista = new ArrayList<Expressao>(this.argsExpressao.size());

		Iterator<? extends Expressao> iterator = argsExpressao.iterator();
		while (iterator.hasNext()) {
			Expressao exp = iterator.next();
			novaLista.add(exp.clone());
		}

		retorno = new Aplicacao(this.func.clone(), novaLista);

		return retorno;
	}
}
```
```java
public class DecFuncao implements DeclaracaoFuncional {

	private Id id;

	private DefFuncao funcao;

	public DecFuncao(Id idFun, List<ParametroFuncao> args, Expressao exp) {
		this.id = idFun;
		this.funcao = new DefFuncao(args, exp);
	}

	public Id getId() {
		return id;
	}

	public List<ParametroFuncao> getListaParametros() {
		return funcao.getListaParametros();
	}

	public Expressao getExpressao() {
		return funcao.getExp();
	}

	public int getAridade() {
		return funcao.getAridade();
	}

	public DefFuncao getFuncao() {
		return funcao;
	}

	@Override
	public String toString() {
		return String.format("fun %s (%s) = %s", id, ToStringProvider.listToString(funcao.getListaParametros(), ","),
				funcao.getExp());
	}

	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		for (int i = 0; i < getAridade(); i++) {
			params.add(new TipoPolimorfico());
		}
		Tipo tipo = new TipoFuncao(params, new TipoPolimorfico());
		// Mapeia a propria funcao no ambiente para permitir recursao.
		ambiente.map(id, tipo);

		boolean result = funcao.checaTipo(ambiente);
		ambiente.restaura();
		return result;
	}

	public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		amb.incrementa();

		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		for (int i = 0; i < getAridade(); i++) {
			params.add(new TipoPolimorfico());
		}
		Tipo tipo = new TipoFuncao(params, new TipoPolimorfico());
		amb.map(id, tipo);

		Tipo result = funcao.getTipo(amb);
		amb.restaura();
		return result;
	}

	public DecFuncao clone() {
		DefFuncao aux = this.funcao.clone();
		return new DecFuncao(this.id.clone(), aux.getListaParametros(), aux.getExp());
	}

	public void elabora(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException {
		tipos.put(getId(), getTipo(amb));
	}

	public void incluir(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException {
		amb.map(getId(), tipos.get(getId()));

	}

	public void elabora(AmbienteExecucaoFuncional amb, Map<Id, Valor> declaracoes,
			Map<Id, DefFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException {
		declaracoesFuncoes.put(getId(), getFuncao());
	}

	public void incluir(AmbienteExecucaoFuncional amb, Map<Id, Valor> declaracoes,
			Map<Id, DefFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException {
		amb.mapFuncao(getId(), declaracoesFuncoes.get(getId()));
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
