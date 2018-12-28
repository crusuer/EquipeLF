package br.com.estudiolf.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.estudiolf.db.ConnectionFactory;
import br.com.estudiolf.model.Ponto;

public class PontoDAOImpl implements PontoDAO {

	@Override
	public List<Ponto> findByUser(String user) {
		Connection con = ConnectionFactory.getConnection();
		Statement stmt = null;
		List<Ponto> pontos = new ArrayList<>();
		try {
			String sql = " select id,dia,inicio,fim,editado from ponto where usuario='" + user + "' ";

			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Ponto ponto = new Ponto();
				ponto.setId(rs.getInt("ID"));
				ponto.setDia(rs.getString("dia"));
				ponto.setInicio(rs.getString("inicio"));
				ponto.setFim(rs.getString("fim"));
				ponto.setEditado(rs.getInt("editado"));
				pontos.add(ponto);
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
		return pontos;

	}

}
