package org.nucleodevel.sisacad.repositories;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Curso;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends AbstractRepository<Curso, Integer> {

	List<Curso> findByOrderByNomeAsc();

	@Query(value = "SELECT x FROM Curso x WHERE (?1 IS NULL OR x.id <> ?1) AND x.codigo = ?2")
	Optional<Curso> findSimilarByCodigo(Integer id, String codigo);

}
