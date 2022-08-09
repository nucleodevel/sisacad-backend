package org.nucleodevel.sisacad.repositories;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Avaliacao;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.ParticipacaoAvaliacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipacaoAvaliacaoRepository extends AbstractRepository<ParticipacaoAvaliacao, Integer> {

	@Query(value = "SELECT x FROM ParticipacaoAvaliacao x "
			+ "ORDER BY x.avaliacao.inicio DESC, x.avaliacao.termino DESC, x.avaliacao.descricao DESC, "
			+ "x.discente.vestibulando.nome ASC")
	List<ParticipacaoAvaliacao> findByOrderByAvaliacaoDescDiscenteAsc();

	@Query(value = "SELECT x FROM ParticipacaoAvaliacao x "
			+ "WHERE (?1 IS NULL OR x.id <> ?1) AND x.avaliacao = ?2 AND x.discente = ?3")
	Optional<ParticipacaoAvaliacao> findSimilarByAvaliacaoAndDiscente(Integer id, Avaliacao avaliacao,
			Discente discente);

}
