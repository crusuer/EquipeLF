package br.com.estudiolf.repository;

import br.com.estudiolf.entity.Propriedade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropriedadeRepository extends CrudRepository<Propriedade, Long> {

}
