package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;
import br.ufscar.dc.dsw.service.spec.ICidadeService;

@RestController
public class CidadeRestController {

	@Autowired
	private ICidadeService service;

	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private void parse(Estado estado, JSONObject json) {
		Map<String, Object> map = (Map<String, Object>) json.get("estado");
		
		Object id = map.get("id");
		if (id instanceof Integer) {
			estado.setId(((Integer) id).longValue());
		} else {
			estado.setId((Long) id);
		}
		 		
		estado.setSigla((String) map.get("sigla"));
		estado.setNome((String) map.get("nome"));
	}

	private void parse(Cidade cidade, JSONObject json) {

		Object id = json.get("id");
		if (id != null) {
			if (id instanceof Integer) {
				cidade.setId(((Integer) id).longValue());
			} else {
				cidade.setId((Long) id);
			}
		}

		cidade.setNome((String) json.get("nome"));

		Estado estado = new Estado();
		parse(estado, json);
		cidade.setEstado(estado);
	}

	@GetMapping(path = "/cidades")
	public ResponseEntity<List<Cidade>> lista() {
		List<Cidade> lista = service.findAll();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/cidades/{id}")
	public ResponseEntity<Cidade> lista(@PathVariable("id") long id) {
		Cidade cidade = service.findById(id);
		if (cidade == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cidade);
	}

	@GetMapping(path = "/cidades/estados/{id}")
	public ResponseEntity<List<Cidade>> listaPorEstado(@PathVariable("id") long id) {
		List<Cidade> lista = service.findByEstado(id);
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/cidades/filtros")
	public ResponseEntity<List<String>> listaPorNome(@RequestParam(name = "term") String nome) {
		List<Cidade> cidades = service.findByNome("%" + nome + "%");
		List<String> lista = new ArrayList<>();
		for (Cidade c : cidades) {
			lista.add(c.getNome() + "/" + c.getEstado().getSigla());
		}
		return ResponseEntity.ok(lista);
	}

	@PostMapping(path = "/cidades")
	@ResponseBody
	public ResponseEntity<Cidade> cria(@RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Cidade cidade = new Cidade();
				parse(cidade, json);
				service.save(cidade);
				return ResponseEntity.ok(cidade);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@PutMapping(path = "/cidades/{id}")
	public ResponseEntity<Cidade> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Cidade cidade = service.findById(id);
				if (cidade == null) {
					return ResponseEntity.notFound().build();
				} else {
					parse(cidade, json);
					service.save(cidade);
					return ResponseEntity.ok(cidade);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@DeleteMapping(path = "/cidades/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

		Cidade cidade = service.findById(id);
		if (cidade == null) {
			return ResponseEntity.notFound().build();
		} else {
			service.delete(id);
			return ResponseEntity.noContent().build();
		}
	}
}
