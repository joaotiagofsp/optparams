package plp.le1.expressoes;

import plp.le1.util.Tipo;
import plp.le1.util.TipoPrimitivo;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;

public class ExpLength extends ExpUnaria {

	public ExpLength(Expressao exp) {
		super(exp, "length");
	}

	public Valor avaliar(AmbienteExecucao amb) {
		return new ValorInteiro(((ValorString) getExp().avaliar(amb)).valor().length());
	}

	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb) {
		return (getExp().getTipo(amb).eString());
	}

	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.INTEIRO;
	}

	@Override
	public ExpUnaria clone() {
		return new ExpLength(exp.clone());
	}
}