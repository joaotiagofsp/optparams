package plp.le1.expressoes;

import plp.le2.expressoes.Id;

/**
 * Classe para representar um parâmtero obrigatório da função.
 * @author Cleber Moura <ctm@cin.ufpe.br>
 */
public class ParametroObrigatorio extends Parametro {
	
	public ParametroObrigatorio(Id paramId) {
		setParamId(paramId);
	}
	
	@Override
	public String toString() {
		return getParamId().toString();
	}
}
