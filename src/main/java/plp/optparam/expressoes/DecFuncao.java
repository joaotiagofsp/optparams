package plp.optparam.expressoes;

import static plp.lf1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.List;
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
import plp.lf1.expressoes.DeclaracaoFuncional;
import plp.lf1.util.TipoPolimorfico;
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
	
	private ValorFuncao valorFuncao;

	public DecFuncao(Id idFun, ValorFuncao valorFuncao) {
		this.id = idFun;
		this.valorFuncao = valorFuncao;
	}

	/**
	 * Retorna uma representacao String desta expressao. Util para depuracao.
	 * 
	 * @return uma representacao String desta expressao.
	 */
	@Override
	public String toString() {
		return String.format("fun %s (%s) = %s", id, listToString(valorFuncao
				.getListaParametros(), ","), getExpressao());
	}

	public Id getId() {
		return id;
	}

	public Expressao getExpressao() {
		return valorFuncao.getExp();
	}

	public ValorFuncao getFuncao() {
		return valorFuncao;
	}

	private int getAridade() {
		return valorFuncao.getAridade();
	}
	
	private int getAridadeRequerido() {
		return valorFuncao.getAridadeRequerido();
	}

	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		for (int i = 0; i < getAridade(); i++) {
			params.add(new TipoPolimorfico());
		}
		Tipo tipo = new TipoFuncao(params, new TipoPolimorfico(), getAridadeRequerido());
		// Mapeia a propria funcao no ambiente para permitir recursao.
		ambiente.map(id, tipo);

		boolean result = valorFuncao.checaTipo(ambiente);
		ambiente.restaura();
		return result;
	}

	public Tipo getTipo(AmbienteCompilacao amb)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		amb.incrementa();

		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		for (int i = 0; i < getAridade(); i++) {
			params.add(new TipoPolimorfico());
		}
		Tipo tipo = new TipoFuncao(params, new TipoPolimorfico(), getAridadeRequerido());
		amb.map(id, tipo);

		Tipo result = valorFuncao.getTipo(amb);
		amb.restaura();
		return result;
	}

	public void setValorFuncao(ValorFuncao valorFuncao) {
		this.valorFuncao = valorFuncao;
	}

	public DecFuncao clone() {
		return new DecFuncao(this.id.clone(), this.valorFuncao.clone());
	}

	public void elabora(AmbienteExecucao amb, Map<Id, Valor> declaracoes,
			Map<Id, ValorFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException {
		declaracoesFuncoes.put(getId(), getFuncao());
		
		//passos a mais
		AmbienteExecucao ambienteClone = amb.clone();
		ambienteClone.incrementa();
		ambienteClone.map(getId(), getFuncao());
		getFuncao().setId(getId());
	}

	public void elabora(AmbienteCompilacao amb, Map<Id, Tipo> tipos) throws VariavelJaDeclaradaException {
		tipos.put(getId(), getTipo(amb));
		
	}

	public void incluir(AmbienteExecucao amb, Map<Id, Valor> declaracoes,
			Map<Id, ValorFuncao> declaracoesFuncoes) throws VariavelJaDeclaradaException {
		amb.map(getId(), declaracoesFuncoes.get(getId()));
		
	}

	public void incluir(AmbienteCompilacao amb, Map<Id, Tipo> tipos, boolean incluirCuringa) throws VariavelJaDeclaradaException {
		boolean ehCuringa = (tipos.get(getId()) == TipoPolimorfico.CURINGA);
		boolean incluir = (ehCuringa&&incluirCuringa) || (!ehCuringa);
		if(incluir) amb.map(getId(), tipos.get(getId()));
	}

	public void reduzir(AmbienteExecucao amb) {
		setValorFuncao((ValorFuncao)getFuncao().reduzir(amb));
	}

}