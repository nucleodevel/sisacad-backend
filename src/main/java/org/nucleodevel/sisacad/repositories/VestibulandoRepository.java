package org.nucleodevel.sisacad.repositories;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VestibulandoRepository extends AbstractRepository<Vestibulando, Integer> {

	@Query(value = "SELECT x FROM Vestibulando x ORDER BY x.usuario.nome ASC")
	List<Vestibulando> findByOrderByNomeAsc();

	@Query(value = "SELECT x FROM Vestibulando x "
			+ "WHERE (?1 IS NULL OR x.id <> ?1) AND x.cpf = ?2 AND x.ofertaCurso = ?3")
	Optional<Vestibulando> findSimilarByCpfAndOfertaCurso(Integer id, String cpf, OfertaCurso ofertaCurso);

	@Query(value = "SELECT x FROM Vestibulando x WHERE x.usuario.username = ?1")
	Optional<Vestibulando> findByUsername(String username);

	@Query(value = "SELECT x FROM Vestibulando x LEFT JOIN x.avaliacaoVestibulando av "
			+ "WHERE av IS NOT NULL ORDER BY x.usuario.nome ASC")
	List<Vestibulando> findByAprovado();

	@Query(value = "SELECT x FROM Vestibulando x LEFT JOIN x.discente d WHERE d IS NULL ORDER BY x.usuario.nome ASC")
	List<Vestibulando> findByIsNotDiscente();

	@Query(value = "SELECT x FROM Vestibulando x LEFT JOIN x.avaliacaoVestibulando av LEFT JOIN x.discente d "
			+ "WHERE av IS NOT NULL AND d IS NULL ORDER BY x.usuario.nome ASC")
	List<Vestibulando> findByAprovadoAndIsNotDiscente();

}
