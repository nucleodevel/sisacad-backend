package org.nucleodevel.sisacad.services;

import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.repositories.DocenteRepository;
import org.springframework.stereotype.Service;

@Service
public class DocenteService extends AbstractService<Docente, Integer, DocenteRepository> {

	@Override
	public void validadeForInsertUpdate(Docente entity) {
		// TODO Auto-generated method stub
		
	}

}
