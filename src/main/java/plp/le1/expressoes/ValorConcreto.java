package plp.le1.expressoes;

import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;

public abstract class ValorConcreto<T> implements Valor {

	private T valor;

	public String toString() {
		return String.valueOf(valor);
	}

	public ValorConcreto(T valor) {
		this.valor = valor;
	}

	public T valor() {
		return valor;
	}

	public boolean isEquals(ValorConcreto<T> obj) {
		return valor().equals(obj.valor());

	}

	public Valor avaliar(AmbienteExecucao amb) {
		return this;
	}

	public boolean checaTipo(AmbienteCompilacao amb) {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (valor == null ? 0 : valor.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ValorConcreto<T> other = (ValorConcreto<T>) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		return this;
	}

	public abstract ValorConcreto<T> clone();
}