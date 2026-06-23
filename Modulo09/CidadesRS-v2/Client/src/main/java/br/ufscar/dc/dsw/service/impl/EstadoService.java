package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import br.ufscar.dc.dsw.domain.Estado;
import br.ufscar.dc.dsw.service.spec.IEstadoService;

@Service
public class EstadoService implements IEstadoService {

	RestClient restClient = RestClient.create("http://localhost:8081");

	@Override
	public Long salvar(Estado estado) {
		ResponseEntity<Estado> res = restClient.post()
				.uri("/estados")
				.contentType(MediaType.APPLICATION_JSON)
				.body(estado)
				.retrieve()
				.toEntity(Estado.class);

		Estado e = res.getBody();

		return e.getId();
	}

	@Override
	public List<Estado> buscarTodos() {
		List<Estado> list = restClient.get()
				.uri("/estados")
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.body(new ParameterizedTypeReference<List<Estado>>() {
				});
		return list;
	}

	@Override
	public Estado buscarPorId(Long id) {
		Estado estado = restClient.get()
				.uri("/estados/" + id)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.body(Estado.class);
		return estado;
	}

	@Override
	public boolean atualizar(Estado estado) {
		ResponseEntity<Void> res = restClient.put()
				.uri("/estados/" + estado.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.body(estado)
				.retrieve()
				.toBodilessEntity();

		return res.getStatusCode() == HttpStatus.OK;
	}

	@Override
	public boolean excluir(Long id) {
		try {
			Boolean res = restClient.delete()
					.uri("/estados/" + id)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.body(Boolean.class);

			return res;
		} catch (RestClientResponseException ex) {
			return false;
		}
	}
}
