package plp.le1.expressoes;

import plp.le2.expressoes.Id;

/**
 * Classe abstrata para representar um parâmtero de função.
 * @author Cleber Moura <ctm@cin.ufpe.br>
 */
public abstract class Parametro {
	
	private Id paramId;
	
	public Id getParamId() {
		return paramId;
	}

	public void setParamId(Id argId) {
		this.paramId = argId;
	}	

	@Override
	public Parametro clone() {
		return this;
	}
}
