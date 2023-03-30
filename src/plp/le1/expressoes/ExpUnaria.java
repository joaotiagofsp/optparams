package plp.le1.expressoes;

import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;

public abstract class ExpUnaria implements Expressao {

	protected Expressao exp;

	private String operador;

	public ExpUnaria(Expressao exp, String operador) {
		this.exp = exp;
		this.operador = operador;
	}

	public Expressao getExp() {
		return exp;
	}

	public String getOperador() {
		return operador;
	}

	@Override
	public String toString() {
		return String.format(" %s %s", operador, exp);
	}

	public boolean checaTipo(AmbienteCompilacao amb) {
		return getExp().checaTipo(amb) && this.checaTipoElementoTerminal(amb);
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		this.exp = this.exp.reduzir(ambiente);

		return this;
	}

	public abstract ExpUnaria clone();

	protected abstract boolean checaTipoElementoTerminal(AmbienteCompilacao amb);
}