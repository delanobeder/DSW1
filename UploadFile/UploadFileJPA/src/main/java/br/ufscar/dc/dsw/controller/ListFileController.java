package br.ufscar.dc.dsw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import br.ufscar.dc.dsw.service.spec.IFileService;

@Controller
public class ListFileController {

	@Autowired
	private IFileService service;

	@GetMapping("/")
	public String list(ModelMap model) throws IOException {

		model.addAttribute("files", service.buscarTodos());
		
		return "index";
	}
}
