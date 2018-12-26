package br.com.estudiolf.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.estudiolf.db.ConnectionFactory;

@Controller
public class PagesController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public String cadastro() {
		return "cadastro";
	}

	@RequestMapping(value = "/cadastro", method = RequestMethod.POST)
	public String cadastroPost() {
		try {
			Connection con = ConnectionFactory.getConnection();
			
			System.out.println("Conectado");
			String sql = " CREATE TABLE Membro" + 
					" (" + 
					" id int auto_increment PRIMARY KEY NOT NULL, " + 
					" nome varchar(200) NOT NULL, " + 
					" senha varchar(300) NOT NULL, " + 
					" tipo int" + 
					" )";
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS ticks");
			stmt.executeUpdate("DROP TABLE IF EXISTS Membro");
			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "index";
	}
}
