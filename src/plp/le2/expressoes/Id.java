package plp.le2.expressoes;

import plp.le1.expressoes.Expressao;
import plp.le1.expressoes.Valor;
import plp.le1.util.Tipo;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;

public class Id implements Expressao {

	private String idName;

	public Id(String strName) {
		idName = strName;
	}

	@Override
	public String toString() {
		return idName;
	}

	public Valor avaliar(AmbienteExecucao ambiente) throws VariavelNaoDeclaradaException {
		return ambiente.get(this);
	}

	public boolean checaTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException {
		boolean result = true;
		amb.get(this); // se estiver no ambiente, entao esta ok.
		return result;
	}

	public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException {
		return amb.get(this);
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (idName == null ? 0 : idName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!obj.getClass().isAssignableFrom(getClass())) {
			return false;
		}
		boolean r;

		Id other = (Id) obj;
		if (this.idName == null) {
			r = other.idName == null;
		} else {
			r = this.idName.equals(other.idName);
		}

		return r;
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {
		try {
			Valor valor = ambiente.get(this);

			if (valor instanceof ValorIrredutivel) {
				return this;
			}

			return valor.clone();

		} catch (VariavelNaoDeclaradaException e) {
			return this;
		}
	}

	public Id clone() {
		return this;
	}
}