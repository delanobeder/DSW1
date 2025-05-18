package br.ufscar.dc.dsw.bean;

import br.ufscar.dc.dsw.dao.CidadeDAO;
import br.ufscar.dc.dsw.dao.EstadoDAO;
import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;

import java.util.List;

public class ListaCidadesBean {

	private String sigla;

	public List<Estado> getEstados() {
        EstadoDAO dao = new EstadoDAO();
        return dao.getAll();
    }
	
	public List<Cidade> getCidades() {
		CidadeDAO dao = new CidadeDAO();
		if (sigla == null || sigla.isEmpty()) {
			return dao.getAll();
		} else {
	        Estado estado = new EstadoDAO().getBySigla(sigla);
	        return new CidadeDAO().getByEstado(estado);
		}
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	public String getSigla() {
		return sigla;
	}
}