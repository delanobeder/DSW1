package br.ufscar.dc.dsw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/buscaEstado").setViewName("buscaEstado");
		registry.addViewController("/buscaNome").setViewName("buscaNome");
		registry.addViewController("/tabelaDinamica").setViewName("tabelaDinamica");
	}
}