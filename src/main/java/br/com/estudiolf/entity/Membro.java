package br.com.estudiolf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Membro", //
		uniqueConstraints = { //
				@UniqueConstraint(name = "MEMBRO_UK", columnNames = "usuario") })
public class Membro {

	@Id
	@Column(name = "usuario", length = 80, nullable = false)
	private String usuario;

	@Size(min = 6, max = 80)
	@Column(name = "senha", length = 80, nullable = false)
	private String senha;

	@Column(name = "nome", length = 120, nullable = false)
	private String nome;

	@Size(min = 6, max = 20)
	@Column(name = "tipo", length = 20, nullable = false)
	private String tipo;

	@Column(name = "habilitado")
	private boolean habilitado;

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

}
