package br.ufscar.dc.dsw.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListFileController {

	@Autowired
	ServletContext context;

	@GetMapping("/")
	public String home(ModelMap model) throws IOException {

		List<String> fileList = new ArrayList<String>();
		
		String uploadPath = context.getRealPath("") + File.separator + "upload";
		File uploadDir = new File(uploadPath);

		File[] files = uploadDir.listFiles();

		if (files != null) {
			for (final File file : files) {
				fileList.add(file.getName());
			}
		}
		
		model.addAttribute("files", fileList);
		
		return "index";
	}
}
