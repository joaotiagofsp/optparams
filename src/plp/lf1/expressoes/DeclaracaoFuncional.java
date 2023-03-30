package plp.lf1.expressoes;

import java.util.Map;

import plp.le1.expressoes.Valor;
import plp.le1.util.Tipo;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.expressoes.Id;
import plp.le2.memoria.AmbienteCompilacao;
import plp.lf1.memoria.AmbienteExecucaoFuncional;
import plp.optparam.util.DefFuncao;

public interface DeclaracaoFuncional {
	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException;

	public void elabora(AmbienteExecucaoFuncional amb, Map<Id, Valor> declaracoes,
			Map<Id, DefFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException;

	public void elabora(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException;

	public void incluir(AmbienteExecucaoFuncional amb, Map<Id, Valor> declaracoes,
			Map<Id, DefFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException;

	public void incluir(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException;

	public DeclaracaoFuncional clone();
}
