package plp.lf1;

import plp.le1.expressoes.Expressao;
import plp.le1.expressoes.Valor;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.ContextoCompilacao;
import plp.lf1.memoria.AmbienteExecucaoFuncional;
import plp.lf1.memoria.ContextoExecucaoFuncional;

public class Programa {

	private Expressao exp;

	public Valor executar() throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		AmbienteExecucaoFuncional ambExec = new ContextoExecucaoFuncional();
		Valor result = exp.avaliar(ambExec);
		System.out.println(result);
		return result;
	}

	public boolean checaTipo() {
		AmbienteCompilacao ambComp = new ContextoCompilacao();
		return exp.checaTipo(ambComp);
	}

	public Programa(Expressao exp) {
		this.exp = exp;
	}

	public Expressao getExpressao() {
		return exp;
	}

}