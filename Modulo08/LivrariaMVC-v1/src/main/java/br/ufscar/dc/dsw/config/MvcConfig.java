package br.ufscar.dc.dsw.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.ufscar.dc.dsw.conversor.BigDecimalConversor;


@Configuration
@ComponentScan(basePackages = "br.ufscar.dc.dsw.config")
public class MvcConfig implements WebMvcConfigurer {
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new BigDecimalConversor());
    }
}