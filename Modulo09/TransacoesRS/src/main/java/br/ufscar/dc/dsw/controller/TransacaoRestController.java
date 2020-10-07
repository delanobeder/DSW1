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

import br.ufscar.dc.dsw.domain.Cartao;
import br.ufscar.dc.dsw.domain.Transacao;
import br.ufscar.dc.dsw.domain.enumeration.Categoria;
import br.ufscar.dc.dsw.domain.enumeration.Status;
import br.ufscar.dc.dsw.service.spec.ITransacaoService;

@RestController
public class TransacaoRestController {

	@Autowired
	private ITransacaoService service;

	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private void parse(Cartao cartao, JSONObject json) {

		Map<String, Object> map = (Map<String, Object>) json.get("cartao");

		Object id = map.get("id");
		if (id instanceof Integer) {
			cartao.setId(((Integer) id).longValue());
		} else {
			cartao.setId(((Long) id));
		}

		cartao.setTitular((String) map.get("titular"));
		cartao.setCPF((String) map.get("cpf"));
		cartao.setNumero((String) map.get("numero"));
		cartao.setVencimento((String) map.get("vencimento"));
		cartao.setCVV((String) map.get("cvv"));
	}

	private void parse(Transacao transacao, JSONObject json) {

		Object id = json.get("id");

		if (id != null) {
			if (id instanceof Integer) {
				transacao.setId(((Integer) id).longValue());
			} else {
				transacao.setId((Long) id);
			}
		}

		transacao.setDescricao((String) json.get("descricao"));
		Double valor = (Double) json.get("valor");
		transacao.setValor(BigDecimal.valueOf(valor));
		transacao.setData((String) json.get("data"));
		transacao.setCategoria(Categoria.parse((String) json.get("categoria")));
		transacao.setStatus(Status.parse((String) json.get("status")));

		Cartao cartao = new Cartao();
		parse(cartao, json);
		transacao.setCartao(cartao);
	}

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
	public ResponseEntity<Transacao> cria(@RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Transacao transacao = new Transacao();
				parse(transacao, json);
				service.salvar(transacao);
				return ResponseEntity.ok(transacao);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@PutMapping(path = "/transacoes/{id}")
	public ResponseEntity<Transacao> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Transacao transacao = service.buscarPorId(id);
				if (transacao == null) {
					return ResponseEntity.notFound().build();
				} else {
					parse(transacao, json);
					service.salvar(transacao);
					return ResponseEntity.ok(transacao);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@DeleteMapping(path = "/transacoes/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

		Transacao transacao = service.buscarPorId(id);
		if (transacao == null) {
			return ResponseEntity.notFound().build();
		} else {
			service.excluir(id);
			return ResponseEntity.noContent().build();
		}
	}
}
