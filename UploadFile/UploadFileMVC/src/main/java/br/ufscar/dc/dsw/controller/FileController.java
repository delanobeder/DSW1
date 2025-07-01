package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.service.spec.FilesStorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FileController {

	@Autowired
	FilesStorageService storageService;

	@GetMapping("/")
	public String home(ModelMap model) throws IOException {

		List<String> fileList = new ArrayList<String>();
		
		for (Path path: storageService.loadAll().toList()) {
			fileList.add(path.toString());
		}
				
		model.addAttribute("files", fileList);
		
		return "index";
	}
	
	@GetMapping(value = "/files/{filename:.+}")
	public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable String filename) {
		
		
		Resource resource = storageService.load(filename);
		
		try {
			// copies all bytes to an output stream
			response.getOutputStream().write(resource.getContentAsByteArray());

			// flushes output stream
			response.getOutputStream().flush();
		} catch (IOException e) {
			System.out.println("Error :- " + e.getMessage());
		}
	}
		
	
	@PostMapping("/uploadFile")
	public String addFile(@RequestParam("file") MultipartFile file, RedirectAttributes attr) throws IOException {
		
		storageService.save(file);
		
		String fileName = file.getOriginalFilename();
				
		attr.addFlashAttribute("sucess", "File " + fileName + " has uploaded successfully!");
		
		return "redirect:/";
	}
}
