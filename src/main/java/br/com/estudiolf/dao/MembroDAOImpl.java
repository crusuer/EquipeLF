package br.com.estudiolf.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.estudiolf.db.ConnectionFactory;
import br.com.estudiolf.model.Membro;

public class MembroDAOImpl implements MembroDAO {

	@Override
	public void save(Membro m) {
		Connection con = ConnectionFactory.getConnection();
		Statement stmt = null;
		try {
			String sql = " INSERT INTO Membro (nome,usuario,senha,tipo) VALUES ('" + m.getNome() + "','" + m.getUsuario()
			+ "','" + m.getSenha() + "','" + m.getTipo() + "')";
			
			stmt = con.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
	@Override
	public boolean find(String user, String pass) {
		Connection con = ConnectionFactory.getConnection();
		Statement stmt = null;
		try {
			String sql = " select usuario from membro where usuario='"+user+"' and senha='"+pass+"' ";
			
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			return rs.next();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return false;
		
	}

}
