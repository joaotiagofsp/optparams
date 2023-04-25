package plp.optparam.expressoes;

import static java.util.Arrays.asList;
import static plp.le1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import plp.le1.expressoes.Expressao;
import plp.le1.expressoes.Parametro;
import plp.le1.expressoes.ParametroOpcional;
import plp.le1.expressoes.Valor;
import plp.le1.util.Tipo;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.expressoes.Id;
import plp.le2.expressoes.ValorFuncao;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;
import plp.lf1.util.TipoPolimorfico;
import plp.optparam.util.DefFuncao;
import plp.optparam.util.TipoFuncao;

/**
 * IMPLEMENTACAO DA CLASSE MODIFICADA PARA RECONHECER VALORES DEFAULT DOS
 * ARGUMENTOS DE UMA FUNCAO
 * 
 * @author joaotiagofsp
 *
 */
public class Aplicacao implements Expressao {

	private Expressao func;
	private List<? extends Expressao> argsExpressao;

	public Aplicacao(Expressao f, Expressao... expressoes) {
		this(f, asList(expressoes));
	}

	public Aplicacao(Expressao f, List<? extends Expressao> expressoes) {
		func = f;
		argsExpressao = expressoes;
	}

	public Valor avaliar(AmbienteExecucao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		ValorFuncao funcao = (ValorFuncao) func.avaliar(ambiente);

		Map<Id, Valor> mapIdValor = resolveParametersBindings(ambiente, funcao);
		ambiente.incrementa();		
		includeValueBindings(ambiente, mapIdValor);

		if(funcao.getId() != null){
			ambiente.map(funcao.getId(), funcao.clone());
		}
		Expressao exp = funcao.getExp().clone();
		exp.reduzir(ambiente);
		
		Valor vresult = exp.avaliar(ambiente);
		
		ambiente.restaura();
		
		return vresult;
	}

	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		boolean result = true;
		
		if (func instanceof ValorFuncao) {
			result = ((ValorFuncao) func).checaTipo(ambiente);
		}
		
		Tipo tipo = getFuncType(ambiente);

		TipoFuncao tipoFuncao = (TipoFuncao) tipo;
		result &= tipoFuncao.checaTipo(ambiente, argsExpressao);

		return result;
	}

	private Tipo getFuncType(AmbienteCompilacao ambiente) {
		Tipo tipoFuncao = null;
		if (func instanceof Id) {
			tipoFuncao = ambiente.get((Id) func);
		} else if (func instanceof ValorFuncao) {
			tipoFuncao = ((ValorFuncao) func).getTipo(ambiente);
		}

		if (tipoFuncao == null || tipoFuncao instanceof TipoPolimorfico) {
			ArrayList<Tipo> params = new ArrayList<Tipo>();
			for (Expressao valorReal : argsExpressao) {
				params.add(valorReal.getTipo(ambiente));
			}
			tipoFuncao = new TipoFuncao(params, new TipoPolimorfico(), (tipoFuncao != null && tipoFuncao instanceof TipoFuncao) ? 
																							  ((TipoFuncao) tipoFuncao).getAridadeRequerido() :															
																							  params.size());
		}
		return tipoFuncao;
	}

	public List<? extends Expressao> getArgsExpressao() {
		return argsExpressao;
	}

	public Expressao getFunc() {
		return func;
	}

	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {

		Tipo tipo = getFuncType(ambiente);

		TipoFuncao tipoFuncao = (TipoFuncao) tipo;

		return tipoFuncao.getTipo(ambiente, argsExpressao);
	}

	private void includeValueBindings(AmbienteExecucao ambiente,
			Map<Id, Valor> mapIdValor) throws VariavelJaDeclaradaException {
		for (Map.Entry<Id, Valor> mapeamento : mapIdValor.entrySet()) {
			Id id = mapeamento.getKey();
			Valor valor = mapeamento.getValue();
			ambiente.map(id, valor);
		}
	}

	/**
	 * METODO ALTERADO PARA COLOCAR NO MAP OS VALORES DEFAULT DOS PARAMETROS OPCIONAIS QDO OS
	 * ARGUMENTOS NAO SAO PASSADOS
	 * 
	 * @param ambiente
	 * @param funcao
	 * @return
	 * @throws VariavelNaoDeclaradaException
	 * @throws VariavelJaDeclaradaException
	 */
	private Map<Id, Valor> resolveParametersBindings(AmbienteExecucao ambiente,
			DefFuncao funcao) throws VariavelNaoDeclaradaException,
			VariavelJaDeclaradaException {		
		List<? extends Expressao> expressoesValorReal = argsExpressao;

		Map<Id, Valor> mapIdValor = new HashMap<Id, Valor>();

		Iterator<? extends Expressao> iterExpressoesValor = expressoesValorReal
				.iterator();
		
		ambiente.incrementa();
		
		for (Parametro param : funcao.getListaParametros()) {
			Id id = param.getParamId();
			Expressao exp;
			
			if (iterExpressoesValor.hasNext()) {
				exp = iterExpressoesValor.next();
			}
			else {
				exp = ((ParametroOpcional) param).getValorPadrao();
			}
			 
			Valor valorReal = exp.avaliar(ambiente);
			mapIdValor.put(id, valorReal);
			ambiente.map(id, valorReal);
		}
		
		ambiente.restaura();
		return mapIdValor;
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", func, listToString(argsExpressao, ","));
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		this.func = this.func.reduzir(ambiente);
		
		ArrayList<Expressao> novosArgs =
			new ArrayList<Expressao>(this.argsExpressao.size());
		
		for(Expressao arg : this.argsExpressao) {
			novosArgs.add(arg.reduzir(ambiente));
		}
		this.argsExpressao = novosArgs;
		
		return this;
	}
	
	public Aplicacao clone() {
		Aplicacao retorno;
		ArrayList<Expressao> novaLista = new ArrayList<Expressao>(this.argsExpressao.size());

		Iterator<? extends Expressao> iterator = argsExpressao.iterator();
		while (iterator.hasNext()){
			Expressao exp = iterator.next();
			novaLista.add(exp.clone());			
		}
		
		retorno = new Aplicacao(this.func.clone(), novaLista);
		
		return retorno;
	}
}