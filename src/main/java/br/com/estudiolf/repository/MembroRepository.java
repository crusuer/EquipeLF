package br.com.estudiolf.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.estudiolf.entity.Membro;

public interface MembroRepository extends CrudRepository<Membro, Long>{
	Membro findByUsuario(String usuario);
	Iterable<Membro> findByTipoAndHabilitado(String tipo, boolean habilitado);
	
	@Query("SELECT m FROM Membro m WHERE m.usuario LIKE :usuario")
	public Optional<Membro> findByUsuarioLike(@Param("usuario") String usuario);
}
