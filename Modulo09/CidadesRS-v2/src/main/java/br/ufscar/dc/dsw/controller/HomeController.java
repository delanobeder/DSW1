package br.ufscar.dc.dsw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import br.ufscar.dc.dsw.service.spec.ICidadeService;
import br.ufscar.dc.dsw.service.spec.IEstadoService;

@Controller
public class HomeController {

	@Autowired
	private IEstadoService estadoService;
	
	@Autowired
	private ICidadeService cidadeService;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/buscaEstado")
	public String buscaEstado(ModelMap model) {
		model.addAttribute("estados", estadoService.findAll());
		return "buscaEstado";
	}
	
	@GetMapping("/buscaNome")
	public String buscaNome() {
		return "buscaNome";
	}
	
	@GetMapping("/tabelaDinamica")
	public String tabelaDinamica(ModelMap model) {
		model.addAttribute("cidades", cidadeService.findAll());
		return "tabelaDinamica";
	}
}