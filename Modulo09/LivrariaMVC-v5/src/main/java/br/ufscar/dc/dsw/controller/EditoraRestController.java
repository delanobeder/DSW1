package br.ufscar.dc.dsw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.domain.Editora;
import br.ufscar.dc.dsw.service.spec.IEditoraService;
import jakarta.validation.Valid;

@RestController
public class EditoraRestController {

	@Autowired
	private IEditoraService service;
	
	@GetMapping(path = "/api/editoras")
	public ResponseEntity<List<Editora>> lista() {
		List<Editora> lista = service.buscarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/api/editoras/{id}")
	public ResponseEntity<Editora> lista(@PathVariable("id") long id) {
		Editora editora = service.buscarPorId(id);
		if (editora == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(editora);
	}

	@PostMapping(path = "/api/editoras")
	@ResponseBody
	public ResponseEntity<Editora> cria(@Valid @RequestBody Editora editora, BindingResult result) {

		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(null);
		} else {
			service.salvar(editora);
			return ResponseEntity.ok(editora);
		}
	}

	@PutMapping(path = "/api/editoras/{id}")
	public ResponseEntity<Editora> atualiza(@PathVariable("id") long id, @Valid @RequestBody Editora editora,
			BindingResult result) {
		// Apenas rejeita se o problema não for com o CNPJ (CNPJ campo read-only)

		if (result.getFieldErrorCount() > 1 || result.getFieldError("CNPJ") == null) {
			return ResponseEntity.badRequest().body(null);
		} else {
			Editora e = service.buscarPorId(id);
			if (e == null) {
				return ResponseEntity.notFound().build();
			} else {
				editora.setId(id);
				service.salvar(editora);
				return ResponseEntity.ok(editora);
			}
		}
	}

	@DeleteMapping(path = "/api/editoras/{id}")
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
