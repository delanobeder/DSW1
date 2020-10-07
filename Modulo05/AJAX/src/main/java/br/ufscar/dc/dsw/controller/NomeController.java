package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.bean.BuscaPorNomeBean;
import br.ufscar.dc.dsw.domain.Cidade;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/buscaPorNome"})
public class NomeController extends HttpServlet {

    private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("term");

        Gson gsonBuilder = new GsonBuilder().create();
        List<String> cidades = new ArrayList<>();
        for (Cidade cidade : new BuscaPorNomeBean().getCidades(nome)) {
            cidades.add(cidade.toString());
        }

        System.out.println(gsonBuilder.toJson(cidades));
        response.getWriter().write(gsonBuilder.toJson(cidades));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}