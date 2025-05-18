package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.bean.BuscaPorEstadoBean;
import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/estados"})
public class EstadosController extends HttpServlet {

    private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gsonBuilder = new GsonBuilder().create();
        List<Estado> estados = new ArrayList<>();
        for (Estado estado : new BuscaPorEstadoBean().getEstados()) {
            estados.add(estado);
        }

        System.out.println(gsonBuilder.toJson(estados));
        response.getWriter().write(gsonBuilder.toJson(estados));
    }
}