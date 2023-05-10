package plp.le2.excecoes;

import plp.le2.expressoes.Id;

public class VariavelNaoDeclaradaException extends IdentificadorNaoDeclaradoException {

	private static final long serialVersionUID = 1L;

	public VariavelNaoDeclaradaException(Id id) {
		super("Variavel " + id + " nao declarada.");
	}
}
