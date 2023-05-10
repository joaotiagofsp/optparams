package plp.optparam.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import plp.le1.expressoes.Expressao;
import plp.le1.expressoes.Parametro;
import plp.le1.expressoes.ParametroObrigatorio;
import plp.le1.expressoes.ParametroOpcional;
import plp.le1.util.Tipo;
import plp.le2.excecoes.ParametroObrigatorioAposOpcionalException;
import plp.le2.excecoes.VariavelJaDeclaradaException;
import plp.le2.excecoes.VariavelNaoDeclaradaException;
import plp.le2.expressoes.Id;
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
	
	protected List<Parametro> parametros;

	protected Expressao exp;

	public DefFuncao(List<Parametro> parametros, Expressao exp) {
		this.parametros = parametros;
		this.exp = exp;
	}

	public List<Parametro> getListaParametros() {
		return parametros;
	}

	public Expressao getExp() {
		return exp;
	}

	public int getAridade() {
		return parametros.size();
	}

	public boolean checaTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();
		
		boolean temArgumentosOpcionais = false;

		// Usa uma instância de TipoPolimorfico para cada parâmetro formal.
		// Essa instância será inferida durante o getTipo de exp.
		for (Parametro param : parametros) {
			Id id = param.getParamId();
			ambiente.map(id, new TipoPolimorfico());
			
			if (param instanceof ParametroOpcional) {
				temArgumentosOpcionais = true;
			}
			else if (temArgumentosOpcionais) {
				throw new ParametroObrigatorioAposOpcionalException(param);
			}
		}

		// Chama o checa tipo da expressão para veririficar se o corpo da
		// função está correto. Isto irá inferir o tipo dos parâmetros.
		boolean result = exp.checaTipo(ambiente);
		
		Map<Parametro, Tipo> auxMap = new HashMap<Parametro, Tipo>();
		for (Parametro arg : parametros) {
			auxMap.put(arg, ambiente.get(arg.getParamId()));
		}
		
		ambiente.restaura();
		
		// Checa o tipo do valor default de parametros opcionais e se eles referenciam
		// parametros formais listados somente a esquerda
		result &= checaTipoParametrosOpcionais(ambiente, auxMap);

		return result;
	}
	
	private boolean checaTipoParametrosOpcionais(AmbienteCompilacao ambiente, Map<Parametro, Tipo> auxMap) {
		boolean result = true;
		ambiente.incrementa();
		
		for (Parametro param : parametros) {
			if (param instanceof ParametroOpcional) {
				ParametroOpcional paramOpcional = (ParametroOpcional) param;
				Expressao valorPadrao = paramOpcional.getValorPadrao();
				
				if (valorPadrao.checaTipo(ambiente)) {
					Tipo tipoValorPadrao = paramOpcional.getValorPadrao().getTipo(ambiente);
					
					result &= auxMap.get(paramOpcional).eIgual(tipoValorPadrao); 
				}
				else {
					result = false;
				}
			}
			
			ambiente.map(param.getParamId(), auxMap.get(param));
		}

		ambiente.restaura();
		
		return result;
	}

	public Tipo getTipo(AmbienteCompilacao ambiente)
			throws VariavelNaoDeclaradaException, VariavelJaDeclaradaException {
		ambiente.incrementa();

		for (Parametro param : parametros) {
			Id id = param.getParamId();
			ambiente.map(id, new TipoPolimorfico());
		}

		// Usa o checaTipo apenas para inferir o tipo dos parâmetros.
		// Pois o getTipo da expressão pode simplismente retornar o
		// tipo, por exemplo, no caso de uma expressão binária ou unária
		// os tipos sempre são bem definidos (Booleano, Inteiro ou String).
		exp.checaTipo(ambiente);

		// Compõe o tipo desta função do resultado para o primeiro parâmetro.
		Tipo result = exp.getTipo(ambiente);

		// Obtem o tipo inferido de cada parâmetro.
		List<Tipo> params = new ArrayList<Tipo>(getAridade());
		Tipo argTipo;
		for (int i = 0; i < getAridade(); i++) {
			argTipo = ((TipoPolimorfico) ambiente.get(parametros.get(i).getParamId())).inferir();
			params.add(argTipo);
		}
		result = new TipoFuncao(params, result, getAridadeRequerido());

		ambiente.restaura();

		return result;
	}
	
	public int getAridadeRequerido() {
		int aridadeRequerido = 0;
		
		for (Parametro param : parametros) {
			if (param instanceof ParametroObrigatorio) {
				aridadeRequerido++;
			}
		}
		
		return aridadeRequerido;
	}

	public DefFuncao clone() {
		List<Parametro> novaLista = new ArrayList<Parametro>(this.parametros.size());
		
		for (Parametro param : this.parametros){
			novaLista.add(param.clone());
		}
		
		return new DefFuncao(novaLista, this.exp.clone());
	}
}
