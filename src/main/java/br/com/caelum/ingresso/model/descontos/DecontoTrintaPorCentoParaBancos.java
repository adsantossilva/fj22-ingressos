package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;

public class DecontoTrintaPorCentoParaBancos implements Desconto {
	private BigDecimal percentualDesconto = new BigDecimal("0.3");
	private BigDecimal divisor = new BigDecimal("100.0");
	
	@Override
	public BigDecimal aplicarDescontoSobre(BigDecimal precoOriginal) {
		return precoOriginal.subtract(trintaPorCentoSobre(precoOriginal));
		//return precoOriginal = percentualDesconto.divide(divisor); 
	}
	
	private BigDecimal trintaPorCentoSobre(BigDecimal precoOriginal){
		return precoOriginal.multiply(percentualDesconto);
	}
}
