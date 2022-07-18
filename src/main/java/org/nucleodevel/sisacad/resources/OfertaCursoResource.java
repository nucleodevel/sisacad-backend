package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.dto.OfertaCursoDto;
import org.nucleodevel.sisacad.dto.VestibulandoDto;
import org.nucleodevel.sisacad.repositories.OfertaCursoRepository;
import org.nucleodevel.sisacad.services.EstruturaCurricularService;
import org.nucleodevel.sisacad.services.OfertaCursoService;
import org.nucleodevel.sisacad.services.VestibularService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/oferta-curso")
public class OfertaCursoResource
		extends AbstractResource<OfertaCurso, Integer, OfertaCursoDto, OfertaCursoRepository, OfertaCursoService> {

	@Autowired
	private EstruturaCurricularService estruturaCurricularService;
	@Autowired
	private VestibularService vestibularService;

	@Override
	public OfertaCurso mergeDtoIntoEntity(OfertaCursoDto dto, OfertaCurso entity) {
		entity.setId(dto.getId());
		entity.setAno(dto.getAno());
		entity.setEstruturaCurricular(estruturaCurricularService.find(dto.getEstruturaCurricular()));
		entity.setVestibular(vestibularService.find(dto.getVestibular()));

		return entity;
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

	@RequestMapping(value = "/{id}/vestibulando", method = RequestMethod.GET)
	public ResponseEntity<List<VestibulandoDto>> findAllVestibulando(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(Vestibulando.class, VestibulandoDto.class, id, "getListaVestibulando");
	}

}
