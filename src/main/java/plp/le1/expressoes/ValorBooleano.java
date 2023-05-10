package plp.le1.expressoes;

import plp.le1.util.Tipo;
import plp.le1.util.TipoPrimitivo;
import plp.le2.memoria.AmbienteCompilacao;

public class ValorBooleano extends ValorConcreto<Boolean> {

	public ValorBooleano(boolean valor) {
		super(valor);
	}

	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.BOOLEANO;
	}

	public ValorBooleano clone() {
		return new ValorBooleano(this.valor());
	}
}