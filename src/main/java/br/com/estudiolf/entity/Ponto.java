package br.com.estudiolf.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Ponto", //
uniqueConstraints = { //
		@UniqueConstraint(name = "PONTO_UK", columnNames = {"usuario", "dia"}) })
public class Ponto {

	@Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "usuario", nullable = false)
	private Membro usuario;
	
	@Column(name = "dia", length = 10, nullable = false)
	private String dia;
	
	@Column(name = "inicio", length = 8)
	private String inicio;
	
	@Column(name = "fim", length = 10)
	private String fim;
	
	@Column(name = "inicioP", length = 8)
	private String inicioP;
	
	@Column(name = "fimP", length = 10)
	private String fimP;
	
	private String total;

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
	
	public String getInicioP() {
		return inicioP;
	}

	public void setInicioP(String inicioP) {
		this.inicioP = inicioP;
	}

	public String getFimP() {
		return fimP;
	}

	public void setFimP(String fimP) {
		this.fimP = fimP;
	}

	public String getTotal() throws ParseException {
		return total;
	}

	public void setTotal() throws ParseException {
		this.total = "";
		if (fim != null) {

			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			Date date1 = format.parse(inicio);
			Date date2 = format.parse(fim);
			long intervalo = 0;
			if(inicioP != null && fimP != null) {
				Date date3 = format.parse(inicioP);
				Date date4 = format.parse(fimP);
				intervalo = date4.getTime() - date3.getTime();
			}
			long diff = date2.getTime() - date1.getTime() - intervalo;
			diff = diff / 1000;

			long hours = (diff / 3600);
			long mins = (diff / 60) % 60;
			String minsString = (mins == 0) ? "00" : ((mins < 10) ? "0" + mins : "" + mins);
			this.total = hours + ":" + minsString;
		}
	}

}
