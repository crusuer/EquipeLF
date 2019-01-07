package br.com.estudiolf.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.estudiolf.dao.MembroDAOImpl;
import br.com.estudiolf.dao.PontoDAOImpl;
import br.com.estudiolf.model.Membro;
import br.com.estudiolf.model.Ponto;
import br.com.estudiolf.model.Resumo;

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
		return "admin/admin";
	}

	@RequestMapping(value = "/admin/usuarios")
	public String usuarios(Model model) {
		List<Membro> membros = dao.findAll();
		model.addAttribute("membros", membros);
		return "admin/usuarios";
	}

	@GetMapping("/admin/usuarios/del/{usuario}")
	public String edit(@PathVariable("usuario") String usuario, Model model) {
		dao.disable(usuario);
		return usuarios(model);
	}

	@RequestMapping(value = "/admin/relatorios/mensal")
	public String relatorios(Model model) {
		List<Resumo> resumos = new ArrayList<>();
		List<Membro> membros = dao.findAll();

		for (Membro m : membros) {
			Resumo resumo = new Resumo();
			resumo.setNome(m.getNome());

			List<Ponto> pontos = daoPonto.findByUser(m.getUsuario());
			int minutos = 0;
			for (Ponto p : pontos) {
				try {
					if (!p.getTotal().isEmpty()) {
						String[] split = p.getTotal().split(":");
						minutos += (60 * Integer.parseInt(split[0]) + Integer.parseInt(split[1]));
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			String total = (int) (minutos / 60) + ":";
			if((minutos % 60)<10) {
				total += "0";
			}
			total += (minutos % 60);
			resumo.setTotal(total);
			resumos.add(resumo);
		}

		model.addAttribute("resumos", resumos);
		return "admin/mensal";
	}

	@RequestMapping(value = "/admin/marcacoes")
	public String marcacoes(@RequestParam(value = "username", required = false) String username, Model model) {
		List<Ponto> pontos = new ArrayList<>();
		if (username != null) {
			pontos = daoPonto.findByUser(username);
		}
		model.addAttribute("pontos", pontos);
		return "admin/marcacoes";
	}

	@GetMapping(value = "/admin/marcacoes/edit/{id}")
	public String marcacoesEdit(@PathVariable("id") String id, Model model) {
		Ponto ponto = daoPonto.findOne(id);
		model.addAttribute("ponto",ponto);
		return "admin/update";
	}
	
	@PostMapping(value = "/admin/marcacoes/update")
	public String marcacoesUpdate(@Valid Ponto ponto, Model model) {
		daoPonto.updateAdmin(ponto);
		return marcacoes("", model);
	}

	@RequestMapping("/user")
	public String user(Authentication authentication, Model model) throws SQLException {
		List<Ponto> pontos = daoPonto.findByUser(authentication.getName());
		model.addAttribute("pontos", pontos);
		model.addAttribute("username", authentication.getName());
		return "user";

	}

	@RequestMapping(value = "/marca", method = RequestMethod.POST)
	public String userMarca(Authentication authentication, Model model) throws SQLException {
		if (!daoPonto.update(authentication.getName())) {
			daoPonto.save(authentication.getName());
		}
		return user(authentication, model);
	}
}
