package org.nucleodevel.sisacad.services;

import org.nucleodevel.sisacad.domain.Disciplina;
import org.nucleodevel.sisacad.repositories.DisciplinaRepository;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService extends AbstractService<Disciplina, Integer, DisciplinaRepository> {

	@Override
	public void validadeForInsertUpdate(Disciplina entity) {
		// TODO Auto-generated method stub

	}

}
