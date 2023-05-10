package plp.le1.expressoes;

import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;

public abstract class ExpBinaria implements Expressao {

	protected Expressao esq;

	protected Expressao dir;

	private String operador;

	public ExpBinaria(Expressao esq, Expressao dir, String operador) {
		this.esq = esq;
		this.dir = dir;
		this.operador = operador;
	}

	public Expressao getEsq() {
		return esq;
	}

	public Expressao getDir() {
		return dir;
	}

	public String getOperador() {
		return operador;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s", esq, operador, dir);
	}

	public boolean checaTipo(AmbienteCompilacao amb) {
		boolean result;
		if (!getEsq().checaTipo(amb) || !getDir().checaTipo(amb)) {
			result = false;
		} else {
			result = this.checaTipoElementoTerminal(amb);
		}
		return result;
	}

	protected abstract boolean checaTipoElementoTerminal(AmbienteCompilacao amb);

	public Expressao reduzir(AmbienteExecucao ambiente) {
		this.esq = this.esq.reduzir(ambiente);
		this.dir = this.dir.reduzir(ambiente);

		return this;
	}

	public abstract ExpBinaria clone();

}
