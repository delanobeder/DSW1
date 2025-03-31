package br.ufscar.dc.dsw;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/ServletB"})
public class ServletB extends HttpServlet {

    private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE HTML>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServletB</title>");
            out.println("</head>");
            out.println("<body>");
            Integer i = (Integer) request.getAttribute("valor");
            if (i == null) {
                i = 0;
            }
            i = i + 30;
            request.setAttribute("valor", i);
            out.println("Conteúdo gerado no ServletB: Valor = " + i + "<br/>");
            request.getRequestDispatcher("ServletC").include(request, response);
            out.println("</body>");
            out.println("</html>");
        } catch(Exception e) {
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
