package br.com.estudiolf.dao;

import br.com.estudiolf.model.Membro;

public interface MembroDAO {

	public void save(Membro membro);
	public void drop();
}
