package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.Disciplina;
import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.domain.OfertaDisciplina;
import org.nucleodevel.sisacad.dto.DisciplinaDto;
import org.nucleodevel.sisacad.dto.EstruturaCurricularDto;
import org.nucleodevel.sisacad.dto.OfertaDisciplinaDto;
import org.nucleodevel.sisacad.repositories.DisciplinaRepository;
import org.nucleodevel.sisacad.services.DisciplinaService;
import org.nucleodevel.sisacad.services.EstruturaCurricularService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/disciplina")
public class DisciplinaResource
		extends AbstractResource<Disciplina, Integer, DisciplinaDto, DisciplinaRepository, DisciplinaService> {

	@Autowired
	private EstruturaCurricularService estruturaCurricularService;

	@Override
	public Disciplina mergeDtoIntoEntity(DisciplinaDto dto, Disciplina entity) {
		entity.setId(dto.getId());
		entity.setNome(dto.getNome());

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(DisciplinaDto dto) {
		String error = "";

		if (dto.getNome() == null) {
			error += "Nome pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}
	}

	@RequestMapping(value = "/{id}/estrutura-curricular", method = RequestMethod.GET)
	public ResponseEntity<List<EstruturaCurricularDto>> findAllEstruturaCurricular(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(EstruturaCurricular.class, EstruturaCurricularDto.class, id, "getListaEstruturaCurricular");
	}

	@RequestMapping(value = "/{id}/estrutura-curricular/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> insertEstruturaCurricular(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return insertItem(EstruturaCurricular.class, EstruturaCurricularDto.class, id, "getListaEstruturaCurricular",
				estruturaCurricularService.find(itemId));
	}

	@RequestMapping(value = "/{id}/estrutura-curricular/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteEstruturaCurricular(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return deleteItem(EstruturaCurricular.class, EstruturaCurricularDto.class, id, "getListaEstruturaCurricular",
				estruturaCurricularService.find(itemId));
	}

	@RequestMapping(value = "/{id}/oferta-disciplina", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaDisciplinaDto>> findAllOfertaDisciplina(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(OfertaDisciplina.class, OfertaDisciplinaDto.class, id, "getListaOfertaDisciplina");
	}

}
