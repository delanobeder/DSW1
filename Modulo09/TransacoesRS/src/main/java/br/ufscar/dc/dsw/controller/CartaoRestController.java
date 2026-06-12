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

import br.ufscar.dc.dsw.domain.Cartao;
import br.ufscar.dc.dsw.service.spec.ICartaoService;
import jakarta.validation.Valid;

@RestController
public class CartaoRestController {

	@Autowired
	private ICartaoService service;

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
	public ResponseEntity<Cartao> cria(@Valid @RequestBody Cartao cartao, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(null);
		} else {
			service.salvar(cartao);
			return ResponseEntity.ok(cartao);
		}	
	}

	@PutMapping(path = "/cartoes/{id}")
	public ResponseEntity<Cartao> atualiza(@PathVariable("id") long id, @Valid @RequestBody Cartao cartao, BindingResult result) {
		
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(null);
		} else {
			Cartao c = service.buscarPorId(id);
			if (c == null) {
				return ResponseEntity.notFound().build();
			} else {
				cartao.setId(id);
				service.salvar(cartao);
				return ResponseEntity.ok(cartao);
			}
		}
	}

	@DeleteMapping(path = "/cartoes/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

		Cartao cartao = service.buscarPorId(id);
		if (cartao == null) {
			return ResponseEntity.notFound().build();
		} else {
			service.excluir(id);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
	}
}