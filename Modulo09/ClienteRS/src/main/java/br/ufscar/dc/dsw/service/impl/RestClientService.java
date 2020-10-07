package br.ufscar.dc.dsw.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;
import br.ufscar.dc.dsw.service.spec.IRestClientService;

@Service
public class RestClientService implements IRestClientService {

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Override
	public Long create(Cidade cidade) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Cidade> entity = new HttpEntity<Cidade>(cidade, headers);
		String url = "http://localhost:8080/cidades";
		ResponseEntity<Cidade> res = restTemplate.postForEntity(url, entity, Cidade.class);
		Cidade c = res.getBody();

		return c.getId();
	}
	
	@Override
	public List<Cidade> get() {
		String url = "http://localhost:8080/cidades";
		Cidade[] cidades = restTemplate.getForObject(url, Cidade[].class);
		return Arrays.asList(cidades);
	}
	
	@Override
	public Cidade get(Long id) {
		String url = "http://localhost:8080/cidades/" + id;
		return restTemplate.getForObject(url, Cidade.class);
	}
	
	@Override
	public List<Cidade> get(Estado estado) {
		String url = "http://localhost:8080/cidades/estados/" + estado.getId();
		Cidade[] cidades = restTemplate.getForObject(url, Cidade[].class);
		return Arrays.asList(cidades);
	}
	
	@Override
	public boolean update(Cidade cidade) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Cidade> entity = new HttpEntity<Cidade>(cidade, headers);
		String url = "http://localhost:8080/cidades/" + cidade.getId();
		ResponseEntity<Cidade> res = restTemplate.exchange(url, HttpMethod.PUT, entity, Cidade.class);
		return res.getStatusCode() == HttpStatus.OK;
	}
	
	@Override
	public boolean delete(Long id) {
		try {
			String url = "http://localhost:8080/cidades/" + id;
			restTemplate.delete(url);
			return true;
		} catch (RestClientException e) {
			return false;
		}
	}
}