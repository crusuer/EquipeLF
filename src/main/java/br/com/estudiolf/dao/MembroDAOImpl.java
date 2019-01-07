package br.com.estudiolf.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.estudiolf.db.ConnectionFactory;
import br.com.estudiolf.model.Membro;

public class MembroDAOImpl implements MembroDAO {

	@Override
	public void save(Membro m) {
		Connection con = ConnectionFactory.getConnection();
		Statement stmt = null;
		try {
			String sql = " INSERT INTO Membro (nome,usuario,senha,habilitado,tipo) VALUES ('" + m.getNome() + "','"
					+ m.getUsuario() + "','" + m.getSenha() + "',true,'" + m.getTipo() + "')";

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
			String sql = " select usuario from membro where usuario='" + user + "' and senha='" + pass + "' ";

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

	@Override
	public List<Membro> findAll() {
		Connection con = ConnectionFactory.getConnection();
		Statement stmt = null;
		List<Membro> membros = new ArrayList<>();
		try {
			String sql = " select nome,usuario from membro where habilitado=true and tipo='ROLE_USER' ";

			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Membro membro = new Membro();
				membro.setNome(rs.getString("nome"));
				membro.setUsuario(rs.getString("usuario"));
				membros.add(membro);

			}
			rs.close();
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
		return membros;
	}

	@Override
	public void disable(String usuario) {
		Connection con = ConnectionFactory.getConnection();
		Statement stmt = null;
		try {
			String sql = " UPDATE Membro set habilitado=false where usuario='" + usuario + "' ";

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
