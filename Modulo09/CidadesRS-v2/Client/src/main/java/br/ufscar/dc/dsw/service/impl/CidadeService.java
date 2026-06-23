package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;
import br.ufscar.dc.dsw.service.spec.ICidadeService;

@Service
public class CidadeService implements ICidadeService {

	RestClient restClient = RestClient.create("http://localhost:8081");

	@Override
	public Long salvar(Cidade cidade) {
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
	public List<Cidade> buscarTodos() {
		List<Cidade> list = restClient.get()
				.uri("/cidades")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.body(new ParameterizedTypeReference<List<Cidade>>() {
				});
		return list;
	}

	@Override
	public Cidade buscarPorId(Long id) {
		Cidade cidade = restClient.get()
				.uri("/cidades/" + id)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.body(Cidade.class);
		return cidade;
	}	

	@Override
	public boolean atualizar(Cidade cidade) {
		ResponseEntity<Void> res = restClient.put()
				.uri("/cidades/" + cidade.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.body(cidade)
				.retrieve()
				.toBodilessEntity();

		return res.getStatusCode() == HttpStatus.OK;
	}

	@Override
	public boolean excluir(Long id) {
		try {
			Boolean res = restClient.delete()
					.uri("/cidades/" + id)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.body(Boolean.class);

			return res;
		} catch (RestClientResponseException ex) {
			return false;
		}
	}
}
