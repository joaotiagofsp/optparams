package plp.lf1.expressoes;

import java.util.Map;

import plp.le1.expressoes.Expressao;
import plp.le1.expressoes.Valor;
import plp.le1.util.Tipo;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.expressoes.Id;
import plp.le2.memoria.AmbienteCompilacao;
import plp.lf1.memoria.AmbienteExecucaoFuncional;
import plp.optparam.util.DefFuncao;

public class DecVariavel implements DeclaracaoFuncional {
	private Id id;
	private Expressao expressao;

	public DecVariavel(Id idArg, Expressao expressaoArg) {
		id = idArg;
		expressao = expressaoArg;
	}

	@Override
	public String toString() {
		return String.format("var %s = %s", id, expressao);
	}

	public Expressao getExpressao() {
		return expressao;
	}

	public Id getId() {
		return id;
	}

	public Tipo getTipo(AmbienteCompilacao amb) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return expressao.getTipo(amb);
	}

	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return expressao.checaTipo(ambiente);
	}

	public DecVariavel clone() {
		return new DecVariavel(this.id.clone(), this.expressao.clone());
	}

	public void elabora(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException {
		tipos.put(getId(), getTipo(amb));
	}

	public void incluir(AmbienteCompilacao amb, Map<Id, Tipo> tipos, boolean incluirCuringa) throws VariavelJaDeclaradaException {
		amb.map(getId(), tipos.get(getId()));
	}

	public void elabora(AmbienteExecucaoFuncional amb, Map<Id, Valor> declaracoes,
			Map<Id, DefFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException {
		declaracoes.put(getId(), getExpressao().avaliar(amb));
	}

	public void incluir(AmbienteExecucaoFuncional amb, Map<Id, Valor> declaracoes,
			Map<Id, DefFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException {
		amb.map(getId(), declaracoes.get(getId()));
	}

}