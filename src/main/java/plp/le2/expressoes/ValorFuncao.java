package plp.le2.expressoes;

import static plp.lf1.util.ToStringProvider.listToString;

import java.util.ArrayList;
import java.util.List;

import plp.le1.expressoes.Expressao;
import plp.le1.expressoes.Parametro;
import plp.le1.expressoes.ParametroOpcional;
import plp.le1.expressoes.Valor;
import plp.le2.memoria.AmbienteExecucao;
import plp.optparam.util.DefFuncao;

/**
 * Representa um valor de função
 * @author <a href="mailto:ctm@cin.ufpe.br">Cleber Moura</a>
 */
public class ValorFuncao extends DefFuncao implements ValorAbstrato {

	private Id id;

	public ValorFuncao(List<Parametro> args, Expressao exp) {
		super(args, exp);
	}

	public Valor avaliar(AmbienteExecucao ambiente) {
		this.reduzir(ambiente);
		return this;
	}

	@Override
	public String toString() {
	
		return String.format("fn %s . %s", listToString(getListaParametros(), " "),
				getExp());
	}
	
	public Id getId() {
		return this.id;
	}
	
	public void setId (Id id){
		this.id = id;
	}
	
	public Expressao reduzir(AmbienteExecucao ambiente) {
		ambiente.incrementa();

		if(this.id != null){
			ambiente.map(this.id, new ValorIrredutivel());
		}
		
		for(Parametro arg : this.parametros){
			Id id = arg.getParamId();
			ambiente.map(id, new ValorIrredutivel());
			
			if (arg instanceof ParametroOpcional) {
				ParametroOpcional argOpcional = (ParametroOpcional) arg;
				argOpcional.setValorPadrao(argOpcional.getValorPadrao().reduzir(ambiente));
			}
		}
		this.exp = exp.reduzir(ambiente);
		
		ambiente.restaura();
		
		return this;
	}
	
	public ValorFuncao clone() {
		ValorFuncao retorno;
		List<Parametro> novaLista = new ArrayList<Parametro>(this.parametros.size());
		
		for (Parametro arg : this.parametros) {
			novaLista.add(arg.clone());
		}
		
		retorno = new ValorFuncao(novaLista, this.exp.clone());
		
		if (this.id != null)
			retorno.setId(this.id.clone());
		
		return retorno;
	}
}
