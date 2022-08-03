package org.nucleodevel.sisacad.repositories;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.ParticipacaoAula;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipacaoAulaRepository extends AbstractRepository<ParticipacaoAula, Integer> {

	@Query(value = "SELECT x FROM ParticipacaoAula x "
			+ "WHERE (?1 IS NULL OR x.id <> ?1) AND x.aula = ?2 AND x.discente = ?3")
	Optional<ParticipacaoAula> findSimilarByAulaAndDiscente(Integer id, Aula aula, Discente discente);

	@Query(value = "SELECT x FROM ParticipacaoAula x WHERE x.aula = ?1 AND x.discente = ?2")
	Optional<ParticipacaoAula> findByAulaAndDiscente(Aula aula, Discente discente);

}
