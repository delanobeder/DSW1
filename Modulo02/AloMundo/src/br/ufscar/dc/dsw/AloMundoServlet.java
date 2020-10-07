package br.ufscar.dc.dsw;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/TestarAloMundo"})
public class AloMundoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
              HttpServletResponse response)
                 throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
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
