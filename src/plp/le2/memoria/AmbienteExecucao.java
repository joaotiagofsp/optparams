package plp.le2.memoria;

import plp.le1.expressoes.Valor;

public interface AmbienteExecucao extends Ambiente<Valor> {
	public AmbienteExecucao clone();

}
