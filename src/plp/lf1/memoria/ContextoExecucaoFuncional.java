package plp.lf1.memoria;

import plp.le1.expressoes.Valor;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.expressoes.Id;
import plp.le2.memoria.ContextoExecucao;
import plp.optparam.util.DefFuncao;

public class ContextoExecucaoFuncional implements AmbienteExecucaoFuncional {

	private ContextoExecucao contextoExecucao;
	private ContextoFuncional contextoFuncional;

	public ContextoExecucaoFuncional() {
		contextoExecucao = new ContextoExecucao();
		contextoFuncional = new ContextoFuncional();
	}

	public void incrementa() {
		contextoExecucao.incrementa();
		contextoFuncional.incrementa();
	}

	public void restaura() {
		contextoExecucao.restaura();
		contextoFuncional.restaura();
	}

	public void map(Id idArg, Valor tipoId) throws VariavelJaDeclaradaException {
		contextoExecucao.map(idArg, tipoId);
	}

	public Valor get(Id idArg) throws VariavelNaoDeclaradaException {
		return contextoExecucao.get(idArg);
	}

	public void mapFuncao(Id idArg, DefFuncao funcao) throws VariavelJaDeclaradaException {
		contextoFuncional.map(idArg, funcao);
	}

	public DefFuncao getFuncao(Id idArg) throws VariavelNaoDeclaradaException {
		return contextoFuncional.get(idArg);
	}

	public ContextoExecucaoFuncional clone() {
		return this;
	}
}
