package org.nucleodevel.sisacad.repositories;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VestibulandoRepository extends AbstractRepository<Vestibulando, Integer> {

	@Query(value = "SELECT x FROM Vestibulando x "
			+ "WHERE (?1 IS NULL OR x.id <> ?1) AND x.cpf = ?2 AND x.ofertaCurso = ?3")
	Optional<Vestibulando> findSimilarByCpfAndOfertaCurso(Integer id, String cpf, OfertaCurso ofertaCurso);

}
