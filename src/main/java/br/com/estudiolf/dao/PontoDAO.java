package br.com.estudiolf.dao;

import java.util.List;

import br.com.estudiolf.model.Ponto;

public interface PontoDAO {

	public List<Ponto> findByUser(String user);
	public Ponto findOne(String id);
	public void save(String user);
	public boolean update(String user);
	public void updateAdmin(Ponto ponto);
}
