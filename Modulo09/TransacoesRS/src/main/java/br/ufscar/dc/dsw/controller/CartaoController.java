package br.ufscar.dc.dsw.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Cartao;
import br.ufscar.dc.dsw.service.spec.ICartaoService;

@Controller
@RequestMapping("/cartoes")
public class CartaoController {

	@Autowired
	private ICartaoService service;

	@GetMapping("/cadastrar")
	public String cadastrar(Cartao cartao) {
		return "cartao/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("cartoes", service.buscarTodos());
		return "cartao/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Cartao cartao, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "cartao/cadastro";
		}

		service.salvar(cartao);
		attr.addFlashAttribute("sucess", "Cartão inserido com sucesso");
		return "redirect:/cartoes/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cartao", service.buscarPorId(id));
		return "cartao/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Cartao cartao, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "cartao/cadastro";
		}

		service.salvar(cartao);
		attr.addFlashAttribute("sucess", "Cartão editado com sucesso.");
		return "redirect:/cartoes/listar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		if (service.cartaoTemTransacoes(id)) {
			model.addAttribute("fail", "Cartão não excluído. Possui transação vinculada.");
		} else {
			service.excluir(id);
			model.addAttribute("sucess", "Cartão excluído com sucesso.");	
		}
		return listar(model);
	}
}
