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
 * Modificado para utilizar AmbienteExecucao e ValorFuncao
 * @author Cleber Moura <ctm@cin.ufpe.br>
 */
public class DecComposta implements DeclaracaoFuncional {
	private DeclaracaoFuncional d1;
	private DeclaracaoFuncional d2;
	
	public DecComposta(DeclaracaoFuncional d1, DeclaracaoFuncional d2) {
		this.d1=d1;
		this.d2=d2;
	}

	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		return (d1.checaTipo(ambiente) && d2.checaTipo(ambiente));
	}

	public void elabora(AmbienteExecucao amb, Map<Id,Valor> declaracoes, Map<Id,ValorFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException {
		d1.elabora(amb, declaracoes,declaracoesFuncoes);
		d2.elabora(amb, declaracoes,declaracoesFuncoes);
	}

	public void elabora(AmbienteCompilacao amb, Map<Id,Tipo> tipos) throws VariavelJaDeclaradaException {
		d1.elabora(amb, tipos);
		d2.elabora(amb, tipos);
	}

	public void incluir(AmbienteExecucao amb, Map<Id,Valor> declaracoes, Map<Id,ValorFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException {
		d1.incluir(amb, declaracoes,declaracoesFuncoes);
		d2.incluir(amb, declaracoes,declaracoesFuncoes);
	}

	public void incluir(AmbienteCompilacao amb, Map<Id,Tipo> tipos, boolean incluirCuringa) throws VariavelJaDeclaradaException {
		d1.incluir(amb, tipos, incluirCuringa);
		d2.incluir(amb, tipos, incluirCuringa);
	}

	public DeclaracaoFuncional clone() {
		return new DecComposta(d1.clone(),d2.clone());
	}

	@Override
	public void reduzir(AmbienteExecucao amb) {
		d1.reduzir(amb);
		d2.reduzir(amb);
	}

}