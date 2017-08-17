package ingresso;


import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.validacao.GerenciadorDeSessao;

public class GerenciadorDeSessaoTest {
	@Test
	public void garanteQueNaoDevePermitirSessaoNoMesmoHorario(){
		Filme filme = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI");
		LocalTime horario =  LocalTime.parse("10:00:00");
		Sala sala = new Sala("");
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario, filme, sala));
		Sessao sessao = new Sessao(horario, filme, sala);
		
		GerenciadorDeSessao gerenciado = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciado.cabe(sessao));
		
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesTerminandoDentroDeHorariosDeUmaMesmaSessaoJaExistente(){
		Filme filme = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI");
		LocalTime horario =  LocalTime.parse("10:00:00");
		Sala sala = new Sala("");
		
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario, filme, sala));
		Sessao sessao = new Sessao(horario.minusHours(1), filme, sala);
		
		GerenciadorDeSessao gerenciado = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciado.cabe(sessao));
		
	}
	@Test
	public void garanteQueNaoDevePermitirQueSessoesIniciandoDentroDoHorarioDeUmaSessaoJaExistente(){
		Filme filme = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI");
		LocalTime horario =  LocalTime.parse("10:00:00");
		Sala sala = new Sala("");
		
		List<Sessao> sessoesDaSala = Arrays.asList(new Sessao(horario, filme, sala));
		
		
		GerenciadorDeSessao gerenciado = new GerenciadorDeSessao(sessoesDaSala);
		Assert.assertFalse(gerenciado.cabe(new Sessao(horario.plusHours(1), filme, sala)));
		
	}
}
