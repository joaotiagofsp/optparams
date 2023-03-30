package plp.le1.expressoes;

import plp.le1.util.Tipo;
import plp.le1.util.TipoPrimitivo;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;

public class ExpEquals extends ExpBinaria {

	public ExpEquals(Expressao esq, Expressao dir) {
		super(esq, dir, "==");
	}

	public Valor avaliar(AmbienteExecucao amb) {
		return (new ValorBooleano(
				((ValorConcreto) getEsq().avaliar(amb)).isEquals((ValorConcreto) getDir().avaliar(amb))));
	}

	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb) {
		return (getEsq().getTipo(amb).eIgual(getDir().getTipo(amb)));
	}

	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.BOOLEANO;
	}

	@Override
	public ExpBinaria clone() {
		return new ExpEquals(esq.clone(), dir.clone());
	}
}
