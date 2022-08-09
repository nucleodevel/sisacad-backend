package org.nucleodevel.sisacad.repositories;

import java.util.List;

import org.nucleodevel.sisacad.domain.Avaliacao;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends AbstractRepository<Avaliacao, Integer> {

	List<Avaliacao> findByOrderByInicioDescTerminoDescDescricaoDesc();

}
