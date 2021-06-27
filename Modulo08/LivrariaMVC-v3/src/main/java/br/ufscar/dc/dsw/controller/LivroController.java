package br.ufscar.dc.dsw.controller;

import java.util.List;

import javax.validation.Valid;

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

import br.ufscar.dc.dsw.domain.Editora;
import br.ufscar.dc.dsw.domain.Livro;
import br.ufscar.dc.dsw.service.spec.IEditoraService;
import br.ufscar.dc.dsw.service.spec.ILivroService;

@Controller
@RequestMapping("/livros")
public class LivroController {

	@Autowired
	private ILivroService livroService;

	@Autowired
	private IEditoraService editoraService;

	@GetMapping("/cadastrar")
	public String cadastrar(Livro livro) {
		return "livro/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("livros", livroService.buscarTodos());
		return "livro/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Livro livro, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "livro/cadastro";
		}

		livroService.salvar(livro);
		attr.addFlashAttribute("sucess", "livro.create.sucess");
		return "redirect:/livros/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("livro", livroService.buscarPorId(id));
		return "livro/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Livro livro, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "livro/cadastro";
		}

		livroService.salvar(livro);
		attr.addFlashAttribute("sucess", "livro.edit.sucess");
		return "redirect:/livros/listar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		livroService.excluir(id);
		attr.addFlashAttribute("sucess", "livro.delete.sucess");
		return "redirect:/livros/listar";
	}

	@ModelAttribute("editoras")
	public List<Editora> listaEditoras() {
		return editoraService.buscarTodos();
	}
}
