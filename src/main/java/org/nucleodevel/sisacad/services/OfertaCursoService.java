package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibular;
import org.nucleodevel.sisacad.repositories.OfertaCursoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.springframework.stereotype.Service;

@Service
public class OfertaCursoService extends AbstractService<OfertaCurso, Integer, OfertaCursoRepository> {

	@Override
	public void validadeForInsertUpdate(OfertaCurso entity) {
		EstruturaCurricular myEstruturaCurricular = entity.getEstruturaCurricular();
		Vestibular myVestibular = entity.getVestibular();

		Optional<OfertaCurso> similar = repo.findDifferentByEstruturaCurricularAndVestibular(entity.getId(),
				myEstruturaCurricular, myVestibular);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException(
					"Já existe uma oferta de curso nesse vestibular para essa estrutura curricular!");
		});
	}

}
