package br.ufscar.dc.dsw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.domain.Compra;
import br.ufscar.dc.dsw.service.spec.ICompraService;

@RestController
public class CompraRestController {

	@Autowired
	private ICompraService service;
	
	@GetMapping(path = "/compras/usuarios/{id}")
	public ResponseEntity<List<Compra>> lista(@PathVariable("id") long id) {
		List<Compra> lista = service.buscarTodosPorUsuario(id);
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}
}
