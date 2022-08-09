package org.nucleodevel.sisacad.repositories;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.AvaliacaoVestibulando;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoVestibulandoRepository extends AbstractRepository<AvaliacaoVestibulando, Integer> {

	@Query(value = "SELECT x FROM AvaliacaoVestibulando x ORDER BY x.vestibulando.nome ASC")
	List<AvaliacaoVestibulando> findByOrderByVestibulandoAsc();

	@Query(value = "SELECT x FROM AvaliacaoVestibulando x WHERE (?1 IS NULL OR x.id <> ?1) AND x.vestibulando = ?2")
	Optional<AvaliacaoVestibulando> findSimilarByVestibulando(Integer id, Vestibulando vestibulando);

	@Query(value = "SELECT x FROM AvaliacaoVestibulando x WHERE x.vestibulando = ?1")
	Optional<AvaliacaoVestibulando> findByVestibulando(Vestibulando vestibulando);

}
