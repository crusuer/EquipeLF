package br.com.estudiolf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Propriedade {

  @Id
  @Column
  private int id;
  @Column
  private String valor;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getValor() {
    return valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }
}
