package br.ufscar.dc.dsw.bean;

import br.ufscar.dc.dsw.dao.CidadeDAO;
import br.ufscar.dc.dsw.domain.Cidade;

import java.util.List;

public class BuscaPorNomeBean {

	public List<Cidade> getCidades() {
		CidadeDAO dao = new CidadeDAO();
		return dao.getAll();
	}
	
	public List<Cidade> getCidades(String nome) {
		CidadeDAO dao = new CidadeDAO();
		List<Cidade> lista;
		if (nome.length() > 0) {
			lista = dao.getByNome(nome);
		} else {
			lista = dao.getAll();
		}
		return lista;
	}
}