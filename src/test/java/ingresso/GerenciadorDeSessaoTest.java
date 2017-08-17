package ingresso;


import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.NestedExceptionUtils;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.validacao.GerenciadorDeSessao;

public class GerenciadorDeSessaoTest {
	@Test
	public void garanteQueNaoDevePermitirSessaoNoMesmoHorario(){
		Filme filme = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		LocalTime horario =  LocalTime.parse("10:00:00");
		Sala sala = new Sala("Eldorado - IMAX", BigDecimal.ONE);
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario, filme, sala));
		Sessao sessao = new Sessao(horario, filme, sala);
		
		GerenciadorDeSessao gerenciado = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciado.cabe(sessao));
		
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesTerminandoDentroDeHorariosDeUmaMesmaSessaoJaExistente(){
		Filme filme = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		LocalTime horario =  LocalTime.parse("10:00:00");
		Sala sala = new Sala("Eldorado - IMAX", BigDecimal.ONE);
		
		List<Sessao> sessoes = Arrays.asList(new Sessao(horario, filme, sala));
		Sessao sessao = new Sessao(horario.minusHours(1), filme, sala);
		
		GerenciadorDeSessao gerenciado = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciado.cabe(sessao));
		
	}
	@Test
	public void garanteQueNaoDevePermitirQueSessoesIniciandoDentroDoHorarioDeUmaSessaoJaExistente(){
		Filme filme = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		LocalTime horario =  LocalTime.parse("10:00:00");
		Sala sala = new Sala("Eldorado - IMAX", BigDecimal.ONE);
		
		List<Sessao> sessoesDaSala = Arrays.asList(new Sessao(horario, filme, sala));
		
		
		GerenciadorDeSessao gerenciado = new GerenciadorDeSessao(sessoesDaSala);
		Assert.assertFalse(gerenciado.cabe(new Sessao(horario.plusHours(1), filme, sala)));
		
	}
	
	@Test
	public void garanteQueDevePermitirUmaInsersaoEntreDoisFilmes(){
		Sala sala = new Sala("Eldorado - IMAX", BigDecimal.ONE);
		Filme filme1 = new Filme("Rougue One", Duration.ofMinutes(90), "SCI-FI", BigDecimal.ONE);
		LocalTime dezHoras =  LocalTime.parse("10:00:00");
		Sessao sessaoDasDez = new Sessao(dezHoras, filme1, sala);
		
		Filme filme2 = new Filme("Rougue One", Duration.ofMinutes(90), "SCI-FI", BigDecimal.ONE);
		LocalTime dezoitoHoras =  LocalTime.parse("18:00:00");
		Sessao sessaoDasDezoito = new Sessao(dezoitoHoras, filme2, sala);
		
		
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez, sessaoDasDezoito);
		
		
		GerenciadorDeSessao gerenciado = new GerenciadorDeSessao(sessoes);
		Assert.assertTrue(gerenciado.cabe(new Sessao(LocalTime.parse("13:00:00"), filme2, sala)));
		
	}
	@Test
	public void oPrecoDaSessaoDeveSerIgualASomaDosPrecosDaSalaMaisOPrecoDoFilme(){
		Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("22.5"));
		Filme filme = new Filme("Rougue One", Duration.ofMinutes(90), "SCI-FI", new BigDecimal("12.0"));
		
		BigDecimal somaDosPrecosDaSalaEFilme = sala.getPreco().add(filme.getPreco());
		
		Sessao sessao = new Sessao(LocalTime.now(), filme, sala);
		Assert.assertEquals(somaDosPrecosDaSalaEFilme, sessao.getPreco());	
		
	}
}
