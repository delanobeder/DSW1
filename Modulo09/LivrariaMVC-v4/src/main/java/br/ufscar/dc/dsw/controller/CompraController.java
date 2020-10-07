package br.ufscar.dc.dsw.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;

import br.ufscar.dc.dsw.domain.Cartao;
import br.ufscar.dc.dsw.domain.Compra;
import br.ufscar.dc.dsw.domain.Livro;
import br.ufscar.dc.dsw.domain.Transacao;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.service.spec.IClienteRestService;
import br.ufscar.dc.dsw.service.spec.ICompraService;
import br.ufscar.dc.dsw.service.spec.ILivroService;

@Controller
@RequestMapping("/compras")
public class CompraController {

	@Autowired
	private ICompraService service;

	@Autowired
	private ILivroService livroService;

	@Autowired
	private IClienteRestService clienteRestService;

	@GetMapping("/cadastrar")
	public String cadastrar(ModelMap model, Compra compra) {
		try {
			model.addAttribute("livros", listaLivros());
			model.addAttribute("cartoes", listaCartoes());
		} catch (RestClientException e) {
			model.addAttribute("fail", "Falha na conexão [cartão de crédito]");
		}
		return "compra/cadastro";
	}

	private Usuario getUsuario() {
		UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return usuarioDetails.getUser();
	}

	@GetMapping("/listar")
	public String listar(ModelMap model, Locale locale) {

		List<Compra> compras = service.buscarTodos(this.getUsuario());
		try {
			for (Compra c : compras) {
				Transacao t = clienteRestService.buscaTransacao(c.getTransacaoID());
				c.setDetalhes(toHTMLString(t, locale));
				c.setData(t.getData());
			}
		} catch (RestClientException e) {
			model.addAttribute("fail", "Falha na conexão [cartão de crédito]");
		}

		model.addAttribute("compras", compras);

		return "compra/lista";
	}

	@PostMapping("/salvar")
	public String salvar(Compra compra, BindingResult result, ModelMap model) {

		try {
			compra.setValor(compra.getLivro().getPreco());
			compra.setUsuario(this.getUsuario());

			Transacao transacao = new Transacao();
			transacao.setDescricao("Compra Livraria Virtual");
			transacao.setValor(compra.getLivro().getPreco().doubleValue());
			String data = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
			transacao.setData(data);
			transacao.setCategoria("COMPRA");
			transacao.setStatus("CONFIRMADA");
			transacao.setCartao(compra.getCartao());
			Long id = clienteRestService.salva(transacao);
			compra.setTransacaoID(id);
			service.salvar(compra);
			model.addAttribute("sucess", "Compra inserida com sucesso.");
			return "redirect:/compras/listar";
		} catch (RestClientException e) {
			model.addAttribute("fail", "Falha na conexão [cartão de crédito]");
			return cadastrar(model, compra);
		}
	}

	public List<Livro> listaLivros() {
		return livroService.buscarTodos();
	}

	public List<Cartao> listaCartoes() {
		return clienteRestService.buscaCartoes(getUsuario().getCPF());
	}

	private String toHTMLString(Transacao transacao, Locale locale) {
		Cartao cartao = transacao.getCartao();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
		StringBuffer sb = new StringBuffer();

		String s = bundle.getString("transacao.descricao.label");
		sb.append("<b>" + s + "</b> " + transacao.getDescricao() + "<br/>");
		s = bundle.getString("transacao.valor.label");
		sb.append("<b>" + s + "</b> R$ " + String.format("%.2f", (double) transacao.getValor()) + "<br/>");
		s = bundle.getString("transacao.data.label");
		sb.append("<b>" + s + "</b> " + transacao.getData() + "<br/>");
		s = bundle.getString("transacao.cartao.label");
		sb.append("<b>" + s + " </b><br/>");
		sb.append("<ul>");
		s = bundle.getString("cartao.numero.label");
		sb.append("<li><b>" + s + " </b>" + cartao.getNumero() + "</li>");
		s = bundle.getString("cartao.titular.label");
		sb.append("<li><b>" + s + " </b>" + cartao.getTitular() + "</li>");
		s = bundle.getString("cartao.CPF.label");
		sb.append("<li><b>" + s + " </b>" + cartao.getCPF() + "</li>");
		s = bundle.getString("cartao.vencimento.label");
		sb.append("<li><b>" + s + " </b>" + cartao.getVencimento() + "</li>");
		sb.append("</ul>");
		return sb.toString();
	}
}