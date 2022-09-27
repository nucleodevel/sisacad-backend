package org.nucleodevel.sisacad.repositories;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscenteRepository extends AbstractRepository<Discente, Integer> {

	@Query(value = "SELECT x FROM Discente x ORDER BY x.vestibulando.usuario.nome ASC")
	List<Discente> findByOrderByVestibulandoAsc();

	@Query(value = "SELECT x FROM Discente x WHERE (?1 IS NULL OR x.id <> ?1) AND x.vestibulando = ?2")
	Optional<Discente> findSimilarByVestibulando(Integer id, Vestibulando item);

}
