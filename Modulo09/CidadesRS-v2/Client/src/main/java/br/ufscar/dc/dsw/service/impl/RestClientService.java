package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;
import br.ufscar.dc.dsw.service.spec.IRestClientService;

@Service
public class RestClientService implements IRestClientService {

    RestClient restClient = RestClient.create("http://localhost:8081");

    @Override
    public Long create(Cidade cidade) {
    	
    	ResponseEntity<Cidade> res = restClient.post()
    		    .uri("/cidades")
    		    .contentType(MediaType.APPLICATION_JSON)
    		    .body(cidade)
    		    .retrieve()
    		    .toEntity(Cidade.class);
    	
    	Cidade c = res.getBody();

		return c.getId();
    }

    @Override
    public List<Cidade> get() {
        List<Cidade> list = restClient.get()
                .uri("/cidades")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return list;
    }

    @Override
    public Cidade get(Long id) {
        Cidade cidade = restClient.get()
                .uri("/cidades/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(Cidade.class);
        return cidade;
    }

    @Override
    public List<Cidade> get(Estado estado) {
    	List<Cidade> list = restClient.get()
                .uri("/cidades/estados/" + estado.getId())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return list;
    }

    @Override
    public boolean update(Cidade cidade) {
    	ResponseEntity<Void> res = restClient.put()
    	  .uri("/cidades/" + cidade.getId())
    	  .contentType(MediaType.APPLICATION_JSON)
    	  .body(cidade)
    	  .retrieve()
    	  .toBodilessEntity();
    	
    	return res.getStatusCode() == HttpStatus.OK;
    }

    @Override
    public boolean delete(Long id) {
    	ResponseEntity<Void> res = restClient.delete()
    			  .uri("/cidades/" + id)
    			  .retrieve()
    			  .toBodilessEntity();
    	return res.getStatusCode() == HttpStatus.NO_CONTENT;
    }
}
