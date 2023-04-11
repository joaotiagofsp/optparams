package plp.le1.expressoes;

import plp.le1.util.Tipo;
import plp.le1.util.TipoPrimitivo;
import plp.le2.memoria.AmbienteCompilacao;

public class ValorInteiro extends ValorConcreto<Integer> {

	public ValorInteiro(int valor) {
		super(valor);
	}

	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.INTEIRO;
	}

	public ValorInteiro clone() {
		return new ValorInteiro(this.valor());
	}
}