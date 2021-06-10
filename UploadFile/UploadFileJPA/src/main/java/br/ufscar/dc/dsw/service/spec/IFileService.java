package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.FileEntity;

public interface IFileService {

	public FileEntity salvar(FileEntity file);	
	
	public void excluir(Long id);
	
	public FileEntity buscarPorId(Long id); 

	public List<FileEntity> buscarTodos();
}