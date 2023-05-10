package plp.lf1.expressoes;

import plp.le1.expressoes.Expressao;
import plp.le1.expressoes.Valor;
import plp.le1.expressoes.ValorBooleano;
import plp.le1.util.Tipo;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;

public class IfThenElse implements Expressao {

	private Expressao condicao;
	private Expressao then;
	private Expressao elseExpressao;

	public IfThenElse(Expressao teste, Expressao thenExpressao, Expressao elseExpressao) {
		this.condicao = teste;
		this.then = thenExpressao;
		this.elseExpressao = elseExpressao;
	}

	public Expressao getCondicao() {
		return condicao;
	}

	public Expressao getThen() {
		return then;
	}

	public Expressao getElseExpressao() {
		return elseExpressao;
	}

	public Valor avaliar(AmbienteExecucao ambiente) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		if (((ValorBooleano) condicao.avaliar(ambiente)).valor())
			return then.avaliar(ambiente);
		else
			return elseExpressao.avaliar(ambiente);
	}

	public boolean checaTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		boolean ret = condicao.checaTipo(amb);
		ret &= then.checaTipo(amb);
		ret &= elseExpressao.checaTipo(amb);

		Tipo condicaoTipo = condicao.getTipo(amb);
		Tipo thenTipo = then.getTipo(amb);
		Tipo elseTipo = elseExpressao.getTipo(amb);

		return ret && condicaoTipo.eBooleano() && thenTipo.eIgual(elseTipo);
	}

	public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return then.getTipo(amb).intersecao(elseExpressao.getTipo(amb));
	}

	@Override
	public String toString() {
		return String.format("if (%s) then (%s) else (%s)", condicao, then, elseExpressao);
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		this.condicao = this.condicao.reduzir(ambiente);
		this.then = this.then.reduzir(ambiente);
		this.elseExpressao = this.elseExpressao.reduzir(ambiente);

		return this;
	}

	public IfThenElse clone() {
		return new IfThenElse(this.condicao.clone(), this.then.clone(), this.elseExpressao.clone());
	}
}
