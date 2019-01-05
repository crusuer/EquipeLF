package br.com.estudiolf.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.estudiolf.dao.MembroDAOImpl;
import br.com.estudiolf.dao.PontoDAOImpl;
import br.com.estudiolf.model.Membro;
import br.com.estudiolf.model.Ponto;

@Controller
public class PagesController {

	MembroDAOImpl dao = new MembroDAOImpl();
	PontoDAOImpl daoPonto = new PontoDAOImpl();

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@RequestMapping("/index")
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
			membro.setSenha(passwordEncoder.encode(membro.getSenha()));
			dao.save(membro);

			return "index";
		}
	}

	@RequestMapping(value = "/admin")
	public String admin() {
		return "admin";
	}

	@RequestMapping("/user")
	public String user(Authentication authentication, Model model) throws SQLException {
		List<Ponto> pontos = daoPonto.findByUser(authentication.getName());
		model.addAttribute("pontos", pontos);
		model.addAttribute("username", authentication.getName());
		return "user";

	}

	@RequestMapping(value="/marca", method=RequestMethod.POST)
	public String userMarca(Authentication authentication, Model model) throws SQLException {
		if (!daoPonto.update(authentication.getName())) {
			daoPonto.save(authentication.getName());
		}
		return user(authentication,model);
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
