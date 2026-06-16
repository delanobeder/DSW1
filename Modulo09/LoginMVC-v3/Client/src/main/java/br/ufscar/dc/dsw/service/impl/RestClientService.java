package br.ufscar.dc.dsw.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import br.ufscar.dc.dsw.domain.JwtToken;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.spec.IRestClientService;

@Service
public class RestClientService implements IRestClientService {

	RestClient restClient = RestClient.create("http://localhost:8081/usuarios");

	@Override
	public boolean createUser(Usuario usuario) {
		try {
			ResponseEntity<Void> res = restClient.post()
				.uri("")
				.contentType(MediaType.APPLICATION_JSON)
				.body(usuario)
				.retrieve()
				.toBodilessEntity();

				return res.getStatusCode() == HttpStatus.CREATED;

		} catch (RestClientResponseException ex) {
			return false;
		}
	}

	@Override
	public JwtToken login(Usuario usuario) {

		ResponseEntity<JwtToken> res = restClient.post()
				.uri("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.body(usuario)
				.retrieve()
				.toEntity(JwtToken.class);

		JwtToken token = res.getBody();

		return token;
	}

	@Override
	public boolean testeAutenticacao(JwtToken token) {
		try {
			ResponseEntity<Void> res = restClient.get()
				.uri("/jwt")
				.header("Authorization", "Bearer " + token.content())
				.retrieve()
				.toBodilessEntity();

				return res.getStatusCode() == HttpStatus.OK;

		} catch (RestClientResponseException ex) {
			return false;
		}
	}

	@Override
	public boolean testeAutorizacaoAdmin(JwtToken token) {
		try {
			ResponseEntity<Void> res = restClient.get()
				.uri("/jwt/admin")
				.header("Authorization", "Bearer " + token.content())
				.retrieve()
				.toBodilessEntity();

				return res.getStatusCode() == HttpStatus.OK;
		
			} catch (RestClientResponseException ex) {
			return false;
		}
	}

	@Override
	public boolean testeAutorizacaoUser(JwtToken token) {
		try {
			ResponseEntity<Void> res = restClient.get()
				.uri("/jwt/user")
				.header("Authorization", "Bearer " + token.content())
				.retrieve()
				.toBodilessEntity();

				return res.getStatusCode() == HttpStatus.OK;
		
		} catch (RestClientResponseException ex) {
			return false;
		}
	}
}
