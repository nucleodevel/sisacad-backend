package org.nucleodevel.sisacad.repositories;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends AbstractRepository<Usuario, Integer> {

	Usuario findByUsername(String username);

	List<Usuario> findByOrderByUsernameAsc();

	@Query(value = "SELECT x FROM Usuario x WHERE (?1 IS NULL OR x.id <> ?1) AND x.username = ?2")
	Optional<Usuario> findSimilarByUsername(Integer id, String username);

}
