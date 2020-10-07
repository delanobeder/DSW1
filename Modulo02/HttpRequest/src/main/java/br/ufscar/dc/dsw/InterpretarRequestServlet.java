package br.ufscar.dc.dsw;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/InterpretarRequestServlet"})
public class InterpretarRequestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response, String metodo)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InterpretarRequestServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InterpretarRequestServlet at " + request.getContextPath() + "</h1>");
            String requestURL = request.getRequestURL().toString();
            String protocol = request.getProtocol();
            int port = request.getLocalPort();
            String queryString = request.getQueryString();
            out.println("Requisição: " + requestURL + "<br/>");
            out.println("Protocolo: " + protocol + "<br/>");
            out.println("Porta: " + port + "<br/>");
            out.println("Método: " + metodo + "<br/>");
            out.println("Query: " + queryString + "<br/>");
            Map<String, String[]> mapaDeParametros = request.getParameterMap();
            Set<Entry<String, String[]>> conjuntoDeParametros = mapaDeParametros.entrySet();
            for (Entry<String, String[]> parametro : conjuntoDeParametros) {
                out.println(parametro.getKey() + ":");
                for (String v : parametro.getValue()) {
                    out.println("[" + v + "] ");
                }
                out.println("<br/>");
            }
            
            out.println("O nome é: " + request.getParameter("nome") + "<br/>");
            boolean receberNoticias = false;
            String strReceberNoticias = request.getParameter("receber");
            if(strReceberNoticias != null && strReceberNoticias.equals("ok")) {
                receberNoticias = true;
            }
            out.println("Receber noticias: " + receberNoticias + "<br/>");
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, "GET");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, "POST");
    }
}
