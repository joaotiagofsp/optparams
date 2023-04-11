package plp.optparam.util;

import plp.le1.expressoes.Valor;
import plp.le2.expressoes.Id;

/**
 * NOVA CLASSE CRIADA PARA ENCAPSULAR OS PARAMETROS DE UMA FUNCAO E SEUS VALORES DEFAULT
 * 
 * @author joaotiagofsp
 *
 */
public class ParametroFuncao {
	private Id id;
	private Valor valorDefault;

	public ParametroFuncao(Id id, Valor valorDefault) {
		this.id = id;
		this.valorDefault = valorDefault;
	}

	@Override
	public String toString() {
		return "ParametroFuncao [id=" + id + ", valorDefault=" + valorDefault + "]";
	}

	public Id getId() {
		return id;
	}

	public void setId(Id id) {
		this.id = id;
	}

	public Valor getValorDefault() {
		return valorDefault;
	}

	public void setValorDefault(Valor valorDefault) {
		this.valorDefault = valorDefault;
	}
	
	public ParametroFuncao clone() {
		return this;
	}

}
