package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.Disciplina;
import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.domain.OfertaDisciplina;
import org.nucleodevel.sisacad.dto.AulaDto;
import org.nucleodevel.sisacad.dto.AvaliacaoDto;
import org.nucleodevel.sisacad.dto.DiscenteDto;
import org.nucleodevel.sisacad.dto.OfertaDisciplinaDto;
import org.nucleodevel.sisacad.dto.TurmaDto;
import org.nucleodevel.sisacad.services.AulaService;
import org.nucleodevel.sisacad.services.AvaliacaoService;
import org.nucleodevel.sisacad.services.DiscenteService;
import org.nucleodevel.sisacad.services.DisciplinaService;
import org.nucleodevel.sisacad.services.DocenteService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
import org.nucleodevel.sisacad.services.TurmaService;
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
@RequestMapping(value = "/oferta-disciplina")
public class OfertaDisciplinaResource
		extends AbstractResource<OfertaDisciplina, OfertaDisciplinaDto, Integer, OfertaDisciplinaService> {

	@Autowired
	private DiscenteService discenteService;
	@Autowired
	private DisciplinaService disciplinaService;
	@Autowired
	private DocenteService docenteService;
	@Autowired
	private TurmaService turmaService;

	@Override
	public OfertaDisciplina mergeDtoIntoEntity(OfertaDisciplinaDto dto, OfertaDisciplina entity) {
		String error = "";

		entity.setId(dto.getId());
		entity.setCodigo(dto.getCodigo());

		if (dto.getDisciplina() != null) {
			Disciplina disciplina = disciplinaService.find(dto.getDisciplina());
			if (disciplina == null) {
				error += "Disciplina com ID " + entity.getDisciplina().getId() + " não existente; ";
			}
			entity.setDisciplina(disciplina);
		} else {
			entity.setDisciplina(null);
		}

		if (dto.getDocente() != null) {
			Docente docente = docenteService.find(dto.getDocente());
			if (docente == null) {
				error += "Docente com ID " + entity.getDocente().getId() + " não existente; ";
			}
			entity.setDocente(docente);
		} else {
			entity.setDocente(null);
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		return entity;
	}

	@RequestMapping(value = "/{id}/aula", method = RequestMethod.GET)
	public ResponseEntity<List<AulaDto>> findAllAula(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllSubList(AulaService.class, AulaDto.class, id);
	}

	@RequestMapping(value = "/{id}/avaliacao", method = RequestMethod.GET)
	public ResponseEntity<List<AvaliacaoDto>> findAllAvaliacao(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllSubList(AvaliacaoService.class, AvaliacaoDto.class, id);
	}

	@RequestMapping(value = "/{id}/discente", method = RequestMethod.GET)
	public ResponseEntity<List<DiscenteDto>> findAllDiscente(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllSubList(DiscenteService.class, DiscenteDto.class, id);
	}

	@RequestMapping(value = "/{id}/discente/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> insertDiscente(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return insertSubList(id, itemId, discenteService);
	}

	@RequestMapping(value = "/{id}/discente/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteDiscente(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return deleteSubList(id, itemId, discenteService);
	}

	@RequestMapping(value = "/{id}/turma", method = RequestMethod.GET)
	public ResponseEntity<List<TurmaDto>> findAllTurma(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllSubList(TurmaService.class, TurmaDto.class, id);
	}

	@RequestMapping(value = "/{id}/turma/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> insertTurma(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return insertSubList(id, itemId, turmaService);
	}

	@RequestMapping(value = "/{id}/turma/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteTurma(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return deleteSubList(id, itemId, turmaService);
	}

}
