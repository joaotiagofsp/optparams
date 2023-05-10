package plp.le1.expressoes;

import plp.le1.util.Tipo;
import plp.le1.util.TipoPrimitivo;
import plp.le2.memoria.AmbienteCompilacao;

public class ValorString extends ValorConcreto<String> {

	public ValorString(String valor) {
		super(valor);
	}

	public Tipo getTipo(AmbienteCompilacao amb) {
		return TipoPrimitivo.STRING;
	}

	@Override
	public String toString() {
		return String.format("\"%s\"", super.toString());
	}

	public ValorString clone() {
		return new ValorString(this.valor());
	}
}