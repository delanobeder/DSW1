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

import br.ufscar.dc.dsw.domain.Livro;
import br.ufscar.dc.dsw.service.spec.ILivroService;
import jakarta.validation.Valid;

@RestController
public class LivroRestController {

	@Autowired
	private ILivroService service;

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
	public ResponseEntity<Livro> cria(@Valid @RequestBody Livro livro, BindingResult result) {
		
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(null);
		} else {
			service.salvar(livro);
			return ResponseEntity.ok(livro);
		}		
	}

	@PutMapping(path = "/livros/{id}")
	public ResponseEntity<Livro> atualiza(@PathVariable("id") long id, @Valid @RequestBody Livro livro, BindingResult result) {
		
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(null);
		} else {
			Livro l = service.buscarPorId(id);
			if (l == null) {
				return ResponseEntity.notFound().build();
			} else {
				livro.setId(id);
				service.salvar(livro);
				return ResponseEntity.ok(livro);
			}
		}			
	}

	@DeleteMapping(path = "/livros/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

		Livro livro = service.buscarPorId(id);
		if (livro == null) {
			return ResponseEntity.notFound().build();
		} else {
			service.excluir(id);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
	}
}
