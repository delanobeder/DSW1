package br.ufscar.dc.dsw.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.domain.Estado;
import br.ufscar.dc.dsw.service.spec.IEstadoService;

@Component
public class EstadoConversor implements Converter<String, Estado>{

	@Autowired
	private IEstadoService service;
	
	@Override
	public Estado convert(String text) {
		
		if (text.isEmpty()) {
		 return null;	
		}
		
		Long id = Long.valueOf(text);	
		return service.buscarPorId(id);
	}
}
