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

import br.ufscar.dc.dsw.domain.Estado;
import br.ufscar.dc.dsw.service.spec.IEstadoService;

@RestController
public class EstadoRestController {

	@Autowired
	private IEstadoService service;

	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

	private void parse(Estado estado, JSONObject json) {
		
		Object id = json.get("id");
		if (id != null) {
			if (id instanceof Integer) {
				estado.setId(((Integer) id).longValue());
			} else {
				estado.setId((Long) id);
			}
		}

		estado.setNome((String) json.get("nome"));
		estado.setSigla((String) json.get("sigla"));
	}

	@GetMapping(path = "/estados")
	public ResponseEntity<List<Estado>> lista() {
		List<Estado> lista = service.findAll();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/estados/{id}")
	public ResponseEntity<Estado> lista(@PathVariable("id") long id) {
		Estado estado = service.findById(id);
		if (estado == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(estado);
	}

	@PostMapping(path = "/estados")
	@ResponseBody
	public ResponseEntity<Estado> cria(@RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Estado estado = new Estado();
				parse(estado, json);
				service.save(estado);
				return ResponseEntity.ok(estado);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@PutMapping(path = "/estados/{id}")
	public ResponseEntity<Estado> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Estado estado = service.findById(id);
				if (estado == null) {
					return ResponseEntity.notFound().build();
				} else {
					parse(estado, json);
					service.save(estado);
					return ResponseEntity.ok(estado);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@DeleteMapping(path = "/estados/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

		Estado estado = service.findById(id);
		if (estado == null) {
			return ResponseEntity.notFound().build();
		} else {
			service.delete(id);
			return ResponseEntity.noContent().build();
		}
	}
}
