package plp.le1.expressoes;

import plp.le1.util.Tipo;
import plp.le1.util.TipoPrimitivo;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;

public class ExpConcat extends ExpBinaria {

	public ExpConcat(Expressao esq, Expressao dir) {
		super(esq, dir, "++");
	}

	public Valor avaliar(AmbienteExecucao amb) {
		return new ValorString(
				((ValorString) getEsq().avaliar(amb)).valor() + ((ValorString) getDir().avaliar(amb)).valor());
	}

	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb) {
		return (getEsq().getTipo(amb).eString() && getDir().getTipo(amb).eString());
	}

	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.STRING;
	}

	@Override
	public ExpBinaria clone() {
		return new ExpConcat(esq.clone(), dir.clone());
	}

}