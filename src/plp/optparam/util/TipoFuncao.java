package plp.optparam.util;

import java.util.Iterator;
import java.util.List;

import plp.le1.expressoes.Expressao;
import plp.le1.util.Tipo;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.memoria.AmbienteCompilacao;
import plp.lf1.util.TipoPolimorfico;
import plp.lf1.util.ToStringProvider;

/**
 * IMPLEMENTACAO DA CLASSE MODIFICADA PARA RECONHECER VALORES DEFAULT DOS
 * ARGUMENTOS DE UMA FUNCAO
 * 
 * @author joaotiagofsp
 *
 */
public class TipoFuncao implements Tipo {

	private List<Tipo> dominio;

	private Tipo imagem;

	public TipoFuncao(List<Tipo> dominio, Tipo imagem) {
		this.dominio = dominio;
		this.imagem = imagem;
	}

	public String getNome() {
		return String.format("(%s) -> %s", ToStringProvider.listToString(dominio, " x"), imagem);
	}

	public List<Tipo> getDominio() {
		return dominio;
	}

	public Tipo getImagem() {
		return imagem;
	}

	public boolean eBooleano() {
		return imagem.eBooleano();
	}

	public boolean eInteiro() {
		return imagem.eInteiro();
	}

	public boolean eString() {
		return imagem.eString();
	}

	public boolean eValido() {
		boolean ret = dominio != null;
		for (Tipo t : this.dominio) {
			ret &= t.eValido();
		}
		ret &= imagem != null && imagem.eValido();
		return ret;
	}

	public boolean eIgual(Tipo tipo) {
		boolean ret = true;
		if (tipo instanceof TipoPolimorfico)
			return tipo.eIgual(this);

		if (tipo instanceof TipoFuncao) {
			TipoFuncao tipoFuncao = (TipoFuncao) tipo;
			if (this.dominio.size() != tipoFuncao.dominio.size())
				return false;
			Iterator<Tipo> it = this.dominio.iterator();
			for (Tipo t : tipoFuncao.dominio) {
				ret &= t.eIgual(it.next());
			}
			return ret && this.imagem.eIgual(tipoFuncao.imagem);
		}

		return ret;
	}

	public Tipo intersecao(Tipo outroTipo) {
		if (outroTipo.eIgual(this))
			return this;
		else
			return null;
	}

	@Override
	public String toString() {
		return getNome();
	}

	private void limparTiposCuringas() {
		for (Tipo tDom : getDominio()) {
			if (tDom instanceof TipoPolimorfico) {
				((TipoPolimorfico) tDom).limpar();
			}

		}
		if (getImagem() instanceof TipoPolimorfico) {
			((TipoPolimorfico) getImagem()).limpar();
		}
	}

	/**
	 * METODO ALTERADO PARA ACEITAR UMA QUANTIDADE DE ARGUMENTOS NA
	 * CHAMADA DA FUNCAO MENOR OU IGUAL AO NUMERO DE PARAMETROS DA SUA DECLARACAO
	 * 
	 * @param parametrosFormais
	 * @return
	 */
	private boolean checkArgumentListSize(List<? extends Expressao> parametrosFormais) {
		// ALTERADO PARA PERMITIR QUE A FUNÇÃO RECEBA MENOS ARGUMENTOS DO QUE CONSTA NA DEFINIÇÃO
		return getDominio().size() >= parametrosFormais.size();
	}

	private boolean checkArgumentTypes(AmbienteCompilacao ambiente, List<? extends Expressao> parametrosFormais)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		boolean result = true;

		Iterator<Tipo> it = getDominio().iterator();
		Tipo tipoArg;
		if (parametrosFormais != null) {
			for (Expressao valorReal : parametrosFormais) {
	
				result &= valorReal.checaTipo(ambiente);
	
				tipoArg = valorReal.getTipo(ambiente);
				Tipo tipoDom = it.next();
	
				result &= tipoArg.eIgual(tipoDom);
			}
		}
		return result;
	}

	public boolean checaTipo(AmbienteCompilacao ambiente, List<? extends Expressao> parametrosFormais) {
		boolean result = checkArgumentListSize(parametrosFormais) && checkArgumentTypes(ambiente, parametrosFormais);
		limparTiposCuringas();
		return result;
	}

	public Tipo getTipo(AmbienteCompilacao ambiente, List<? extends Expressao> parametrosFormais) {
		// Infere os parametros
		Iterator<Tipo> it = getDominio().iterator();
		Tipo tipoArg;
		for (Expressao valorReal : parametrosFormais) {
			tipoArg = valorReal.getTipo(ambiente);
			tipoArg.eIgual(it.next());
		}

		// Obtem o resultado.
		Tipo ret = getImagem();
		// Caso seja um tipo polimorfico procura a mais especifica instanciacao.
		while (ret instanceof TipoPolimorfico) {
			if (((TipoPolimorfico) ret).getTipoInstanciado() == null) {
				break;
			}
			ret = ((TipoPolimorfico) ret).getTipoInstanciado();
		}

		limparTiposCuringas();
		return ret;
	}

}