package br.ufscar.dc.dsw.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.ufscar.dc.dsw.domain.Cartao;
import br.ufscar.dc.dsw.domain.Transacao;
import br.ufscar.dc.dsw.service.spec.IClienteRestService;

@Service
public class ClienteRestService implements IClienteRestService {

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public Cartao buscaCartao(Long id) {
		Cartao cartao = restTemplate.getForObject("http://localhost:8081/cartoes/" + id, Cartao.class);
		return cartao;
	}

	public List<Cartao> buscaCartoes(String cpf) {
		Cartao[] cartoes = restTemplate.getForObject("http://localhost:8081/cartoes/cpf/" + cpf, Cartao[].class);
		return Arrays.asList(cartoes);
	}

	public Transacao buscaTransacao(Long id) {
		return restTemplate.getForObject("http://localhost:8081/transacoes/" + id, Transacao.class);
	}

	public Long salva(Transacao transacao) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Transacao> entity = new HttpEntity<Transacao>(transacao, headers);

		ResponseEntity<Transacao> res = restTemplate.postForEntity("http://localhost:8081/transacoes", entity,
				Transacao.class);
		Transacao t = res.getBody();

		return t.getId();
	}

//	public boolean remove(Long id) {
//		Transacao transacao = this.buscaTransacao(id);
//		transacao.setStatus("CANCELADA");
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		HttpEntity<Transacao> entity = new HttpEntity<Transacao>(transacao, headers);
//
//		ResponseEntity<Transacao> res = restTemplate.exchange("http://localhost:8081/transacoes/" + id, HttpMethod.PUT,
//				entity, Transacao.class);
//
//		return res.getStatusCode() == HttpStatus.OK;
//	}

	public boolean remove(Long id) {

		try {
			restTemplate.delete("http://localhost:8081/transacoes/" + id);
			return true;
		} catch (RestClientException e) {
			return false;
		}
	}
}
