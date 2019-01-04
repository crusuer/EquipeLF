package br.com.estudiolf.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Membro {

	@NotNull
	private String nome;
	@NotNull
	private String usuario;
	@NotNull
	@Size(min = 6, max = 80)
	private String senha;
	@NotNull
	@Size(min = 6, max = 80)
	private String confsenha;
	@Size(min = 6, max = 20)
	private String tipo;

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
		this.senha = senha;
	}

	public String getConfsenha() {
		return confsenha;
	}

	public void setConfsenha(String confsenha) {
		this.confsenha = confsenha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
