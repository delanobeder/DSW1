package br.ufscar.dc.dsw.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.service.spec.ICidadeService;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
public class CidadeRestController {

	@Autowired
	private ICidadeService service;

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
	public ResponseEntity<Cidade> cria(@Valid @RequestBody Cidade cidade, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(null);
		} else {
			service.save(cidade);
			return ResponseEntity.ok(cidade);
		}
	}

	@PutMapping(path = "/cidades/{id}")
	public ResponseEntity<Cidade> atualiza(@PathVariable("id") long id, @Valid @RequestBody Cidade cidade, 
			BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(null);
		} else {
			Cidade c = service.findById(id);
			if (c == null) {
				return ResponseEntity.notFound().build();
			} else {
				cidade.setId(id);
				service.save(cidade);
				return ResponseEntity.ok(cidade);
			}
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
