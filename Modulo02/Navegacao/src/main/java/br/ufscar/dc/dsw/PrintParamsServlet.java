package br.ufscar.dc.dsw;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/PrintParams"})
public class PrintParamsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PrintParamsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            Map<String, String[]> mapaDeParametros = request.getParameterMap();
            if (!mapaDeParametros.isEmpty()) {
                out.println("<p>Parâmetros:</p>");
                Set<Entry<String, String[]>> conjuntoDeParametros = mapaDeParametros.entrySet();
                for (Entry<String, String[]> parametro : conjuntoDeParametros) {
                    out.println(parametro.getKey() + ":");
                    for (String v : parametro.getValue()) {
                        out.println("[" + v + "] ");
                    }
                    out.println("<br/>");
                }
            } else {
                out.println("<p>Nenhum parâmetro foi enviado</p>");
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