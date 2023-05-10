package plp.lf1.expressoes;

import java.util.HashMap;
import java.util.Map;

import plp.le1.expressoes.Expressao;
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
public class ExpDeclaracao implements Expressao {

	DeclaracaoFuncional declaracao;
	Expressao expressao;

	public ExpDeclaracao(DeclaracaoFuncional declaracao, Expressao expressao) {
		this.declaracao = declaracao;
		this.expressao = expressao;
	}

	public Expressao getExpressao() {
		return expressao;
	}

	public Valor avaliar(AmbienteExecucao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		amb.incrementa();
		Map<Id, Valor> declaracoes = new HashMap<Id, Valor>();
		Map<Id, ValorFuncao> declaracoesFuncoes = new HashMap<Id, ValorFuncao>();
		declaracao.elabora(amb, declaracoes, declaracoesFuncoes);
		declaracao.incluir(amb, declaracoes, declaracoesFuncoes);
		Valor vresult = expressao.avaliar(amb);
		amb.restaura();
		return vresult;
	}

	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		boolean result = false;
		try {
			result = declaracao.checaTipo(ambiente);
			if (result) {
				Map<Id, Tipo> tipos = new HashMap<Id, Tipo>();
				declaracao.elabora(ambiente, tipos);
				declaracao.incluir(ambiente, tipos, true);
				result = expressao.checaTipo(ambiente);
			}
		} finally {
			ambiente.restaura();
		}
		return result;
	}

	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();
		Map<Id, Tipo> tipos = new HashMap<Id, Tipo>();
		declaracao.elabora(ambiente, tipos);
		declaracao.incluir(ambiente, tipos, true);
		Tipo vresult = expressao.getTipo(ambiente);
		ambiente.restaura();
		return vresult;
	}

	public Expressao reduzir(AmbienteExecucao ambiente) {

		return null;
	}

	public ExpDeclaracao clone() {
		return new ExpDeclaracao(declaracao.clone(), this.expressao.clone());
	}

}