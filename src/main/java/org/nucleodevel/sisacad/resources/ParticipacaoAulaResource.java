package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.ParticipacaoAula;
import org.nucleodevel.sisacad.dto.ParticipacaoAulaDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.AulaService;
import org.nucleodevel.sisacad.services.DiscenteService;
import org.nucleodevel.sisacad.services.ParticipacaoAulaService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/participacao-aula")
public class ParticipacaoAulaResource
		extends AbstractResource<ParticipacaoAula, ParticipacaoAulaDto, Integer, ParticipacaoAulaService> {

	@Autowired
	private AulaService aulaService;
	@Autowired
	private DiscenteService discenteService;

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.DISCENTE);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.DOCENTE);
	}

	@Override
	public ParticipacaoAula mergeDtoIntoEntity(ParticipacaoAulaDto dto, ParticipacaoAula entity) {
		String error = "";

		entity.setId(dto.getId());

		if (dto.getAula() != null) {
			Aula aula = aulaService.find(dto.getAula());
			if (aula == null) {
				error += "Aula com ID " + entity.getAula().getId() + " não existente; ";
			}
			entity.setAula(aula);
		} else {
			entity.setAula(null);
		}

		if (dto.getDiscente() != null) {
			Discente discente = discenteService.find(dto.getDiscente());
			if (discente == null) {
				error += "Discente com ID " + entity.getDiscente().getId() + " não existente; ";
			}
			entity.setDiscente(discente);
		} else {
			entity.setDiscente(null);
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		return entity;
	}

	@RequestMapping(value = "/aula/{aulaId}/discente/{discenteId}", method = RequestMethod.GET)
	public ResponseEntity<ParticipacaoAulaDto> findByAulaAndDiscente(@PathVariable Integer aulaId,
			@PathVariable Integer discenteId) throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();

		Aula aula = aulaService.find(aulaId);
		Discente discente = discenteService.find(discenteId);

		ParticipacaoAulaDto dto = getDtoFromEntity(service.findByAulaAndDiscente(aula, discente));
		return ResponseEntity.ok().body(dto);
	}

}
