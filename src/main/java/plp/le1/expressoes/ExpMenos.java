package plp.le1.expressoes;

import plp.le1.util.Tipo;
import plp.le1.util.TipoPrimitivo;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;

public class ExpMenos extends ExpUnaria {

	public ExpMenos(Expressao exp) {
		super(exp, "-");
	}

	public Valor avaliar(AmbienteExecucao amb) {
		return new ValorInteiro(-((ValorInteiro) getExp().avaliar(amb)).valor());
	}

	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb) {
		return (getExp().getTipo(amb).eInteiro());
	}

	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.INTEIRO;
	}

	@Override
	public ExpUnaria clone() {
		return new ExpMenos(exp.clone());
	}
}