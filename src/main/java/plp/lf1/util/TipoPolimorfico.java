package plp.lf1.util;

import plp.le1.util.Tipo;
import plp.le1.util.TipoPrimitivo;

public class TipoPolimorfico implements Tipo {

	public static final Tipo CURINGA = new TipoPolimorfico();

	private Tipo tipoInferido = null;

	private Tipo tipoInstanciado;

	public TipoPolimorfico() {
	}

	public String getNome() {
		String nome = "?";
		if (ehCoringa()) {
			if (jaInstanciou()) {
				nome = tipoInstanciado.getNome();
			}
		} else {
			if (jaInferiu()) {
				nome = tipoInferido.getNome();
			}
		}
		return nome;
	}

	public Tipo getTipoInstanciado() {
		return this.tipoInstanciado;
	}

	public boolean eInteiro() {
		return this.eIgual(TipoPrimitivo.INTEIRO);
	}

	public boolean eBooleano() {
		return this.eIgual(TipoPrimitivo.BOOLEANO);
	}

	public boolean eString() {
		return this.eIgual(TipoPrimitivo.STRING);
	}

	public boolean eIgual(Tipo tipo) {
		boolean ret = false;

		if (tipo == this)
			return true;

		if (jaInferiu()) {
			if (ehCoringa()) {
				if (jaInstanciou())
					return this.tipoInstanciado.eIgual(tipo);
				else {
					this.tipoInstanciado = tipo;
					return true;
				}
			} else
				return this.tipoInferido.eIgual(tipo);

		}

		if (tipo instanceof TipoPolimorfico) {
			if (((TipoPolimorfico) tipo).tipoInferido != null)
				return ((TipoPolimorfico) tipo).eIgual(this);
			else {
				// Ainda n�o inferiu nem this nem obj.

				// "Inferiu" o tipo de obj!
				((TipoPolimorfico) tipo).tipoInferido = this;
				((TipoPolimorfico) tipo).tipoInstanciado = this;
				// OBS: this ainda continua n�o inferido.

				ret = true;
			}
		} else {
			// Inferiu o tipo!
			this.tipoInferido = tipo;
			this.tipoInstanciado = tipo;
			ret = true;
		}

		return ret;
	}

	private boolean jaInstanciou() {
		return tipoInstanciado != null;
	}

	private boolean ehCoringa() {
		return tipoInferido == CURINGA;
	}

	private boolean jaInferiu() {
		return tipoInferido != null;
	}

	public boolean eValido() {
		boolean ret = false;
		if (jaInferiu()) {
			if (ehCoringa()) {
				ret = jaInstanciou() && tipoInstanciado.eValido();
			} else {
				ret = tipoInferido.eValido();
			}
		}
		return ret;
	}

	public Tipo inferir() {
		if (eValido())
			return this.tipoInferido;

		if (!(this.tipoInferido instanceof TipoPolimorfico)) {
			this.tipoInferido = CURINGA;
		}
		// Este tipo deve ser o mesmo de outro tipo polimorfico.
		return this;

	}

	public void limpar() {
		this.tipoInstanciado = null;
	}

	public Tipo intersecao(Tipo outroTipo) {
		if (outroTipo.eIgual(this))
			return outroTipo;
		else
			return null;
	}

	@Override
	public String toString() {
		return getNome();
	}

}