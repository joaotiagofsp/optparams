package plp.optparam.expressoes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import plp.le1.expressoes.Expressao;
import plp.le1.expressoes.Valor;
import plp.le1.util.Tipo;
import plp.le2.excecoes.IdentificadorNaoDeclaradoException;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.expressoes.Id;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;
import plp.lf1.memoria.AmbienteExecucaoFuncional;
import plp.lf1.util.ToStringProvider;
import plp.optparam.util.DefFuncao;
import plp.optparam.util.ParametroFuncao;
import plp.optparam.util.TipoFuncao;

/**
 * IMPLEMENTACAO DA CLASSE MODIFICADA PARA RECONHECER VALORES DEFAULT DOS
 * ARGUMENTOS DE UMA FUNCAO
 * 
 * @author joaotiagofsp
 *
 */
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

		Iterator<? extends Expressao> iterExpressoesValor = expressoesValorReal.iterator();
		for (ParametroFuncao param : parametrosId) {
			Expressao exp = null;
			Valor valorReal = null;
			// ESSE IF NAO EXISTIA NA IMPLEMENTACAO DE LF1 - FOI INCLUIDO NA IMPLEMENTACAO DE OPTPARAM
			if (iterExpressoesValor.hasNext()) {
				exp = iterExpressoesValor.next();
				valorReal = exp.avaliar(ambiente);
				mapIdValor.put(param.getId(), valorReal);
			} else {
				exp = param.getValorDefault();
				valorReal = exp.avaliar(ambiente);
				mapIdValor.put(param.getId(), valorReal);
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