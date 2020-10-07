package br.ufscar.dc.dsw.bean;

import br.ufscar.dc.dsw.dao.CidadeDAO;
import br.ufscar.dc.dsw.dao.EstadoDAO;
import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;

import java.util.List;

public class BuscaPorEstadoBean {

    public List<Estado> getEstados() {
        EstadoDAO dao = new EstadoDAO();
        return dao.getAll();
    }

    public List<Cidade> getCidades(String sigla) {
        EstadoDAO dao = new EstadoDAO();
        Estado estado = dao.getBySigla(sigla);
        return new CidadeDAO().getByEstado(estado);
    } 
}