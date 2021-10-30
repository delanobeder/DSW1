package br.ufscar.dc.dsw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Editora;
import br.ufscar.dc.dsw.service.spec.IEditoraService;

@Controller
@RequestMapping("/editoras")
public class EditoraController {
	
	@Autowired
	private IEditoraService service;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Editora editora) {
		return "editora/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("editoras",service.buscarTodos());
		return "editora/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Editora editora, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "editora/cadastro";
		}
		
		service.salvar(editora);
		attr.addFlashAttribute("sucess", "editora.create.sucess");
		return "redirect:/editoras/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("editora", service.buscarPorId(id));
		return "editora/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Editora editora, BindingResult result, RedirectAttributes attr) {
		
		// Apenas rejeita se o problema não for com o CNPJ (CNPJ campo read-only) 
		
		if (result.getFieldErrorCount() > 1 || result.getFieldError("CNPJ") == null) {
			return "editora/cadastro";
		}

		service.salvar(editora);
		attr.addFlashAttribute("sucess", "editora.edit.sucess");
		return "redirect:/editoras/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		if (service.editoraTemLivros(id)) {
			model.addAttribute("fail", "editora.delete.fail");
		} else {
			service.excluir(id);
			model.addAttribute("sucess", "editora.delete.sucess");
		}
		return listar(model);
	}
}
