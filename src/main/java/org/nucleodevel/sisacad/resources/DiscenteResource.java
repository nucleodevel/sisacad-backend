package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.dto.AulaDto;
import org.nucleodevel.sisacad.dto.DiscenteDto;
import org.nucleodevel.sisacad.dto.OfertaDisciplinaDto;
import org.nucleodevel.sisacad.dto.ParticipacaoAulaDto;
import org.nucleodevel.sisacad.dto.ParticipacaoAvaliacaoDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.AulaService;
import org.nucleodevel.sisacad.services.DiscenteService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
import org.nucleodevel.sisacad.services.ParticipacaoAulaService;
import org.nucleodevel.sisacad.services.ParticipacaoAvaliacaoService;
import org.nucleodevel.sisacad.services.VestibulandoService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/discente")
public class DiscenteResource extends AbstractResource<Discente, DiscenteDto, Integer, DiscenteService> {

	@Autowired
	private AulaService aulaService;
	@Autowired
	private OfertaDisciplinaService ofertaDisciplinaService;
	@Autowired
	private VestibulandoService vestibulandoService;

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.DISCENTE, Role.DOCENTE);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.GRADUACAO);
	}

	@Override
	public Discente mergeDtoIntoEntity(DiscenteDto dto, Discente entity) {
		String error = "";

		entity.setId(dto.getId());

		if (dto.getVestibulando() != null) {
			Vestibulando vestibulando = vestibulandoService.find(dto.getVestibulando());
			if (vestibulando == null) {
				error += "Vestibulando com ID " + entity.getVestibulando().getId() + " não existente; ";
			}
			entity.setVestibulando(vestibulando);
		} else {
			entity.setVestibulando(null);
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		return entity;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<DiscenteDto> find(@PathVariable Integer id) {
		return super.find(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<DiscenteDto> insert(@RequestBody DiscenteDto dto) {
		return super.insert(dto);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody DiscenteDto dto, @PathVariable Integer id) {
		return super.update(dto, id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		return super.delete(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<DiscenteDto>> findAll() {
		return super.findAll();
	}

	@RequestMapping(value = "/by-username/{username}", method = RequestMethod.GET)
	public ResponseEntity<List<DiscenteDto>> findByUsername(@PathVariable String username) {
		validatePermissionsToRead();

		if (username != null && !username.equals("")) {
			Discente entity = service.findByUsername(username);
			DiscenteDto dto = getDtoFromEntity(entity);

			List<DiscenteDto> listAllDto = new ArrayList<>();
			listAllDto.add(dto);

			return ResponseEntity.ok().body(listAllDto);
		}

		return super.findAll();
	}

	@RequestMapping(value = "/{id}/oferta-disciplina", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaDisciplinaDto>> findAllOfertaDisciplina(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();
		return findAllSubList(OfertaDisciplinaService.class, OfertaDisciplinaDto.class, id);
	}

	@RequestMapping(value = "/{id}/oferta-disciplina/{itemId}", method = RequestMethod.POST)
	public ResponseEntity<Void> insertOfertaDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToWrite();
		return insertSubList(id, itemId, ofertaDisciplinaService);
	}

	@RequestMapping(value = "/{id}/oferta-disciplina/{itemId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteOfertaDisciplina(@PathVariable Integer id, @PathVariable Integer itemId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToWrite();
		return deleteSubList(id, itemId, ofertaDisciplinaService);
	}

	@RequestMapping(value = "/{id}/participacao-aula", method = RequestMethod.GET)
	public ResponseEntity<List<ParticipacaoAulaDto>> findAllParticipacaoAula(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();
		return findAllSubList(ParticipacaoAulaService.class, ParticipacaoAulaDto.class, id);
	}

	@RequestMapping(value = "/{id}/participacao-avaliacao", method = RequestMethod.GET)
	public ResponseEntity<List<ParticipacaoAvaliacaoDto>> findAllParticipacaoAvaliacao(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();
		return findAllSubList(ParticipacaoAvaliacaoService.class, ParticipacaoAvaliacaoDto.class, id);
	}

	@RequestMapping(value = "/{id}/aula", method = RequestMethod.GET)
	public ResponseEntity<List<AulaDto>> findAllAula(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();

		Discente entity = service.find(id);

		List<Aula> listaAula = aulaService.findAllByDiscente(entity);
		Map<Integer, AulaDto> mapAulaDto = new TreeMap<>();

		for (Aula item : listaAula) {
			AulaDto dto = new AulaDto();
			dto.copyFromEntity(item);

			if (!mapAulaDto.containsKey(dto.getId())) {
				mapAulaDto.put(item.getId(), dto);
			}
		}

		return ResponseEntity.ok().body(new ArrayList<>(mapAulaDto.values()));
	}

}
