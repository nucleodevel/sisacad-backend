package org.nucleodevel.sisacad.repositories;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EstruturaCurricularRepository extends AbstractRepository<EstruturaCurricular, Integer> {

	List<EstruturaCurricular> findByOrderByAnoInicioDescAnoTerminoDesc();

	@Query(value = "SELECT x FROM EstruturaCurricular x WHERE (?1 IS NULL OR x.id <> ?1) AND x.codigo = ?2")
	Optional<EstruturaCurricular> findSimilarByCodigo(Integer id, String codigo);

}
