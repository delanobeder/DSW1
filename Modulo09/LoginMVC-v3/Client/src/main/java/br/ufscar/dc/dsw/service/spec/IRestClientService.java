package br.ufscar.dc.dsw.service.spec;

import br.ufscar.dc.dsw.domain.JwtToken;
import br.ufscar.dc.dsw.domain.Usuario;

public interface IRestClientService {
	
	boolean createUser (Usuario usuario);
	
	JwtToken login(Usuario usuario);

	boolean testeAutenticacao(JwtToken token);

	boolean testeAutorizacaoAdmin(JwtToken token);

	boolean testeAutorizacaoUser(JwtToken token);
}
