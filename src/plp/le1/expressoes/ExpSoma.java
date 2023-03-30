package plp.le1.expressoes;

import plp.le1.util.Tipo;
import plp.le1.util.TipoPrimitivo;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;

public class ExpSoma extends ExpBinaria {

	public ExpSoma(Expressao esq, Expressao dir) {
		super(esq, dir, "+");
	}

	public Valor avaliar(AmbienteExecucao amb) {
		return new ValorInteiro(
				((ValorInteiro) getEsq().avaliar(amb)).valor() + ((ValorInteiro) getDir().avaliar(amb)).valor());
	}

	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb) {
		return (getEsq().getTipo(amb).eInteiro() && getDir().getTipo(amb).eInteiro());
	}

	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.INTEIRO;
	}

	@Override
	public ExpBinaria clone() {
		return new ExpSoma(esq.clone(), dir.clone());
	}
}