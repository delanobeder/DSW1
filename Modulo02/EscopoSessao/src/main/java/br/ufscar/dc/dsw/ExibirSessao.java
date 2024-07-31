package br.ufscar.dc.dsw;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/ExibirSessao"})
public class ExibirSessao extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ExibirSessao</title>");
            out.println("</head>");
            out.println("<body>");

            out.println("Dados da sessão:<br/>");
            HttpSession sessao = request.getSession();
            String idSessao = sessao.getId();
            Date dataCriacao = new Date(sessao.getCreationTime());
            Date dataUltimoAcesso = new Date(sessao.getLastAccessedTime());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss:SSS");

            out.println("Id:" + idSessao + "<br/>");
            out.println("Data criação:" + sdf.format(dataCriacao) + "<br/>");
            out.println("Data último acesso:" + sdf.format(dataUltimoAcesso) + "<br/>");
            out.println("Atributos:<br/>");
            Enumeration<String> nomesAtributos = sessao.getAttributeNames();
            while (nomesAtributos.hasMoreElements()) {
                String nomeAtributo = nomesAtributos.nextElement();
                Object valor = sessao.getAttribute(nomeAtributo);
                out.println(nomeAtributo + "=" + valor + "<br/>");
            }
            out.println("</body>");
            out.println("</html>");
        }
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
