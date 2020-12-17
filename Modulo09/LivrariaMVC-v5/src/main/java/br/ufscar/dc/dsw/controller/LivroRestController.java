package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufscar.dc.dsw.domain.Editora;
import br.ufscar.dc.dsw.domain.Livro;
import br.ufscar.dc.dsw.service.spec.ILivroService;

@RestController
public class LivroRestController {

	@Autowired
	private ILivroService service;

	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private void parse(Editora editora, JSONObject json) {

		Map<String, Object> map = (Map<String, Object>) json.get("editora");

		Object id = map.get("id");
		if (id instanceof Integer) {
			editora.setId(((Integer) id).longValue());
		} else {
			editora.setId(((Long) id));
		}

		editora.setCNPJ((String) map.get("cnpj"));
		editora.setNome((String) map.get("nome"));
	}

	private void parse(Livro livro, JSONObject json) {

		Object id = json.get("id");
		if (id != null) {
			if (id instanceof Integer) {
				livro.setId(((Integer) id).longValue());
			} else {
				livro.setId(((Long) id));
			}
		}

		livro.setTitulo((String) json.get("titulo"));
		livro.setAutor((String) json.get("autor"));
		livro.setAno((Integer) json.get("ano"));
		Double value = (Double) json.get("preco");
		livro.setPreco(BigDecimal.valueOf(value));

		Editora editora = new Editora();
		parse(editora, json);
		livro.setEditora(editora);
	}

	@GetMapping(path = "/livros")
	public ResponseEntity<List<Livro>> lista() {
		List<Livro> lista = service.buscarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/livros/{id}")
	public ResponseEntity<Livro> lista(@PathVariable("id") long id) {
		Livro livro = service.buscarPorId(id);
		if (livro == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(livro);
	}

	@GetMapping(path = "/livros/titulos/{titulo}")
	public ResponseEntity<List<Livro>> listaPorTitulo(@PathVariable("titulo") String titulo) {
		List<Livro> lista = service.buscarPorTitulo(titulo);
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping(path = "/livros")
	@ResponseBody
	public ResponseEntity<Livro> cria(@RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Livro livro = new Livro();
				parse(livro, json);
				service.salvar(livro);
				return ResponseEntity.ok(livro);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@PutMapping(path = "/livros/{id}")
	public ResponseEntity<Livro> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Livro livro = service.buscarPorId(id);
				if (livro == null) {
					return ResponseEntity.notFound().build();
				} else {
					parse(livro, json);
					service.salvar(livro);
					return ResponseEntity.ok(livro);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@DeleteMapping(path = "/livros/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

		Livro livro = service.buscarPorId(id);
		if (livro == null) {
			return ResponseEntity.notFound().build();
		} else {
			service.excluir(id);
			return ResponseEntity.noContent().build();
		}
	}
}
