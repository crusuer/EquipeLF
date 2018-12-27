package br.com.estudiolf.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Membro {

	private int id;
	@NotNull
	private String nome;
	@NotNull
	private String usuario;
	@NotNull
	@Size(min = 6)
	private String senha;
	@NotNull
	@Size(min = 6)
	private String confsenha;
	@Min(1)
	private int tipo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(senha.getBytes());
			byte[] hashMd5 = md.digest();

			StringBuilder s = new StringBuilder();
			for (int i = 0; i < hashMd5.length; i++) {
				int parteAlta = ((hashMd5[i] >> 4) & 0xf) << 4;
				int parteBaixa = hashMd5[i] & 0xf;
				if (parteAlta == 0)
					s.append('0');
				s.append(Integer.toHexString(parteAlta | parteBaixa));
			}

			this.senha = s.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	public String getConfsenha() {
		return confsenha;
	}

	public void setConfsenha(String confsenha) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(confsenha.getBytes());
			byte[] hashMd5 = md.digest();

			StringBuilder s = new StringBuilder();
			for (int i = 0; i < hashMd5.length; i++) {
				int parteAlta = ((hashMd5[i] >> 4) & 0xf) << 4;
				int parteBaixa = hashMd5[i] & 0xf;
				if (parteAlta == 0)
					s.append('0');
				s.append(Integer.toHexString(parteAlta | parteBaixa));
			}

			this.confsenha = s.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

}
