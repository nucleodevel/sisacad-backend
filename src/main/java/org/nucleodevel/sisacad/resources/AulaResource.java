package org.nucleodevel.sisacad.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.domain.OfertaDisciplina;
import org.nucleodevel.sisacad.domain.ParticipacaoAula;
import org.nucleodevel.sisacad.domain.Usuario;
import org.nucleodevel.sisacad.dto.AulaDto;
import org.nucleodevel.sisacad.dto.DiscenteDto;
import org.nucleodevel.sisacad.dto.ParticipacaoAulaDto;
import org.nucleodevel.sisacad.security.Role;
import org.nucleodevel.sisacad.services.AulaService;
import org.nucleodevel.sisacad.services.DiscenteService;
import org.nucleodevel.sisacad.services.MailService;
import org.nucleodevel.sisacad.services.OfertaDisciplinaService;
import org.nucleodevel.sisacad.services.ParticipacaoAulaService;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/aula")
public class AulaResource extends AbstractResource<Aula, AulaDto, Integer, AulaService> {

	@Autowired
	private MailService mailService;

	@Autowired
	private DiscenteService discenteService;
	@Autowired
	private OfertaDisciplinaService ofertaDisciplinaService;

	@Override
	public List<Role> getListAllowedRoleToRead() {
		return List.of(Role.DISCENTE);
	}

	@Override
	public List<Role> getListAllowedRoleToWrite() {
		return List.of(Role.DOCENTE);
	}

	@Override
	public Aula mergeDtoIntoEntity(AulaDto dto, Aula entity) {
		String error = "";

		entity.setId(dto.getId());
		entity.setInicio(dto.getInicio() == null ? null : new Date(dto.getInicio()));
		entity.setTermino(dto.getTermino() == null ? null : new Date(dto.getTermino()));

		if (dto.getOfertaDisciplina() != null) {
			OfertaDisciplina ofertaDisciplina = ofertaDisciplinaService.find(dto.getOfertaDisciplina());
			if (ofertaDisciplina == null) {
				error += "OfertaDisciplina com ID " + entity.getOfertaDisciplina().getId() + " não existente; ";
			}
			entity.setOfertaDisciplina(ofertaDisciplina);
		} else {
			entity.setOfertaDisciplina(null);
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(AulaDto dto) {
		String error = "";

		if (dto.getInicio() == null) {
			error += "Data e hora de início pendente; ";
		}

		if (dto.getTermino() == null) {
			error += "Data e hora de término pendente; ";
		}

		if (dto.getInicio() != null && dto.getTermino() == null && dto.getInicio() > dto.getTermino()) {
			error += "Data e hora de início posterior à de término; ";
		}

		if (dto.getOfertaDisciplina() == null) {
			error += "Oferta de disciplina pendente; ";
		} else {
			try {
				ofertaDisciplinaService.find(dto.getOfertaDisciplina());
			} catch (ObjectNotFoundException e) {
				error += "Oferta de disciplina com ID " + dto.getOfertaDisciplina() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<AulaDto> find(@PathVariable Integer id) {
		return super.find(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<AulaDto> insert(@RequestBody AulaDto dto) {

		ResponseEntity<AulaDto> response = super.insert(dto);

		dto = response.getBody();

		Aula entity = getEntityFromDto(dto);
		OfertaDisciplina ofertaDisciplina = entity.getOfertaDisciplina();
		Docente docente = ofertaDisciplina.getDocente();
		Usuario usuario = docente.getUsuario();

		String mailText = "Aula criada com sucesso" + "\nDocente: " + docente.getUsuario().getNome()
				+ "\nOferta de disciplina: " + ofertaDisciplina.getCodigo() + " - "
				+ ofertaDisciplina.getDisciplina().getNome() + "\nInício: " + entity.getInicio() + "\nTérmino: "
				+ entity.getTermino();

		mailService.send(usuario.getEmail(), "Aula criada com sucesso", mailText);

		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody AulaDto dto, @PathVariable Integer id) {
		return super.update(dto, id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		return super.delete(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<AulaDto>> findAll() {
		return super.findAll();
	}

	@RequestMapping(value = "/{id}/participacao-aula", method = RequestMethod.GET)
	public ResponseEntity<List<ParticipacaoAulaDto>> findAllParticipacaoAula(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		validatePermissionsToRead();
		return findAllSubList(ParticipacaoAulaService.class, ParticipacaoAula.class, ParticipacaoAulaDto.class, id);
	}

	@RequestMapping(value = "/{id}/discente-participante", method = RequestMethod.GET)
	public ResponseEntity<List<DiscenteDto>> findAllDiscenteParticipante(@PathVariable Integer id)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		List<DiscenteDto> listDiscenteDto = new ArrayList<>();
		List<ParticipacaoAulaDto> listParticipacaoAulaDto = findAllParticipacaoAula(id).getBody();

		for (ParticipacaoAulaDto participacaoAulaDto : listParticipacaoAulaDto) {
			Discente discente = discenteService.find(participacaoAulaDto.getDiscente());
			DiscenteDto discenteDto = new DiscenteDto();
			discenteDto.copyFromEntity(discente);

			listDiscenteDto.add(discenteDto);
		}

		validatePermissionsToRead();
		return ResponseEntity.ok().body(listDiscenteDto);
	}

}
