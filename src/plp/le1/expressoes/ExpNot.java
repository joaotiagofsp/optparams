package plp.le1.expressoes;

import plp.le1.util.Tipo;
import plp.le1.util.TipoPrimitivo;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;

public class ExpNot extends ExpUnaria {

	public ExpNot(Expressao exp) {
		super(exp, "~");
	}

	public Valor avaliar(AmbienteExecucao amb) {
		return new ValorBooleano(!((ValorBooleano) getExp().avaliar(amb)).valor());
	}

	protected boolean checaTipoElementoTerminal(AmbienteCompilacao amb) {
		return (getExp().getTipo(amb).eBooleano());
	}

	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.BOOLEANO;
	}

	@Override
	public ExpUnaria clone() {
		return new ExpNot(exp.clone());
	}
}