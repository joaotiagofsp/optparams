package plp.le1.expressoes;

import plp.le1.util.Tipo;
import plp.le1.util.TipoPrimitivo;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;

public class ExpAnd extends ExpBinaria {

	public ExpAnd(Expressao esq, Expressao dir) {
		super(esq, dir, "and");
	}

	public Valor avaliar(AmbienteExecucao amb) {
		return new ValorBooleano(
				((ValorBooleano) getEsq().avaliar(amb)).valor() && ((ValorBooleano) getDir().avaliar(amb)).valor());
	}

	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb) {
		return (getEsq().getTipo(amb).eBooleano() && getDir().getTipo(amb).eBooleano());
	}

	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.BOOLEANO;
	}

	@Override
	public ExpBinaria clone() {
		return new ExpAnd(esq.clone(), dir.clone());
	}
}