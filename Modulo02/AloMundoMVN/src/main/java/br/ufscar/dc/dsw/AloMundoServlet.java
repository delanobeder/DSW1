package br.ufscar.dc.dsw;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/AloMundoServletNoMaven" })
public class AloMundoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
              HttpServletResponse response)
                 throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Primeira aplicação</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Alô mundo!</h1>");
        out.println("<h1>"+ new Date() + "</h1>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
