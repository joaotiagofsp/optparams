package plp.le1.expressoes;

import plp.le2.expressoes.Id;

/**
 * Classe para representar um parâmtero opcional da função.
 * @author <a href="mailto:ctm@cin.ufpe.br">Cleber Moura</a>
 */
public class ParametroOpcional extends Parametro {
	
	private Expressao valorPadrao;

	public ParametroOpcional(Id paramId, Expressao valorPadrao) {
		setParamId(paramId);
		this.valorPadrao = valorPadrao;
	}
	
	public Expressao getValorPadrao() {
		return valorPadrao;
	}

	public void setValorPadrao(Expressao valorPadrao) {
		this.valorPadrao = valorPadrao;
	}
	
	@Override
	public String toString() {
		return getParamId() + "?(" + valorPadrao + ")";
	}
}
