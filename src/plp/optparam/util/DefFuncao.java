package plp.optparam.util;

import java.util.ArrayList;
import java.util.List;

import plp.le1.expressoes.Expressao;
import plp.le1.util.Tipo;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.memoria.AmbienteCompilacao;
import plp.lf1.util.TipoPolimorfico;

/**
 * IMPLEMENTACAO DA CLASSE MODIFICADA PARA RECONHECER VALORES DEFAULT DOS
 * ARGUMENTOS DE UMA FUNCAO
 * 
 * @author joaotiagofsp
 *
 */
public class DefFuncao {
	
	// TIPO DA LISTA ATUALIZADO
	protected List<ParametroFuncao> args;

	protected Expressao exp;

	public DefFuncao(List<ParametroFuncao> args, Expressao exp) {
		this.args = args;
		this.exp = exp;
	}

	public List<ParametroFuncao> getListaParametros() {
		return args;
	}

	public Expressao getExp() {
		return exp;
	}

	public int getAridade() {
		return args.size();
	}

	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		for (ParametroFuncao arg : args) {
			ambiente.map(arg.getId(), new TipoPolimorfico());
		}

		boolean result = exp.checaTipo(ambiente);

		ambiente.restaura();

		return result;
	}

	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		for (ParametroFuncao arg : args) {
			ambiente.map(arg.getId(), new TipoPolimorfico());
		}
		exp.checaTipo(ambiente);

		// Compoe o tipo desta funcao do resultado para o primeiro parametro.
		Tipo result = exp.getTipo(ambiente);

		// Obtem o tipo inferido de cada parametro.
		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		Tipo argTipo;
		for (int i = 0; i < getAridade(); i++) {
			argTipo = ((TipoPolimorfico) ambiente.get(args.get(i).getId())).inferir();
			params.add(argTipo);
		}
		result = new TipoFuncao(params, result);

		ambiente.restaura();

		return result;
	}

	public DefFuncao clone() {
		List<ParametroFuncao> novaLista = new ArrayList<ParametroFuncao>(this.args.size());

		for (ParametroFuncao arg : this.args) {
			novaLista.add(arg.clone());
		}

		return new DefFuncao(novaLista, this.exp.clone());
	}
}
