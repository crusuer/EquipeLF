package br.com.estudiolf.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.estudiolf.entity.Membro;

public interface MembroRepository extends CrudRepository<Membro, Long>{
	Membro findByUsuario(String usuario);
	List<Membro> findByTipoAndHabilitado(String tipo, boolean habilitado);
	
	@Query("SELECT m FROM Membro m WHERE m.usuario LIKE :usuario")
	public Optional<Membro> findByNomeLike(@Param("usuario") String usuario);
	
	@Query("SELECT m FROM Baile b RIGHT JOIN b.usuario m with b.dia=:dia WHERE b.usuario is NULL AND m.tipo = 'ROLE_USER' AND m.habilitado=true")
	public Iterable<Membro> findByDiaBaile(@Param("dia") String dia);
	
	@Query("SELECT m from Baile b INNER JOIN b.usuario m WHERE b.dia=:dia AND m.tipo = 'ROLE_USER' AND m.habilitado=true")
	public Iterable<Membro> findByPresenteBaile(@Param("dia") String dia);
}
