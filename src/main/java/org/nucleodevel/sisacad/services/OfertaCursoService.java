package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibular;
import org.nucleodevel.sisacad.dto.OfertaCursoDto;
import org.nucleodevel.sisacad.repositories.OfertaCursoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfertaCursoService extends AbstractService<OfertaCurso, Integer, OfertaCursoDto, OfertaCursoRepository> {

	@Autowired
	private EstruturaCurricularService estruturaCurricularService;
	@Autowired
	private VestibularService vestibularService;

	@Override
	public void validadeForInsertUpdate(OfertaCurso entity) {
		String error = "";

		if (entity.getAno() == null) {
			error += "Ano pendente; ";
		}

		if (entity.getEstruturaCurricular() == null) {
			error += "Estrutura curricular pendente; ";
		}

		if (entity.getVestibular() == null) {
			error += "Vestibular pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		EstruturaCurricular myEstruturaCurricular = entity.getEstruturaCurricular();
		Vestibular myVestibular = entity.getVestibular();

		Optional<OfertaCurso> similar = repo.findDifferentByEstruturaCurricularAndVestibular(entity.getId(),
				myEstruturaCurricular, myVestibular);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException(
					"Já existe uma oferta de curso nesse vestibular para essa estrutura curricular!");
		});
	}

	@Override
	public void validadeForInsertUpdate(OfertaCursoDto dto) {
		String error = "";

		if (dto.getAno() == null) {
			error += "Ano pendente; ";
		}

		if (dto.getEstruturaCurricular() == null) {
			error += "Estrutura curricular pendente; ";
		} else {
			try {
				estruturaCurricularService.find(dto.getEstruturaCurricular());
			} catch (ObjectNotFoundException e) {
				error += "Estrutura curricular com ID " + dto.getEstruturaCurricular() + " não existente; ";
			}
		}

		if (dto.getVestibular() == null) {
			error += "Vestibular pendente; ";
		} else {
			try {
				vestibularService.find(dto.getVestibular());
			} catch (ObjectNotFoundException e) {
				error += "Vestibular com ID " + dto.getVestibular() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}
	}

}
