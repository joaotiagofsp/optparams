package plp.le2.memoria;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import plp.le1.expressoes.Valor;
import plp.le2.expressoes.Id;

public class ContextoExecucao extends Contexto<Valor> implements AmbienteExecucao {

	public ContextoExecucao clone() {
		ContextoExecucao retorno = new ContextoExecucao();

		Stack<HashMap<Id, Valor>> novaPilha = new Stack<HashMap<Id, Valor>>();

		HashMap<Id, Valor> novoMap = new HashMap<Id, Valor>();
		novaPilha.add(novoMap);

		for (HashMap<Id, Valor> map : this.pilha) {
			for (Entry<Id, Valor> entry : map.entrySet()) {
				novoMap.put(entry.getKey(), entry.getValue());
			}
		}

		retorno.setPilha(novaPilha);

		return retorno;
	}
}