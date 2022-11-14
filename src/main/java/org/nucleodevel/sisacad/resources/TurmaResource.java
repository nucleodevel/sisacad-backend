package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.OfertaDisciplina;
import org.nucleodevel.sisacad.domain.Turma;
import org.nucleodevel.sisacad.dto.OfertaDisciplinaDto;
import org.nucleodevel.sisacad.dto.TurmaDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.DiscenteService;
import org.nucleodevel.sisacad.services.OfertaCursoService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
import org.nucleodevel.sisacad.services.TurmaService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/turma")
public class TurmaResource extends AbstractResource<Turma, TurmaDto, Integer, TurmaService> {

	@Autowired
	private OfertaCursoService ofertaCursoService;
	@Autowired
	private OfertaDisciplinaService ofertaDisciplinaService;
	@Autowired
	private DiscenteService discenteService;

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.DISCENTE, Role.DOCENTE);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.PEDAGOGICO);
	}

	@Override
	public Turma mergeDtoIntoEntity(TurmaDto dto, Turma entity) {
		String error = "";

		entity.setId(dto.getId());
		entity.setCodigo(dto.getCodigo());

		if (dto.getOfertaCurso() != null) {
			OfertaCurso ofertaCurso = ofertaCursoService.find(dto.getOfertaCurso());
			if (ofertaCurso == null) {
				error += "OfertaCurso com ID " + entity.getOfertaCurso().getId() + " não existente; ";
			}
			entity.setOfertaCurso(ofertaCurso);
		} else {
			entity.setOfertaCurso(null);
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		return entity;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<TurmaDto> find(@PathVariable Integer id) {
		return super.find(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TurmaDto> insert(@RequestBody TurmaDto dto) {
		return super.insert(dto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody TurmaDto dto, @PathVariable Integer id) {
		return super.update(dto, id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		return super.delete(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<TurmaDto>> findAll() {
		return super.findAll();
	}

	@RequestMapping(value = "/{id}/oferta-disciplina", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaDisciplinaDto>> findAllOfertaDisciplina(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();
		return findAllSubList(OfertaDisciplinaService.class, OfertaDisciplina.class, OfertaDisciplinaDto.class, id);
	}

	@RequestMapping(value = "/{id}/oferta-disciplina/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> insertOfertaDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToWrite();
		return insertSubList(id, itemId, ofertaDisciplinaService, OfertaDisciplina.class);
	}

	@RequestMapping(value = "/{id}/oferta-disciplina/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteOfertaDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToWrite();
		return deleteSubList(id, itemId, ofertaDisciplinaService, OfertaDisciplina.class);
	}

	@RequestMapping(value = "/oferta-curso/{ofertaCursoId}", method = RequestMethod.GET)
	public ResponseEntity<TurmaDto> findByOfertaCurso(@PathVariable Integer ofertaCursoId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();

		OfertaCurso ofertaCurso = ofertaCursoService.find(ofertaCursoId);
		Turma entity = ofertaCurso == null ? null : ofertaCurso.getTurma();

		TurmaDto dto = entity == null ? null : getDtoFromEntity(entity);
		return ResponseEntity.ok().body(dto);
	}

	@RequestMapping(value = "/discente/{discenteId}", method = RequestMethod.GET)
	public ResponseEntity<TurmaDto> findByDiscente(@PathVariable Integer discenteId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();

		Discente discente = discenteService.find(discenteId);
		Turma entity = discente == null ? null : discente.getVestibulando().getOfertaCurso().getTurma();

		TurmaDto dto = entity == null ? null : getDtoFromEntity(entity);
		return ResponseEntity.ok().body(dto);
	}

}
