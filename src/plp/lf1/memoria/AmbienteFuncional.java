package plp.lf1.memoria;

import plp.le2.excecoes.IdentificadorJaDeclaradoException;
import plp.le2.excecoes.IdentificadorNaoDeclaradoException;
import plp.le2.expressoes.Id;

public interface AmbienteFuncional<T> {

	public void mapFuncao(Id id, T funcao) throws IdentificadorJaDeclaradoException;

	public T getFuncao(Id id) throws IdentificadorNaoDeclaradoException;

}