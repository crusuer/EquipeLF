package br.com.estudiolf.controller;

import java.awt.image.BufferedImage;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

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

	@PostMapping(value = "/marca")
	public String userMarca(Authentication authentication, Model model) throws SQLException {
		Result result = getQRCode();
		System.out.println("fir");
		if(result != null && result.getText().equals("S0me kn0w h0w t0 c0de")) {
			System.out.println("sec");
			if (!daoPonto.update(authentication.getName())) {
				daoPonto.save(authentication.getName());
			}
		}
		System.out.println("thir");
		return user(authentication, model);
	}
	
	private Result getQRCode() {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		
		Result result = null;
		long startTime = System.currentTimeMillis();
		long currentTime = startTime;
		do {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			BufferedImage image = null;

			if (webcam.isOpen()) {
				
				if ((image = webcam.getImage()) == null) {
					continue;					
				}

				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

				try {					
					result = new MultiFormatReader().decode(bitmap);					
				} catch (NotFoundException e) {
					
				}
			}
			currentTime = System.currentTimeMillis();

		} while (result == null && currentTime < startTime+20000);
		
		webcam.close();
		return result;
	}
}
