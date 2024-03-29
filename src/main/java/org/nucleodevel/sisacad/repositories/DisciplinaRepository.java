package org.nucleodevel.sisacad.repositories;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Disciplina;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends AbstractRepository<Disciplina, Integer> {

	List<Disciplina> findByOrderByNomeAsc();

	@Query(value = "SELECT x FROM Disciplina x WHERE (?1 IS NULL OR x.id <> ?1) AND x.codigo = ?2")
	Optional<Disciplina> findSimilarByCodigo(Integer id, String codigo);

	@Query(value = "SELECT x FROM Disciplina x WHERE (?1 IS NULL OR x.id <> ?1) AND x.nome = ?2")
	Optional<Disciplina> findSimilarByNome(Integer id, String nome);

}
