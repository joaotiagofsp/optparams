package plp.lf1;

import plp.le1.expressoes.Expressao;
import plp.le1.expressoes.Valor;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;
import plp.le2.memoria.ContextoCompilacao;
import plp.le2.memoria.ContextoExecucao;

/**
 * Modificado para instanciar o AmbienteExecucao
 * @author <a href="mailto:ctm@cin.ufpe.br">Cleber Moura</a>
 */
public class Programa {

	private Expressao exp;

	public Programa(Expressao exp) {
		this.exp = exp;
	}

	public Valor executar()
		throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		AmbienteExecucao ambExec = new ContextoExecucao();
		Valor result = exp.avaliar(ambExec);
		System.out.println(result);
		return result;
	}

	public boolean checaTipo()
		throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException {
		AmbienteCompilacao ambComp = new ContextoCompilacao();
		return exp.checaTipo(ambComp);
	}

	public Expressao getExpressao() {
		return exp;
	}

}