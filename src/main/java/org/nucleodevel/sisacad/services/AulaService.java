package org.nucleodevel.sisacad.services;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.repositories.AulaRepository;
import org.springframework.stereotype.Service;

@Service
public class AulaService extends AbstractService<Aula, Integer, AulaRepository> {

	@Override
	public void validadeForInsertUpdate(Aula entity) {
		// TODO Auto-generated method stub
		
	}

}
