package org.nucleodevel.sisacad.services;

import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.dto.EstruturaCurricularDto;
import org.nucleodevel.sisacad.repositories.EstruturaCurricularRepository;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstruturaCurricularService
		extends AbstractService<EstruturaCurricular, Integer, EstruturaCurricularDto, EstruturaCurricularRepository> {

	@Autowired
	private CursoService cursoService;

	@Override
	public EstruturaCurricular mergeDtoIntoEntity(EstruturaCurricularDto dto, EstruturaCurricular entity) {
		entity.setId(dto.getId());
		entity.setAnoInicio(dto.getAnoInicio());
		entity.setAnoTermino(dto.getAnoTermino());
		entity.setCurso(cursoService.find(dto.getCurso()));

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(EstruturaCurricularDto dto) {
		String error = "";

		if (dto.getAnoInicio() == null) {
			error += "Ano de início pendente; ";
		}

		if (dto.getAnoTermino() == null) {
			error += "Ano de término pendente; ";
		}

		if (dto.getAnoInicio() > dto.getAnoTermino()) {
			error += "Ano de início posterior ao de término; ";
		}

		if (dto.getCurso() == null) {
			error += "Curso pendente; ";
		} else {
			try {
				cursoService.find(dto.getCurso());
			} catch (ObjectNotFoundException e) {
				error += "Curso com ID " + dto.getCurso() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}
	}

}
