package br.com.estudiolf.repository;

import br.com.estudiolf.entity.Membro;
import br.com.estudiolf.entity.Ponto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PontoRepository extends CrudRepository<Ponto, Long> {

  @Query("SELECT p FROM Ponto p JOIN p.usuario m WHERE p.usuario = :usuario AND p.dia LIKE :dia ORDER BY p.id")
  Iterable<Ponto> findByUsuarioAndDia(@Param("usuario") Membro usuario,
      @Param("dia") String dia);
}
