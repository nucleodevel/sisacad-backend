package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.nucleodevel.sisacad.domain.AvaliacaoVestibulando;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.dto.AvaliacaoVestibulandoDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.AvaliacaoVestibulandoService;
import org.nucleodevel.sisacad.services.VestibulandoService;
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
@RequestMapping(value = "/avaliacao-vestibulando")
public class AvaliacaoVestibulandoResource extends
		AbstractResource<AvaliacaoVestibulando, AvaliacaoVestibulandoDto, Integer, AvaliacaoVestibulandoService> {

	@Autowired
	private VestibulandoService vestibulandoService;

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.DISCENTE, Role.VESTIBULANDO);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.GRADUACAO, Role.PEDAGOGICO);
	}

	@Override
	public AvaliacaoVestibulando mergeDtoIntoEntity(AvaliacaoVestibulandoDto dto, AvaliacaoVestibulando entity) {
		String error = "";

		entity.setId(dto.getId());
		entity.setConceitoBiologicas(dto.getConceitoBiologicas());
		entity.setConceitoExatas(dto.getConceitoExatas());
		entity.setConceitoHumanas(dto.getConceitoHumanas());
		entity.setConceitoFinal(dto.getConceitoFinal());

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

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		return entity;
	}

	@RequestMapping(value = "/vestibulando/{vestibulandoId}", method = RequestMethod.GET)
	public ResponseEntity<AvaliacaoVestibulandoDto> findByVestibulando(@PathVariable Integer vestibulandoId)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();

		Vestibulando vestibulando = vestibulandoService.find(vestibulandoId);
		AvaliacaoVestibulandoDto dto = getDtoFromEntity(service.findByVestibulando(vestibulando));

		return ResponseEntity.ok().body(dto);
	}

}
