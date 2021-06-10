package br.ufscar.dc.dsw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.FileEntity;
import br.ufscar.dc.dsw.service.spec.IFileService;

@Controller
public class UploadFileController {
	
	@Autowired
	private IFileService service;
	
	@PostMapping("/uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attr) throws IOException {
				
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileEntity entity = new FileEntity(fileName, file.getContentType(), file.getBytes());
		
		service.salvar(entity);
		
		attr.addFlashAttribute("sucess", "File " + fileName + " has uploaded successfully!");
		return "redirect:/";
	}

}
