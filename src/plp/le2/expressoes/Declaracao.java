package plp.le2.expressoes;

import java.util.Map;

import plp.le1.expressoes.Valor;
import plp.le1.util.Tipo;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;

public interface Declaracao {
	public void elabora(AmbienteExecucao amb, Map<Id, Valor> declaracoes) throws VariavelJaDeclaradaException;

	public void elabora(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException;

	public void incluir(AmbienteExecucao amb, Map<Id, Valor> declaracoes) throws VariavelJaDeclaradaException;

	public void incluir(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException;

	public boolean checaTipo(AmbienteCompilacao amb) throws VariavelJaDeclaradaException, VariavelNaoDeclaradaException;

	public void reduzir(AmbienteExecucao amb);
}
