package br.ufscar.dc.dsw;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.ufscar.dc.dsw.dao.ICartaoDAO;
import br.ufscar.dc.dsw.dao.ITransacaoDAO;
import br.ufscar.dc.dsw.domain.Cartao;
import br.ufscar.dc.dsw.domain.Transacao;
import br.ufscar.dc.dsw.domain.enumeration.Categoria;
import br.ufscar.dc.dsw.domain.enumeration.Status;

@SpringBootApplication
public class TransacoesRSApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransacoesRSApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(ICartaoDAO cartaoDAO, ITransacaoDAO transacaoDAO) {
		return (args) -> {

			Cartao c1 = new Cartao();
			c1.setTitular("Beltrano Andrade");
			c1.setCPF("985.849.614-10");
			c1.setNumero("5259 5697 2426 9163");
			c1.setVencimento("02/22");
			c1.setCVV("147");
			cartaoDAO.save(c1);

			Cartao c2 = new Cartao();
			c2.setTitular("Fulano Silva");
			c2.setCPF("367.318.380-04");
			c2.setNumero("4929 5828 5594 8623");
			c2.setVencimento("12/21");
			c2.setCVV("663");
			cartaoDAO.save(c2);
			
			Transacao t1 = new Transacao();
			t1.setDescricao("Compra Mercado Preso");
			t1.setValor(BigDecimal.valueOf(100.87));
			t1.setData("10/08/2020");
			t1.setCategoria(Categoria.COMPRA);
			t1.setStatus(Status.CONFIRMADA);
			t1.setCartao(c1);
			transacaoDAO.save(t1);
			
			Transacao t2 = new Transacao();
			t2.setDescricao("Pagamento via Boleto");
			t2.setValor(BigDecimal.valueOf(100.87));
			t2.setData("01/09/2020");
			t2.setCategoria(Categoria.PAGAMENTO);
			t2.setStatus(Status.CONFIRMADA);
			t2.setCartao(c1);
			transacaoDAO.save(t2);
		};
	}
}
