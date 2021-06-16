package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Compra;
import br.ufscar.dc.dsw.domain.Usuario;

public interface ICompraService {

	Compra buscarPorId(Long id);

	List<Compra> buscarTodosPorUsuario(Usuario u);
	
	void salvar(Compra editora);
}
