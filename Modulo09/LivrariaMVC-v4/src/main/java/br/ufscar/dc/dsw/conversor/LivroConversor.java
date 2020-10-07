package br.ufscar.dc.dsw.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.domain.Livro;
import br.ufscar.dc.dsw.service.spec.ILivroService;

@Component
public class LivroConversor implements Converter<String, Livro>{

	@Autowired
	private ILivroService service;
	
	@Override
	public Livro convert(String text) {
		
		if (text.isEmpty()) {
		 return null;	
		}
		
		Long id = Long.valueOf(text);	
		return service.buscarPorId(id);
	}
}
