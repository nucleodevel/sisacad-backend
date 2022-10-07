package org.nucleodevel.sisacad.repositories;

import java.util.List;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.Docente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository extends AbstractRepository<Aula, Integer> {

	List<Aula> findByOrderByInicioDescTerminoDesc();

	@Query(value = "SELECT x FROM Aula x WHERE x.ofertaDisciplina.docente = ?1")
	List<Aula> findByDocente(Docente item);

	@Query(value = "SELECT x FROM Aula x LEFT JOIN x.listParticipacaoAula participacaoAula "
			+ "WHERE participacaoAula.discente = ?1")
	List<Aula> findByDiscente(Discente item);

}
