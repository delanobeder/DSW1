package br.ufscar.dc.dsw.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.ufscar.dc.dsw.domain.FileEntity;
import br.ufscar.dc.dsw.service.spec.IFileService;

@Controller
public class DownloadFileController {

	@Autowired
	private IFileService service;

	@GetMapping(value = "/download/{id}")
	public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) {
		FileEntity entity = service.buscarPorId(id);

		// set content type
		response.setContentType(entity.getType());
		
		// add response header (caso queira forçar o download)
		// response.addHeader("Content-Disposition", "attachment; filename=" + entity.getName());

		try {
			// copies all bytes to an output stream
			response.getOutputStream().write(entity.getData());

			// flushes output stream
			response.getOutputStream().flush();
		} catch (IOException e) {
			System.out.println("Error :- " + e.getMessage());
		}
	}
}
