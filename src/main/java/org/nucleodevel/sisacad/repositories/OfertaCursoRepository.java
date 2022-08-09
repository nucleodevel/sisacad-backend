package org.nucleodevel.sisacad.repositories;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibular;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaCursoRepository extends AbstractRepository<OfertaCurso, Integer> {

	@Query(value = "SELECT x FROM OfertaCurso x ORDER BY x.ano ASC, x.estruturaCurricular.curso.nome ASC")
	List<OfertaCurso> findByOrderByAnoDescCursoAsc();

	@Query(value = "SELECT x FROM OfertaCurso x WHERE (?1 IS NULL OR x.id <> ?1) "
			+ "AND (x.codigo = ?2 OR (x.estruturaCurricular = ?3 AND x.vestibular = ?4))")
	Optional<OfertaCurso> findSimilarByCodigoOrEstruturaCurricularAndVestibular(Integer id, String codigo,
			EstruturaCurricular estruturaCurricular, Vestibular vestibular);

}
