package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.domain.Avaliacao;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.OfertaDisciplina;
import org.nucleodevel.sisacad.domain.Turma;
import org.nucleodevel.sisacad.dto.AulaDto;
import org.nucleodevel.sisacad.dto.AvaliacaoDto;
import org.nucleodevel.sisacad.dto.DiscenteDto;
import org.nucleodevel.sisacad.dto.OfertaDisciplinaDto;
import org.nucleodevel.sisacad.dto.TurmaDto;
import org.nucleodevel.sisacad.repositories.OfertaDisciplinaRepository;
import org.nucleodevel.sisacad.services.DiscenteService;
import org.nucleodevel.sisacad.services.DisciplinaService;
import org.nucleodevel.sisacad.services.DocenteService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
import org.nucleodevel.sisacad.services.TurmaService;
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
@RequestMapping(value = "/oferta-disciplina")
public class OfertaDisciplinaResource extends
		AbstractResource<OfertaDisciplina, Integer, OfertaDisciplinaDto, OfertaDisciplinaRepository, OfertaDisciplinaService> {

	@Autowired
	private DisciplinaService disciplinaService;
	@Autowired
	private DocenteService docenteService;
	@Autowired
	private DiscenteService discenteService;
	@Autowired
	private TurmaService turmaService;

	@Override
	public OfertaDisciplina mergeDtoIntoEntity(OfertaDisciplinaDto dto, OfertaDisciplina entity) {
		entity.setId(dto.getId());
		entity.setDisciplina(disciplinaService.find(dto.getDisciplina()));
		entity.setDocente(docenteService.find(dto.getDocente()));

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(OfertaDisciplinaDto dto) {
		String error = "";

		if (dto.getDisciplina() == null) {
			error += "Disciplina pendente; ";
		} else {
			try {
				disciplinaService.find(dto.getDisciplina());
			} catch (ObjectNotFoundException e) {
				error += "Disciplina com ID " + dto.getDisciplina() + " não existente; ";
			}
		}

		if (dto.getDocente() == null) {
			error += "Docente pendente; ";
		} else {
			try {
				docenteService.find(dto.getDocente());
			} catch (ObjectNotFoundException e) {
				error += "Docente com ID " + dto.getDocente() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}
	}

	@RequestMapping(value = "/{id}/aula", method = RequestMethod.GET)
	public ResponseEntity<List<AulaDto>> findAllAula(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(Aula.class, AulaDto.class, id, "getListaAula");
	}

	@RequestMapping(value = "/{id}/avaliacao", method = RequestMethod.GET)
	public ResponseEntity<List<AvaliacaoDto>> findAllAvaliacao(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(Avaliacao.class, AvaliacaoDto.class, id, "getListaAvaliacao");
	}

	@RequestMapping(value = "/{id}/discente", method = RequestMethod.GET)
	public ResponseEntity<List<DiscenteDto>> findAllDiscente(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(Discente.class, DiscenteDto.class, id, "getListaDiscente");
	}

	@RequestMapping(value = "/{id}/discente/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> insertDiscente(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return insertItem(Discente.class, DiscenteDto.class, id, "getListaDiscente", discenteService.find(itemId));
	}

	@RequestMapping(value = "/{id}/discente/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteDiscente(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return deleteItem(Discente.class, DiscenteDto.class, id, "getListaDiscente", discenteService.find(itemId));
	}

	@RequestMapping(value = "/{id}/turma", method = RequestMethod.GET)
	public ResponseEntity<List<TurmaDto>> findAllTurma(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return findAllItem(Turma.class, TurmaDto.class, id, "getListaTurma");
	}

	@RequestMapping(value = "/{id}/turma/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> insertTurma(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return insertItem(Turma.class, TurmaDto.class, id, "getListaTurma", turmaService.find(itemId));
	}

	@RequestMapping(value = "/{id}/turma/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteTurma(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		return deleteItem(Turma.class, TurmaDto.class, id, "getListaTurma", turmaService.find(itemId));
	}

}
