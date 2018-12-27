package br.com.estudiolf.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.estudiolf.db.ConnectionFactory;
import br.com.estudiolf.model.Membro;

public class MembroDAOImpl implements MembroDAO {

	@Override
	public void save(Membro m) {
		String sql = " INSERT INTO Membro (nome,usuario,senha,tipo) VALUES ('" + m.getNome() + "','" + m.getUsuario()
				+ "','" + m.getSenha() + "','" + m.getTipo() + "')";

		dbQuery(sql);
	}

	@Override
	public void drop() {

		String sql = " DROP TABLE IF EXISTS membro";

		dbQuery(sql);
	}

	private void dbQuery(String sql) {
		Connection con = ConnectionFactory.getConnection();
		Statement stmt = null;
		try {
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

}
