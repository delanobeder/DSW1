package br.ufscar.dc.dsw.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ErrorViewController implements ErrorViewResolver {

	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> map) {
		
		ModelAndView model = new ModelAndView("error");
		model.addObject("status", status.value());
		switch (status.value()) {
				case 404:
					model.addObject("error", "Página não encontrada.");
					model.addObject("message", "A url para a página '" + map.get("path") + "' não existe.");
					break;
				case 500:
					model.addObject("error", "Ocorreu um erro interno no servidor.");
					model.addObject("message", "Ocorreu um erro inexperado, tente mais tarde.");
					break;
				default:
					model.addObject("error", map.get("error"));
					model.addObject("message", map.get("message"));
					break;
		}		
		return model;
	}

}