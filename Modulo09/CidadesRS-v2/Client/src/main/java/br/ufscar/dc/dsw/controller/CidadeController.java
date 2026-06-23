package br.ufscar.dc.dsw.controller;

import java.util.List;

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

import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;
import br.ufscar.dc.dsw.service.spec.ICidadeService;
import br.ufscar.dc.dsw.service.spec.IEstadoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cidade")
public class CidadeController {

	@Autowired
	private ICidadeService cidadeService;

	@Autowired
	private IEstadoService estadoService;

	@GetMapping("/cadastrar")
	public String cadastrar(Cidade cidade) {
		return "cidade/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("cidades", cidadeService.buscarTodos());
		return "cidade/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "cidade/cadastro";
		}

		cidadeService.salvar(cidade);
		attr.addFlashAttribute("sucess", "Cidade inserida com sucesso");
		return "redirect:/cidade/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		Cidade cidade = cidadeService.buscarPorId(id);
		model.addAttribute("cidade", cidade);
		model.addAttribute("estado", cidade.getEstado());
		return "cidade/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "cidade/cadastro";
		}

		cidadeService.atualizar(cidade);
		attr.addFlashAttribute("sucess", "Cidade editada com sucesso.");
		return "redirect:/cidade/listar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		cidadeService.excluir(id);
		attr.addFlashAttribute("sucess", "Cidade excluída com sucesso.");
		return "redirect:/cidade/listar";
	}

	@ModelAttribute("estados")
	public List<Estado> listaEstados() {
		List<Estado> estados = estadoService.buscarTodos();
		return estados;
	}
}
