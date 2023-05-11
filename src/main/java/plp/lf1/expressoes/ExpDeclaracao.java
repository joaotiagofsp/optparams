package plp.lf1.expressoes;

import java.util.HashMap;
import java.util.Map;

import plp.le1.expressoes.Expressao;
import plp.le1.expressoes.Valor;
import plp.le1.util.Tipo;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.expressoes.Id;
import plp.le2.memoria.AmbienteCompilacao;
import plp.le2.memoria.AmbienteExecucao;
import plp.lf1.memoria.AmbienteExecucaoFuncional;
import plp.optparam.util.DefFuncao;

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

	public Valor avaliar(AmbienteExecucao ambienteFuncional)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		AmbienteExecucaoFuncional amb = (AmbienteExecucaoFuncional) ambienteFuncional;
		amb.incrementa();
		Map<Id, Valor> declaracoes = new HashMap<Id, Valor>();
		Map<Id, DefFuncao> declaracoesFuncoes = new HashMap<Id, DefFuncao>();
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