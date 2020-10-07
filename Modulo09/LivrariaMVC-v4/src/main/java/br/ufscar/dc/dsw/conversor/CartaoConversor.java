package br.ufscar.dc.dsw.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import br.ufscar.dc.dsw.domain.Cartao;
import br.ufscar.dc.dsw.service.spec.IClienteRestService;

@Component
public class CartaoConversor implements Converter<String, Cartao>{

	@Autowired
	private IClienteRestService service;
	
	@Override
	public Cartao convert(String text) {
		
		if (text.isEmpty()) {
		 return null;	
		}
		
		Long id = Long.valueOf(text);	
		try {
			return service.buscaCartao(id);
		} catch(RestClientException e) {
			return null;
		}
		
	}
}