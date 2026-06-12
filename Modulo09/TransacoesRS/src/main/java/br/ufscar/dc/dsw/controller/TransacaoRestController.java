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

import br.ufscar.dc.dsw.domain.Transacao;
import br.ufscar.dc.dsw.service.spec.ITransacaoService;
import jakarta.validation.Valid;

@RestController
public class TransacaoRestController {

	@Autowired
	private ITransacaoService service;

	@GetMapping(path = "/transacoes")
	public ResponseEntity<List<Transacao>> lista() {
		List<Transacao> lista = service.buscarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/transacoes/{id}")
	public ResponseEntity<Transacao> lista(@PathVariable("id") long id) {
		Transacao transacao = service.buscarPorId(id);
		if (transacao == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(transacao);
	}

	@PostMapping(path = "/transacoes")
	@ResponseBody
	public ResponseEntity<Transacao> cria(@Valid @RequestBody Transacao transacao, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(null);
		} else {
			service.salvar(transacao);
			return ResponseEntity.ok(transacao);
		}	
	}

	@PutMapping(path = "/transacoes/{id}")
	public ResponseEntity<Transacao> atualiza(@PathVariable("id") long id, @Valid @RequestBody Transacao transacao, BindingResult result) {
		
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(null);
		} else {
			Transacao t = service.buscarPorId(id);
			if (t == null) {
				return ResponseEntity.notFound().build();
			} else {
				transacao.setId(id);
				service.salvar(transacao);
				return ResponseEntity.ok(transacao);
			}
		}	

	}

	@DeleteMapping(path = "/transacoes/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

		Transacao transacao = service.buscarPorId(id);
		if (transacao == null) {
			return ResponseEntity.notFound().build();
		} else {
			service.excluir(id);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
	}
}
