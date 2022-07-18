package org.nucleodevel.sisacad.services;

import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.repositories.EstruturaCurricularRepository;
import org.springframework.stereotype.Service;

@Service
public class EstruturaCurricularService
		extends AbstractService<EstruturaCurricular, Integer, EstruturaCurricularRepository> {

	@Override
	public void validadeForInsertUpdate(EstruturaCurricular entity) {
		// TODO Auto-generated method stub

	}

}
