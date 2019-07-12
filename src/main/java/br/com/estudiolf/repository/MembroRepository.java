package br.com.estudiolf.repository;

import br.com.estudiolf.entity.Membro;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MembroRepository extends CrudRepository<Membro, Long> {

  Membro findByUsuario(String usuario);

  List<Membro> findByTipoAndHabilitado(String tipo, boolean habilitado);

  @Query("SELECT m FROM Membro m WHERE m.nome LIKE :nome AND m.habilitado = true")
  List<Membro> findByNomeLike(@Param("nome") String nome);

  @Query("SELECT m FROM Evento b RIGHT JOIN b.usuario m with b.dia=:dia WHERE b.usuario is NULL AND m.tipo = 'ROLE_USER' AND m.habilitado=true")
  Iterable<Membro> findByDiaEvento(@Param("dia") String dia);

  @Query("SELECT m from Evento b INNER JOIN b.usuario m WHERE b.dia=:dia AND m.tipo = 'ROLE_USER' AND m.habilitado=true ORDER BY m.nome")
  Iterable<Membro> findByPresenteEvento(@Param("dia") String dia);
}
