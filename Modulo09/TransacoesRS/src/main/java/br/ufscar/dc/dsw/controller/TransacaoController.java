package br.ufscar.dc.dsw.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Cartao;
import br.ufscar.dc.dsw.domain.Transacao;
import br.ufscar.dc.dsw.domain.enumeration.Categoria;
import br.ufscar.dc.dsw.domain.enumeration.Status;
import br.ufscar.dc.dsw.service.spec.ICartaoService;
import br.ufscar.dc.dsw.service.spec.ITransacaoService;

@Controller
@RequestMapping("/transacoes")
public class TransacaoController {

	@Autowired
	private ITransacaoService service;

	@Autowired
	private ICartaoService cartaoService;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Transacao transacao) {
		return "transacao/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("transacoes", service.buscarTodos());
		return "transacao/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Transacao transacao, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "transacao/cadastro";
		}

		service.salvar(transacao);
		attr.addFlashAttribute("sucess", "Transação inserida com sucesso.");
		return "redirect:/transacoes/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("transacao", service.buscarPorId(id));
		return "transacao/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Transacao transacao, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "transacao/cadastro";
		}

		service.salvar(transacao);
		attr.addFlashAttribute("sucess", "Transação editada com sucesso.");
		return "redirect:/transacoes/listar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		service.excluir(id);
		model.addAttribute("sucess", "Transação excluída com sucesso.");
		return listar(model);
	}
	
	@ModelAttribute("cartoes")
	public List<Cartao> listaCartoes() {
		return cartaoService.buscarTodos();
	}
	
	@ModelAttribute("categorias")
	public Categoria[] listaCategorias() {
		return Categoria.values();
	}
	
	@ModelAttribute("statuses")
	public Status[] listaStatuses() {
		return Status.values();
	}
	
}
