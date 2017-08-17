package ingresso;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.descontos.DecontoTrintaPorCentoParaBancos;
import br.com.caelum.ingresso.model.descontos.DescontoEstudante;
import br.com.caelum.ingresso.model.descontos.SemDesconto;

public class DecontoTest {
	@Test
	public void deveConcederDescotoDe30PorCentoParaIngrssosDeClientesDeBanco(){
		Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.5"));
		Filme filme = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("12"));
		Sessao sessao = new Sessao(LocalTime.now(), filme, sala);
		
		Ingresso ingresso = new Ingresso(sessao, new DecontoTrintaPorCentoParaBancos());
		
		BigDecimal precoEsperado = new BigDecimal("22.75");
		
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
		
	}
	
	@Test
	public void deveConcederDescotoDe50PorCentoParaIngrssosDeEstudante(){
		Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.5"));
		Filme filme = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("12"));
		Sessao sessao = new Sessao(LocalTime.now(), filme, sala);
		
		Ingresso ingresso = new Ingresso(sessao, new DescontoEstudante());
		
		BigDecimal precoEsperado = new BigDecimal("16.25");
		
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
		
	}
	
	@Test
	public void naoDeveConcederDescontoParaIngressoNormal(){
		Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("20.5"));
		Filme filme = new Filme("Rougue One", Duration.ofMinutes(120), "SCI-FI", new BigDecimal("12"));
		Sessao sessao = new Sessao(LocalTime.now(), filme, sala);
		
		Ingresso ingresso = new Ingresso(sessao, new SemDesconto());
		
		BigDecimal precoEsperado = new BigDecimal("32.5");
		
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
		
	}

}
