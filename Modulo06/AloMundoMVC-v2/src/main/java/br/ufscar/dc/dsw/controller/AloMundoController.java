package br.ufscar.dc.dsw.controller;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AloMundoController {

    @GetMapping("/")
    public String index(Model model, Locale locale) {

        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.FULL, locale);
    	
    	Calendar cal = Calendar.getInstance();
        model.addAttribute("dateString", dateFormat.format(cal.getTime()));
        model.addAttribute("date", cal.getTime());

        return "index";
    }

}