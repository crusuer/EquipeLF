package br.com.estudiolf.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.estudiolf.entity.Evento;
import br.com.estudiolf.entity.Membro;

public interface EventoRepository extends CrudRepository<Evento, Long> {

    @Query("SELECT count(b) FROM Evento b WHERE b.usuario = :usuario and b.dia LIKE :dia")
    public Integer countEventos(@Param("usuario") Membro usuario, @Param("dia") String dia);
}
