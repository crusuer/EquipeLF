package br.com.estudiolf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.estudiolf.dao.MembroDAOImpl;

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
		MembroDAOImpl dao = new MembroDAOImpl();
		dao.drop();
		
		return "index";
	}
}
