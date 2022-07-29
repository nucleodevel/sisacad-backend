package org.nucleodevel.sisacad.repositories;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.Docente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends AbstractRepository<Docente, Integer> {

	@Query(value = "SELECT x FROM Docente x WHERE (?1 IS NULL OR x.id <> ?1) AND x.nome = ?2")
	Optional<Docente> findSimilarByNome(Integer id, String nome);

}
