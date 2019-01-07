package br.com.estudiolf.dao;

import java.util.List;

import br.com.estudiolf.model.Membro;

public interface MembroDAO {

	public void save(Membro membro);
	public boolean find(String user, String pass);
	public List<Membro> findAll();
	public void disable(String usuario);
}
