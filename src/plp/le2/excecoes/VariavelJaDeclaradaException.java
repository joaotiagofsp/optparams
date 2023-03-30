package plp.le2.excecoes;

import plp.le2.expressoes.Id;

public class VariavelJaDeclaradaException extends IdentificadorJaDeclaradoException {

	private static final long serialVersionUID = 1L;

	public VariavelJaDeclaradaException(Id id) {
		super("Variavel " + id + " ja declarada.");
	}
}