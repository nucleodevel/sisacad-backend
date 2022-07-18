package org.nucleodevel.sisacad.repositories;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.AvaliacaoVestibulando;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoVestibulandoRepository extends AbstractRepository<AvaliacaoVestibulando, Integer> {

	@Query(value = "SELECT x FROM AvaliacaoVestibulando x WHERE (?1 IS NULL OR x.id <> ?1) AND x.vestibulando = ?2")
	Optional<AvaliacaoVestibulando> findDifferentByVestibulando(Integer id, Vestibulando vestibulando);

}
