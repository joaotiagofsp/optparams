package plp.le1.expressoes;

import plp.le2.expressoes.Id;

/**
 * Classe para representar um parâmtero obrigatório da função.
 * @author <a href="mailto:ctm@cin.ufpe.br">Cleber Moura</a>
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
