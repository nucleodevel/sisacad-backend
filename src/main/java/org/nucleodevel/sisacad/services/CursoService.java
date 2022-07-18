package org.nucleodevel.sisacad.services;

import org.nucleodevel.sisacad.domain.Curso;
import org.nucleodevel.sisacad.repositories.CursoRepository;
import org.springframework.stereotype.Service;

@Service
public class CursoService extends AbstractService<Curso, Integer, CursoRepository> {

	@Override
	public void validadeForInsertUpdate(Curso entity) {
		// TODO Auto-generated method stub

	}

}
