package br.com.estudiolf.model;

public class Ponto {

	private int id;
	private String dia;
	private String inicio;
	private String fim;
	private Integer editado;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFim() {
		return fim;
	}

	public void setFim(String fim) {
		this.fim = fim;
	}

	public Integer getEditado() {
		return editado;
	}

	public void setEditado(Integer editado) {
		this.editado = editado;
	}

}
