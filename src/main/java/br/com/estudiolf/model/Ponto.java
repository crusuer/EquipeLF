package br.com.estudiolf.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ponto {

	private int id;
	private String dia;
	private String inicio;
	private String fim;
	private Integer editado;
	private String total;

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

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) throws ParseException {
		if (fim.isEmpty()) {

			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			Date date1 = format.parse(inicio);
			Date date2 = format.parse(fim);
			long diff = date2.getTime() - date1.getTime();
			diff = diff / 1000;

			long hours = (diff / 3600);
			long mins = (diff / 60) % 60;
			String minsString = (mins == 0) ? "00" : ((mins < 10) ? "0" + mins : "" + mins);
			this.total = hours + ":" + minsString;
		}
		this.total = "";
	}

}
