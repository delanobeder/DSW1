package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufscar.dc.dsw.dao.CompraDAO;
import br.ufscar.dc.dsw.dao.LivroDAO;
import br.ufscar.dc.dsw.domain.Compra;
import br.ufscar.dc.dsw.domain.Livro;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.util.Erro;

@WebServlet(urlPatterns = "/compras/*")
public class CompraController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CompraDAO dao;

    @Override
    public void init() {
        dao = new CompraDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
		Erro erros = new Erro();

		if (usuario == null) {
			response.sendRedirect(request.getContextPath());
			return;
		} else if (!usuario.getPapel().equals("USER")) {
			erros.add("Acesso não autorizado!");
			erros.add("Apenas Papel [USER] tem acesso a essa página");
			request.setAttribute("mensagens", erros);
			RequestDispatcher rd = request.getRequestDispatcher("/noAuth.jsp");
			rd.forward(request, response);
			return;
		}
		
        String action = request.getPathInfo();
        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "/cadastro":
                    apresentaFormCadastro(request, response);
                    break;
                case "/insercao":
                    insere(request, response);
                    break;
                default:
                    lista(request, response);
                    break;
            }
        } catch (RuntimeException | IOException | ServletException e) {
            throw new ServletException(e);
        }
    }

    private void lista(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
        List<Compra> listaCompras = dao.getAll(usuario);
        request.setAttribute("listaCompras", listaCompras);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/logado/compra/lista.jsp");
        dispatcher.forward(request, response);
    }

    private Map<Long, Livro> getLivros() {
        Map<Long, Livro> livros = new HashMap<>();
        for (Livro livro: new LivroDAO().getAll()) {
            livros.put(livro.getId(), livro);
        }
        return livros;
    }

    private void apresentaFormCadastro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("livros", getLivros());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/logado/compra/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void insere(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        Long id = Long.parseLong(request.getParameter("livro"));

        Livro livro = new LivroDAO().get(id);
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
        
        String data = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        Compra compra = new Compra(data, livro.getPreco(), livro, usuario);
        dao.insert(compra);
        
        response.sendRedirect("lista");
    }
}