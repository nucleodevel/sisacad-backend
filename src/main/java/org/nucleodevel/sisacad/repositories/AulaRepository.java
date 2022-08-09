package org.nucleodevel.sisacad.repositories;

import java.util.List;

import org.nucleodevel.sisacad.domain.Aula;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository extends AbstractRepository<Aula, Integer> {

	List<Aula> findByOrderByInicioDescTerminoDesc();

}
