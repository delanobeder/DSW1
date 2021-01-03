package br.ufscar.dc.dsw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufscar.dc.dsw.domain.Cidade;
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
	
	@GetMapping("/listaCidades")
	public String listaCidades(@RequestParam(required = false) Long estado, ModelMap model) {
		List<Cidade> cidades;
		model.addAttribute("estados", estadoService.findAll());
		if (estado == null) {
			cidades = cidadeService.findAll();
		} else {
			cidades = cidadeService.findByEstado(estado);
		}
		model.addAttribute("cidades", cidades);
		return "listaCidades";
	}
}