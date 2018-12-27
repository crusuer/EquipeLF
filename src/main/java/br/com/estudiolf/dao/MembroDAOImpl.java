package br.com.estudiolf.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.estudiolf.db.ConnectionFactory;
import br.com.estudiolf.model.Membro;

public class MembroDAOImpl implements MembroDAO {

	@Override
	public void save(Membro membro) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drop() {
		try {
			Connection con = ConnectionFactory.getConnection();

			String sql = " DROP TABLE IF EXISTS test";
			Statement stmt;

			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

}
