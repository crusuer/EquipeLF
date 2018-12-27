package br.com.estudiolf.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.estudiolf.dao.MembroDAOImpl;
import br.com.estudiolf.model.Membro;

@Controller
public class PagesController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public String cadastro(Model model) {
		model.addAttribute("membro", new Membro());
		return "cadastro";
	}

	@RequestMapping(value = "/cadastro", method = RequestMethod.POST)
	public String cadastroPost(@Valid Membro membro, BindingResult result) {
		if (result.hasErrors()) {

			return "cadastro";

		} else {

			if (!membro.getSenha().equals(membro.getConfsenha())) {
				return "cadastro";
			}
			MembroDAOImpl dao = new MembroDAOImpl();
			dao.save(membro);

			return "index";
		}
	}
}
