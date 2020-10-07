package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Cartao;
import br.ufscar.dc.dsw.domain.Transacao;

public interface IClienteRestService {

	Cartao buscaCartao(Long id);
	
	List<Cartao> buscaCartoes(String cpf);
	
	Transacao buscaTransacao(Long id);
	
	Long salva(Transacao transacao);
	
	boolean remove(Long id);
	
}
