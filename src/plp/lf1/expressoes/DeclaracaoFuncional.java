package plp.lf1.expressoes;

import java.util.Map;

import plp.le1.expressoes.Valor;
import plp.le1.util.Tipo;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.expressoes.Id;
import plp.le2.expressoes.ValorFuncao;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;

/**
 * Modificado para utilizar: AmbienteExecucao, ValorFuncao.
 * @author Cleber Moura <ctm@cin.ufpe.br>
 */
public interface DeclaracaoFuncional {
	public boolean checaTipo(AmbienteCompilacao ambiente) throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException;
	public void elabora(AmbienteExecucao amb, Map<Id,Valor> declaracoes, Map<Id,ValorFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException;
	public void elabora(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException;
	public void incluir(AmbienteExecucao amb, Map<Id,Valor> declaracoes, Map<Id,ValorFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException;
	public void incluir(AmbienteCompilacao amb, Map<Id, Tipo> tipos, boolean incluirCuringa) throws VariavelJaDeclaradaException;
	public void reduzir(AmbienteExecucao amb);

	public DeclaracaoFuncional clone();
}
