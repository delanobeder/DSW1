package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.bean.BuscaPorEstadoBean;
import br.ufscar.dc.dsw.domain.Cidade;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/buscaPorEstado"})
public class EstadoController extends HttpServlet {

    private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String sigla = request.getParameter("estado");
        String buffer = "<tr><td>Cidade</td><td><select id='cidade' name='cidade' "
                + "onchange='apresenta()'>";
        buffer = buffer + "<option value=''>Selecione</option>";
        List<Cidade> cidades = new BuscaPorEstadoBean().getCidades(sigla);
        for (Cidade cidade : cidades) {
            buffer = buffer + "<option value='" + cidade.getNome() + "'>" + cidade.getNome() + "</option>";
        }
        buffer = buffer + "</select></td>";
        
        
        System.out.println(buffer);
        response.getWriter().println(buffer);
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