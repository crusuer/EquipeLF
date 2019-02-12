package br.com.estudiolf.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestParam;

import br.com.estudiolf.entity.Baile;
import br.com.estudiolf.entity.Membro;
import br.com.estudiolf.entity.Ponto;
import br.com.estudiolf.entity.Resumo;
import br.com.estudiolf.repository.BaileRepository;
import br.com.estudiolf.repository.MembroRepository;
import br.com.estudiolf.repository.PontoRepository;
import br.com.estudiolf.utils.TimeUtils;

@Controller
public class PagesController {

	@Autowired
	MembroRepository membroRepository;

	@Autowired
	PontoRepository pontoRepository;

	@Autowired
	BaileRepository baileRepository;

	TimeUtils timeUtils = new TimeUtils();

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

	@GetMapping(value = "/cadastro")
	public String cadastro(Model model) {
		model.addAttribute("membro", new Membro());
		return "cadastro";
	}

	@GetMapping(value = "/cadastroAdm")
	public String cadastroAdm(Model model) {
		model.addAttribute("membro", new Membro());
		return "cadastroAdm";
	}

	@PostMapping(value = "/cadastro")
	public String cadastroPost(@Valid Membro membro, BindingResult result) {
		if (result.hasErrors()) {
			return "cadastro";
		} else {
			membro.setNome(membro.getNome().toUpperCase());
			membro.setSenha(passwordEncoder.encode(membro.getSenha()));
			membro.setHabilitado(true);
			membroRepository.save(membro);

			return "index";
		}
	}

	@RequestMapping(value = "/admin")
	public String admin() {
		return "admin/admin";
	}

	@RequestMapping(value = "/admin/usuarios")
	public String usuarios(Model model) {
		List<Membro> membros = membroRepository.findByTipoAndHabilitado("ROLE_USER", true);
		sort(membros);
		model.addAttribute("membros", membros);
		return "admin/usuarios";
	}

	@GetMapping("/admin/usuarios/del/{usuario}")
	public String edit(@PathVariable("usuario") String usuario, Model model) {
		Membro m = membroRepository.findByUsuario(usuario);
		m.setHabilitado(false);
		membroRepository.save(m);
		return usuarios(model);
	}

	@RequestMapping(value = "/admin/eventos")
	public String eventos(@RequestParam(value = "dia", required = false) String dia, Model model) throws ParseException {
		if (dia != null && !dia.isEmpty()) {
			model.addAttribute("dia", dia);
			Iterable<Membro> membros = membroRepository
					.findByDiaBaile(dia);
			model.addAttribute("membros", membros);
			Iterable<Membro> presentes = membroRepository
					.findByPresenteBaile(dia);
			model.addAttribute("presentes", presentes);
		}
		return "admin/eventos";
	}

	@RequestMapping(value = "/admin/eventos/check")
	public String eventosCheck(@RequestParam(value = "dia", required = true) String dia,
			@RequestParam(value = "usuario", required = true) String usuario, Model model) throws ParseException {
		Baile baile = new Baile();
		baile.setDia(dia);
		baile.setUsuario(membroRepository.findByUsuario(usuario));

		baileRepository.save(baile);

		return eventos(dia, model);
	}

	@RequestMapping(value = "/admin/relatorios/mensal")
	public String relatorioMensal(@RequestParam(value = "mes", required = false, defaultValue = "01") String mes,Model model) {
		List<Resumo> resumos = new ArrayList<>();
		List<Membro> membros = membroRepository.findByTipoAndHabilitado("ROLE_USER", true);
		sort(membros);
		
		String dia = "__/"+ mes + timeUtils.sdfDate.format(timeUtils.getTime()).substring(5);
		String diaEvento = timeUtils.interDate.format(timeUtils.getTime()).substring(0,5) + mes + "-__";
		for (Membro m : membros) {
			Resumo resumo = new Resumo();
			resumo.setNome(m.getNome());

			Iterable<Ponto> pontos = pontoRepository.findByUsuarioAndDia(m, dia);
			long minutos = 0;
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
			if ((minutos % 60) < 10) {
				total += "0";
			}
			total += (minutos % 60);
			resumo.setTotal(total);
			Integer eventos = baileRepository.countEventos(m, diaEvento);
			resumo.setEventos(eventos);
			
			resumos.add(resumo);
		}

		model.addAttribute("resumos", resumos);
		return "admin/mensal";
	}

