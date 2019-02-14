package br.com.estudiolf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Baile", //
		uniqueConstraints = { //
				@UniqueConstraint(name = "BAILE_UK", columnNames = { "usuario", "dia" }) })
public class Evento {

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "usuario", nullable = false)
	private Membro usuario;

	@Column(name = "dia", length = 10, nullable = false)
	private String dia;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Membro getUsuario() {
		return usuario;
	}

	public void setUsuario(Membro usuario) {
		this.usuario = usuario;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}
}
