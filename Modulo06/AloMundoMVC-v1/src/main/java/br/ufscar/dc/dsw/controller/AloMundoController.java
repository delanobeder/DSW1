package br.ufscar.dc.dsw.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AloMundoController {

    @GetMapping("/")
    public String index(Model model) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - dd MMMM yyyy");
    	
    	Calendar cal = Calendar.getInstance();
        model.addAttribute("date", dateFormat.format(cal.getTime()));

        return "index";
    }

}
