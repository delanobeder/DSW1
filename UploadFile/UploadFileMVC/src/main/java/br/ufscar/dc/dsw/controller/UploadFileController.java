package br.ufscar.dc.dsw.controller;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UploadFileController {

	@Autowired
	ServletContext context;
	
	@PostMapping("/uploadFile")
	public String addPhoto(@RequestParam("file") MultipartFile file, RedirectAttributes attr) throws IOException {
		
		String fileName = file.getOriginalFilename();
		
		String uploadPath = context.getRealPath("") + File.separator + "upload";
		File uploadDir = new File(uploadPath);
		
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		
		file.transferTo(new File(uploadDir, fileName));
		
		attr.addFlashAttribute("sucess", "File " + fileName + " has uploaded successfully!");
		return "redirect:/";
	}

}
