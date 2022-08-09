package org.nucleodevel.sisacad.repositories;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.OfertaDisciplina;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaDisciplinaRepository extends AbstractRepository<OfertaDisciplina, Integer> {

	@Query(value = "SELECT x FROM OfertaDisciplina x ORDER BY x.disciplina.nome ASC, x.docente.nome ASC")
	List<OfertaDisciplina> findByOrderByDisciplinaAscDocenteAsc();

	@Query(value = "SELECT x FROM OfertaDisciplina x WHERE (?1 IS NULL OR x.id <> ?1) AND x.codigo = ?2")
	Optional<OfertaDisciplina> findSimilarByCodigo(Integer id, String codigo);

}
