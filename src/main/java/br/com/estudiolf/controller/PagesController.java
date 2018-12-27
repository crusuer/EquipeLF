package br.com.estudiolf.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.estudiolf.dao.MembroDAOImpl;
import br.com.estudiolf.model.Membro;

@Controller
public class PagesController {

	MembroDAOImpl dao = new MembroDAOImpl();

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public String cadastro(Model model) {
		model.addAttribute("membro", new Membro());
		return "cadastro";
	}

	@RequestMapping(value = "/cadastroAdm", method = RequestMethod.GET)
	public String cadastroAdm(Model model) {
		model.addAttribute("membro", new Membro());
		return "cadastroAdm";
	}

	@RequestMapping(value = "/cadastro", method = RequestMethod.POST)
	public String cadastroPost(@Valid Membro membro, BindingResult result) {
		if (result.hasErrors()) {
			return "cadastro";
		} else {
			membro.setSenha(encrypt(membro.getSenha()));
			membro.setConfsenha(encrypt(membro.getConfsenha()));
			
			if (!membro.getSenha().equals(membro.getConfsenha())) {
				return "cadastro";
			}			
			dao.save(membro);

			return "index";
		}
	}

	@RequestMapping("/user")
	public String userPost(@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "password", required = false) String password, Model model) throws SQLException {
		if (username == null || password == null) {
			return "index";
		}
		String pass = encrypt(password);

		if (dao.find(username, pass)) {
			model.addAttribute("user", username);
			return "user";
		}

		return "index";
	}

	public String encrypt(String senha) {
		StringBuilder s = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(senha.getBytes());
			byte[] hashMd5 = md.digest();

			for (int i = 0; i < hashMd5.length; i++) {
				int parteAlta = ((hashMd5[i] >> 4) & 0xf) << 4;
				int parteBaixa = hashMd5[i] & 0xf;
				if (parteAlta == 0)
					s.append('0');
				s.append(Integer.toHexString(parteAlta | parteBaixa));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return s.toString();
	}
}
