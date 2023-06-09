package plp.le2.memoria;

import java.util.HashMap;
import java.util.Stack;

import plp.le2.excecoes.IdentificadorJaDeclaradoException;
import plp.le2.excecoes.IdentificadorNaoDeclaradoException;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.expressoes.Id;

public class Contexto<T> {

	protected Stack<HashMap<Id, T>> pilha;

	public Contexto() {
		pilha = new Stack<HashMap<Id, T>>();
	}

	public void incrementa() {
		pilha.push(new HashMap<Id, T>());
	}

	public void restaura() {
		pilha.pop();
	}

	public void map(Id idArg, T valorId) throws VariavelJaDeclaradaException {
		try {
			HashMap<Id, T> aux = pilha.peek();
			if (aux.put(idArg, valorId) != null)
				throw new IdentificadorJaDeclaradoException();
		} catch (IdentificadorJaDeclaradoException e) {
			throw new VariavelJaDeclaradaException(idArg);
		}
	}

	public T get(Id idArg) throws VariavelNaoDeclaradaException {
		try {
			T result = null;
			Stack<HashMap<Id, T>> auxStack = new Stack<HashMap<Id, T>>();
			while (result == null && !pilha.empty()) {
				HashMap<Id, T> aux = pilha.pop();
				auxStack.push(aux);
				result = aux.get(idArg);
			}
			while (!auxStack.empty()) {
				pilha.push(auxStack.pop());
			}
			if (result == null)
				throw new IdentificadorNaoDeclaradoException();

			return result;
		} catch (IdentificadorNaoDeclaradoException e) {
			throw new VariavelNaoDeclaradaException(idArg);
		}
	}

	protected Stack<HashMap<Id, T>> getPilha() {
		return pilha;
	}

	protected void setPilha(Stack<HashMap<Id, T>> pilha) {
		this.pilha = pilha;
	}

}