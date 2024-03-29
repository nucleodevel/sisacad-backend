package org.nucleodevel.sisacad.repositories;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Docente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends AbstractRepository<Docente, Integer> {

	@Query(value = "SELECT x FROM Docente x ORDER BY x.usuario.nome ASC")
	List<Docente> findByOrderByNomeAsc();

	@Query(value = "SELECT x FROM Docente x WHERE (?1 IS NULL OR x.id <> ?1) AND x.cpf = ?2")
	Optional<Docente> findSimilarByCpf(Integer id, String cpf);

	@Query(value = "SELECT x FROM Docente x WHERE x.usuario.username = ?1")
	Optional<Docente> findByUsername(String username);

}
