package org.nucleodevel.sisacad.services;

import java.util.List;
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
	public OfertaCurso mergeDtoIntoEntity(OfertaCursoDto dto, OfertaCurso entity) {
		entity.setId(dto.getId());
		entity.setCodigo(dto.getCodigo());
		entity.setAno(dto.getAno());
		entity.setEstruturaCurricular(estruturaCurricularService.find(dto.getEstruturaCurricular()));
		entity.setVestibular(vestibularService.find(dto.getVestibular()));

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(OfertaCursoDto dto) {
		String error = "";

		if (dto.getCodigo() == null) {
			error += "Código pendente; ";
		}

		if (dto.getAno() == null) {
			error += "Ano pendente; ";
		}

		EstruturaCurricular myEstruturaCurricular = null;
		if (dto.getEstruturaCurricular() == null) {
			error += "Estrutura curricular pendente; ";
		} else {
			try {
				myEstruturaCurricular = estruturaCurricularService.find(dto.getEstruturaCurricular());
			} catch (ObjectNotFoundException e) {
				error += "Estrutura curricular com ID " + dto.getEstruturaCurricular() + " não existente; ";
			}
		}

		Vestibular myVestibular = null;
		if (dto.getVestibular() == null) {
			error += "Vestibular pendente; ";
		} else {
			try {
				myVestibular = vestibularService.find(dto.getVestibular());
			} catch (ObjectNotFoundException e) {
				error += "Vestibular com ID " + dto.getVestibular() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}

		String myCodigo = dto.getCodigo();

		Optional<OfertaCurso> similar = repository.findSimilarByCodigoOrEstruturaCurricularAndVestibular(dto.getId(),
				myCodigo, myEstruturaCurricular, myVestibular);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException(
					"Já existe uma oferta de curso com esse código ou nesse vestibular para essa estrutura curricular!");
		});
	}

	@Override
	protected List<OfertaCurso> findAll() {
		return repository.findByOrderByAnoDescCursoAsc();
	}

}
