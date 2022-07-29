package org.nucleodevel.sisacad.repositories;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Turma;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaRepository extends AbstractRepository<Turma, Integer> {

	@Query(value = "SELECT x FROM Turma x "
			+ "WHERE (?1 IS NULL OR x.id <> ?1) AND (x.codigo = ?2 OR x.ofertaCurso = ?3)")
	Optional<Turma> findSimilarByCodigoOuOfertaCurso(Integer id, String codigo, OfertaCurso ofertaCurso);

}
