package plp.lf1.memoria;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import plp.le2.expressoes.Id;
import plp.le2.memoria.Ambiente;
import plp.le2.memoria.Contexto;
import plp.optparam.util.DefFuncao;

public class ContextoFuncional extends Contexto<DefFuncao> implements Ambiente<DefFuncao> {

	public ContextoFuncional clone() {
		ContextoFuncional retorno = new ContextoFuncional();
		
		Stack<HashMap<Id, DefFuncao>> novaPilha = new Stack<HashMap<Id, DefFuncao>>();
		
		HashMap<Id, DefFuncao> novoMap = new HashMap<Id, DefFuncao>();
		novaPilha.add(novoMap);

		for (HashMap<Id, DefFuncao> map : this.pilha){
			for(Entry<Id, DefFuncao> entry : map.entrySet()){
				novoMap.put(entry.getKey(), entry.getValue());
			}
		}
		
		retorno.setPilha(novaPilha);
		
		return retorno;
	}
}
