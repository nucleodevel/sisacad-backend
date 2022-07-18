package org.nucleodevel.sisacad.services;

import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.repositories.VestibulandoRepository;
import org.springframework.stereotype.Service;

@Service
public class VestibulandoService extends AbstractService<Vestibulando, Integer, VestibulandoRepository> {

	@Override
	public void validadeForInsertUpdate(Vestibulando entity) {
		// TODO Auto-generated method stub

	}

}
