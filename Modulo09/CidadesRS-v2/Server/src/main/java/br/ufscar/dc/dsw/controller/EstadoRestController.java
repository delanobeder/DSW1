package br.ufscar.dc.dsw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.domain.Estado;
import br.ufscar.dc.dsw.service.spec.IEstadoService;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
public class EstadoRestController {

	@Autowired
	private IEstadoService service;

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
	public ResponseEntity<Estado> cria(@Valid @RequestBody Estado estado, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(null);
		} else {
			service.save(estado);
			return ResponseEntity.ok(estado);
		}
	}

	@PutMapping(path = "/estados/{id}")
	public ResponseEntity<Estado> atualiza(@PathVariable("id") long id, @Valid @RequestBody Estado estado,
			BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(null);
		} else {
			Estado e = service.findById(id);
			if (e == null) {
				return ResponseEntity.notFound().build();
			} else {
				estado.setId(id);
				service.save(estado);
				return ResponseEntity.ok(estado);
			}
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
