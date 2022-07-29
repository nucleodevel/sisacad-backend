package org.nucleodevel.sisacad.repositories;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VestibulandoRepository extends AbstractRepository<Vestibulando, Integer> {

	@Query(value = "SELECT x FROM Vestibulando x "
			+ "WHERE (?1 IS NULL OR x.id <> ?1) AND x.ofertaCurso = ?2 AND x.nome = ?3")
	Optional<Vestibulando> findSimilarByOfertaCursoAndNome(Integer id, OfertaCurso ofertaCurso, String nome);

}
