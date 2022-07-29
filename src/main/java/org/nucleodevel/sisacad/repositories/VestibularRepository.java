package org.nucleodevel.sisacad.repositories;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.Vestibular;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VestibularRepository extends AbstractRepository<Vestibular, Integer> {

	@Query(value = "SELECT x FROM Vestibular x WHERE (?1 IS NULL OR x.id <> ?1) AND x.ano = ?2")
	Optional<Vestibular> findSimilarByAno(Integer id, Integer ano);

}
