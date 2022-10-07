package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.dto.AulaDto;
import org.nucleodevel.sisacad.dto.DiscenteDto;
import org.nucleodevel.sisacad.dto.DocenteDto;
import org.nucleodevel.sisacad.dto.OfertaDisciplinaDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.AulaService;
import org.nucleodevel.sisacad.services.DiscenteService;
import org.nucleodevel.sisacad.services.DocenteService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/docente")
public class DocenteResource extends AbstractResource<Docente, DocenteDto, Integer, DocenteService> {

	@Autowired
	private AulaService aulaService;
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
	public Docente mergeDtoIntoEntity(DocenteDto dto, Docente entity) {
		entity.setId(dto.getId());
		entity.setCpf(dto.getCpf());
		entity.setDataNascimento(dto.getDataNascimento() == null ? null : new Date(dto.getDataNascimento()));
		entity.setEndereco(dto.getEndereco());
		entity.setTelefones(dto.getTelefones());

		return entity;
	}

	@RequestMapping(value = "/by-username/{username}", method = RequestMethod.GET)
	public ResponseEntity<List<DocenteDto>> findByUsername(@PathVariable String username) {
		validatePermissionsToRead();

		if (username != null && !username.equals("")) {
			Docente entity = service.findByUsername(username);
			DocenteDto dto = getDtoFromEntity(entity);

			List<DocenteDto> listAllDto = new ArrayList<>();
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

	@RequestMapping(value = "/{id}/discente", method = RequestMethod.GET)
	public ResponseEntity<List<DiscenteDto>> findAllDiscente(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();

		Docente entity = service.find(id);

		List<Discente> listaDiscente = discenteService.findAllByDocente(entity);
		Map<Integer, DiscenteDto> mapDiscenteDto = new TreeMap<>();

		for (Discente item : listaDiscente) {
			DiscenteDto dto = new DiscenteDto();
			dto.copyFromEntity(item);

			if (!mapDiscenteDto.containsKey(dto.getId())) {
				mapDiscenteDto.put(item.getId(), dto);
			}
		}

		return ResponseEntity.ok().body(new ArrayList<>(mapDiscenteDto.values()));
	}

	@RequestMapping(value = "/{id}/aula", method = RequestMethod.GET)
	public ResponseEntity<List<AulaDto>> findAllAula(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();

		Docente entity = service.find(id);

		List<Aula> listaAula = aulaService.findAllByDocente(entity);
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
