package br.com.caelum.ingresso.validacao;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {
	private List<Sessao> sessoesDasala;
	
	public GerenciadorDeSessao(List<Sessao> sessoesDaSala){
		this.sessoesDasala = sessoesDasala;
	}
	
	public boolean cabe(final Sessao sessaoAtual){
		Optional<Boolean> optionalCabe = sessoesDasala
				.stream()
				.map(sessaoExistente -> 
				    horarioIsValido(sessaoExistente, sessaoAtual))
				.reduce(Boolean::logicalAnd);
				return optionalCabe.orElse(true);
	}
	
	private boolean horarioIsValido(Sessao sessaoExixtente, Sessao sessaoAtual){
		LocalTime horarioSessao = sessaoExixtente.getHorario();
		LocalTime horarioAtual = sessaoAtual.getHorario();
		boolean ehAntes = horarioAtual.isBefore(horarioSessao);
		if(ehAntes){
			return sessaoAtual.getHorarioTermino().isBefore(horarioSessao);
		}else{
			return sessaoExixtente.getHorarioTermino().isBefore(horarioAtual);
		}
	}
}
