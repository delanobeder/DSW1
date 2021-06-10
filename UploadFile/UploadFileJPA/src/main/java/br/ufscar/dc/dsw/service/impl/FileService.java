package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufscar.dc.dsw.dao.IFileDAO;
import br.ufscar.dc.dsw.domain.FileEntity;
import br.ufscar.dc.dsw.service.spec.IFileService;

@Service
public class FileService implements IFileService {

	@Autowired
	private IFileDAO dao;

	public FileEntity salvar(FileEntity file) {
		return dao.save(file);
	}
	
	public void excluir(Long id) {
		dao.deleteById(id);
	}
	
	public FileEntity buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	public List<FileEntity> buscarTodos() {
		return dao.findAll();
	}
}