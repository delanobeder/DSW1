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

import br.ufscar.dc.dsw.domain.Editora;
import br.ufscar.dc.dsw.service.spec.IEditoraService;

@RestController
public class EditoraRestController {

	@Autowired
	private IEditoraService service;

	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

	private void parse(Editora editora, JSONObject json) {

		Object id = json.get("id");
		if (id != null) {
			if (id instanceof Integer) {
				editora.setId(((Integer) id).longValue());
			} else {
				editora.setId((Long) id);
			}
		}

		editora.setCNPJ((String) json.get("cnpj"));
		editora.setNome((String) json.get("nome"));
	}

	@GetMapping(path = "/editoras")
	public ResponseEntity<List<Editora>> lista() {
		List<Editora> lista = service.buscarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/editoras/{id}")
	public ResponseEntity<Editora> lista(@PathVariable("id") long id) {
		Editora editora = service.buscarPorId(id);
		if (editora == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(editora);
	}

	@PostMapping(path = "/editoras")
	@ResponseBody
	public ResponseEntity<Editora> cria(@RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Editora editora = new Editora();
				parse(editora, json);
				service.salvar(editora);
				return ResponseEntity.ok(editora);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@PutMapping(path = "/editoras/{id}")
	public ResponseEntity<Editora> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Editora editora = service.buscarPorId(id);
				if (editora == null) {
					return ResponseEntity.notFound().build();
				} else {
					parse(editora, json);
					service.salvar(editora);
					return ResponseEntity.ok(editora);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@DeleteMapping(path = "/editoras/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

		Editora editora = service.buscarPorId(id);
		if (editora == null) {
			return ResponseEntity.notFound().build();
		} else {
			if (service.editoraTemLivros(id)) {
				return new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
			} else {
				service.excluir(id);
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			}
		}
	}
}
