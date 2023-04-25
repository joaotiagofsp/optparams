package plp.le2.excecoes;

import plp.le1.expressoes.Parametro;

/**
 * Exceção para tratar a restrição para não permitir parâmetros obrigatórios após os opcionais.
 * @author Cleber Moura <ctm@cin.ufpe.br>
 */
public class ParametroObrigatorioAposOpcionalException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParametroObrigatorioAposOpcionalException(Parametro arg){
		super("Não é permitido especificar o parâmetro obrigatório " + arg + " após a especificação de parâmetros opcionais.");
	}
}
