package plp.optparam.expressoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import plp.le1.expressoes.Expressao;
import plp.le1.expressoes.Valor;
import plp.le1.util.Tipo;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.expressoes.Id;
import plp.le2.memoria.AmbienteCompilacao;
import plp.lf1.expressoes.DeclaracaoFuncional;
import plp.lf1.memoria.AmbienteExecucaoFuncional;
import plp.lf1.util.TipoPolimorfico;
import plp.lf1.util.ToStringProvider;
import plp.optparam.util.DefFuncao;
import plp.optparam.util.ParametroFuncao;
import plp.optparam.util.TipoFuncao;

/**
 * IMPLEMENTACAO DA CLASSE MODIFICADA PARA RECONHECER VALORES DEFAULT DOS
 * ARGUMENTOS DE UMA FUNCAO
 * 
 * @author joaotiagofsp
 *
 */
public class DecFuncao implements DeclaracaoFuncional {

	private Id id;

	private DefFuncao funcao;

	public DecFuncao(Id idFun, List<ParametroFuncao> args, Expressao exp) {
		this.id = idFun;
		this.funcao = new DefFuncao(args, exp);
	}

	public Id getId() {
		return id;
	}

	public List<ParametroFuncao> getListaParametros() {
		return funcao.getListaParametros();
	}

	public Expressao getExpressao() {
		return funcao.getExp();
	}

	public int getAridade() {
		return funcao.getAridade();
	}

	public DefFuncao getFuncao() {
		return funcao;
	}

	@Override
	public String toString() {
		return String.format("fun %s (%s) = %s", id, ToStringProvider.listToString(funcao.getListaParametros(), ","),
				funcao.getExp());
	}

	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		for (int i = 0; i < getAridade(); i++) {
			params.add(new TipoPolimorfico());
		}
		Tipo tipo = new TipoFuncao(params, new TipoPolimorfico());
		// Mapeia a propria funcao no ambiente para permitir recursao.
		ambiente.map(id, tipo);

		boolean result = funcao.checaTipo(ambiente);
		ambiente.restaura();
		return result;
	}

	public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		amb.incrementa();

		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		for (int i = 0; i < getAridade(); i++) {
			params.add(new TipoPolimorfico());
		}
		Tipo tipo = new TipoFuncao(params, new TipoPolimorfico());
		amb.map(id, tipo);

		Tipo result = funcao.getTipo(amb);
		amb.restaura();
		return result;
	}

	public DecFuncao clone() {
		DefFuncao aux = this.funcao.clone();
		return new DecFuncao(this.id.clone(), aux.getListaParametros(), aux.getExp());
	}

	public void elabora(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException {
		tipos.put(getId(), getTipo(amb));
	}

	public void incluir(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException {
		amb.map(getId(), tipos.get(getId()));

	}

	public void elabora(AmbienteExecucaoFuncional amb, Map<Id, Valor> declaracoes,
			Map<Id, DefFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException {
		declaracoesFuncoes.put(getId(), getFuncao());
	}

	public void incluir(AmbienteExecucaoFuncional amb, Map<Id, Valor> declaracoes,
			Map<Id, DefFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException {
		amb.mapFuncao(getId(), declaracoesFuncoes.get(getId()));
	}

}