	@RequestMapping(value = "/admin/relatorios/anual")
	public String relatorioAnual(Model model) {
		List<Resumo> resumos = new ArrayList<>();
		List<Membro> membros = membroRepository.findByTipoAndHabilitado("ROLE_USER", true);
		sort(membros);

		String dia = "__/__" + timeUtils.sdfDate.format(timeUtils.getTime()).substring(5);
		String diaEvento = timeUtils.interDate.format(timeUtils.getTime()).substring(0,4) + "-__-__";
		for (Membro m : membros) {
			Resumo resumo = new Resumo();
			resumo.setNome(m.getNome());

			Iterable<Ponto> pontos = pontoRepository.findByUsuarioAndDia(m, dia);
			long minutos = 0;
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
			if ((minutos % 60) < 10) {
				total += "0";
			}
			total += (minutos % 60);
			resumo.setTotal(total);
			Integer eventos = baileRepository.countEventos(m,diaEvento);
			resumo.setEventos(eventos);
			
			resumos.add(resumo);
		}

		model.addAttribute("resumos", resumos);
		return "admin/anual";
	}

	@RequestMapping(value = "/admin/marcacoes")
	public String marcacoes(@RequestParam(value = "nome", required = false) String nome, Model model) {
		Iterable<Ponto> pontos = new ArrayList<>();
		if (nome != null && !nome.isEmpty()) {
			nome = nome.toUpperCase();
			model.addAttribute("nome", nome);
			List<Membro> m = membroRepository.findByNomeLike(nome+"%");
			if ( m != null && !m.isEmpty()) {
				String dia = "__" + timeUtils.sdfDate.format(timeUtils.getTime()).substring(2);
				pontos = pontoRepository.findByUsuarioAndDia(m.get(0), dia);
				model.addAttribute("nome", m.get(0).getNome());
			}
		}
		model.addAttribute("pontos", pontos);
		return "admin/marcacoes";
	}

	@GetMapping(value = "/admin/marcacoes/edit/{id}")
	public String marcacoesEdit(@PathVariable("id") Long id, Model model) {
		Ponto ponto = pontoRepository.findById(id).orElse(null);
		model.addAttribute("ponto", ponto);
		return "admin/update";
	}

	@PostMapping(value = "/admin/marcacoes/update")
	public String marcacoesUpdate(@Valid Ponto ponto, Authentication authentication, Model model)
			throws ParseException {
		Ponto p = pontoRepository.findById(ponto.getId()).orElse(null);
		p.setDia(ponto.getDia());
		p.setInicio(ponto.getInicio());
		p.setFim(ponto.getFim());
		p.setInicioP(ponto.getInicioP());
		p.setFimP(ponto.getFimP());
		p.setTotal();
		pontoRepository.save(p);
		return marcacoes("", model);
	}

	@GetMapping("/user")
	public String user(Authentication authentication, Model model) {
		String dia = "__" + timeUtils.sdfDate.format(timeUtils.getTime()).substring(2);

		Iterable<Ponto> pontos = pontoRepository
				.findByUsuarioAndDia(membroRepository.findByUsuario(authentication.getName()), dia);
		model.addAttribute("pontos", pontos);
		model.addAttribute("username", authentication.getName());
		return "user";

	}

	@PostMapping(value = "/user")
	public String userMarca(Authentication authentication, Model model, HttpServletRequest request,
			@RequestParam(value = "action", required = true) String action) throws ParseException {
		String remoteAddr = "";
		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}

		if (remoteAddr.equals("189.54.147.218")) {
			String dia = timeUtils.sdfDate.format(timeUtils.getTime());
			Iterable<Ponto> ponto = pontoRepository
					.findByUsuarioAndDia(membroRepository.findByUsuario(authentication.getName()), dia);
			boolean check = true;
			for (Ponto p : ponto) {
				check = false;
				if (action.equals("Pausar")) {
					if (p.getInicioP() == null) {
						p.setInicioP(timeUtils.sdfTime.format(timeUtils.getTime()));
					} else {
						p.setFimP(timeUtils.sdfTime.format(timeUtils.getTime()));
					}
				} else {
					p.setFim(timeUtils.sdfTime.format(timeUtils.getTime()));
					p.setTotal();
				}
				pontoRepository.save(p);
			}
			if (check) {
				Ponto p = new Ponto();
				p.setDia(dia);
				p.setInicio(timeUtils.sdfTime.format(timeUtils.getTime()));
				p.setTotal();
				p.setUsuario(membroRepository.findByUsuario(authentication.getName()));
				pontoRepository.save(p);
			}
		}

		return user(authentication, model);
	}

	private void sort(List<Membro> membros) {
		Collections.sort(membros, new Comparator<Membro>() {
			@Override
			public int compare(Membro m1, Membro m2) {
				return m1.getNome().compareTo(m2.getNome());
			}
		});
	}
}
