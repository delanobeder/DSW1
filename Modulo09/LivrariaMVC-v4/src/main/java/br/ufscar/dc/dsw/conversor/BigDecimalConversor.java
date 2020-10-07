package br.ufscar.dc.dsw.conversor;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;

public class BigDecimalConversor implements Converter<String, BigDecimal> {

	@Override
	public BigDecimal convert(String text) {

		if (text.isEmpty()) {
			return null;
		} else {
			text = text.replace(',','.');	
		}
		
		return new BigDecimal(Double.parseDouble(text));
	}

}
