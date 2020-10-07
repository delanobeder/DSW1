package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufscar.dc.dsw.domain.Cartao;
import br.ufscar.dc.dsw.service.spec.ICartaoService;

@RestController
public class CartaoRestController {

	@Autowired
	private ICartaoService service;

	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}
	
	private void parse(Cartao cartao, JSONObject json) {

		Object id = json.get("id");
		if (id != null) {
			if (id instanceof Integer) {
				cartao.setId(((Integer) id).longValue());
			} else {
				cartao.setId(((Long) id));
			}
		}
		
		cartao.setTitular((String) json.get("titular"));
		cartao.setCPF((String) json.get("cpf"));
		cartao.setNumero((String) json.get("numero"));
		cartao.setVencimento((String) json.get("vencimento"));
		cartao.setCVV((String) json.get("cvv"));		
	}

	@GetMapping(path = "/cartoes")
	public ResponseEntity<List<Cartao>> lista() {
		List<Cartao> lista = service.buscarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/cartoes/cpf/{cpf}")
	public ResponseEntity<List<Cartao>> listaPorCPF(@PathVariable("cpf") String cpf) {
		List<Cartao> lista = service.buscarPorCPF(cpf);
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping(path = "/cartoes/{id}")
	public ResponseEntity<Cartao> lista(@PathVariable("id") long id) {
		Cartao cartao = service.buscarPorId(id);
		if (cartao == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cartao);
	}

	@PostMapping(path = "/cartoes")
	@ResponseBody
	public ResponseEntity<Cartao> cria(@RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Cartao cartao = new Cartao(); 
				parse(cartao, json);
				service.salvar(cartao);
				return ResponseEntity.ok(cartao);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@PutMapping(path = "/cartoes/{id}")
	public ResponseEntity<Cartao> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Cartao cartao = service.buscarPorId(id);
				if (cartao == null) {
					return ResponseEntity.notFound().build();
				} else {
					parse(cartao, json);
					service.salvar(cartao);
					return ResponseEntity.ok(cartao);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@DeleteMapping(path = "/cartoes/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

		Cartao cartao = service.buscarPorId(id);
		if (cartao == null) {
			return ResponseEntity.notFound().build();
		} else {
			service.excluir(id);
			return ResponseEntity.noContent().build();
		}
	}
}