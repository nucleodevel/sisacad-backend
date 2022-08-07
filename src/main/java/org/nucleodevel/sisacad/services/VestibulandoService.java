package org.nucleodevel.sisacad.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.dto.VestibulandoDto;
import org.nucleodevel.sisacad.repositories.VestibulandoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VestibulandoService
		extends AbstractService<Vestibulando, Integer, VestibulandoDto, VestibulandoRepository> {

	@Autowired
	private OfertaCursoService ofertaCursoService;

	@Override
	public Vestibulando mergeDtoIntoEntity(VestibulandoDto dto, Vestibulando entity) {
		entity.setId(dto.getId());
		entity.setCpf(dto.getCpf());
		entity.setNome(dto.getNome());
		entity.setDataNascimento(dto.getDataNascimento() == null ? null : new Date(dto.getDataNascimento()));
		entity.setEndereco(dto.getEndereco());
		entity.setTelefones(dto.getTelefones());
		entity.setOfertaCurso(ofertaCursoService.find(dto.getOfertaCurso()));

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(VestibulandoDto dto) {
		String error = "";

		if (dto.getCpf() == null) {
			error += "CPF pendente; ";
		}

		if (dto.getNome() == null) {
			error += "Nome pendente; ";
		}

		if (dto.getDataNascimento() == null) {
			error += "Data de nascimento pendente; ";
		}

		if (dto.getEndereco() == null) {
			error += "Endereço pendente; ";
		}

		if (dto.getTelefones() == null) {
			error += "Telefones pendentes; ";
		}

		OfertaCurso myOfertaCurso = null;
		if (dto.getOfertaCurso() == null) {
			error += "Oferta de curso pendente; ";
		} else {
			try {
				myOfertaCurso = ofertaCursoService.find(dto.getOfertaCurso());
			} catch (ObjectNotFoundException e) {
				error += "Oferta de curso com ID " + dto.getOfertaCurso() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}

		String myCpf = dto.getCpf();

		Optional<Vestibulando> similar = repository.findSimilarByCpfAndOfertaCurso(dto.getId(), myCpf, myOfertaCurso);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um cadastro com esse CPF nessa oferta de curso!");
		});
	}

	public List<VestibulandoDto> findListDtoByIsNotDiscente() {
		List<Vestibulando> listEntity = repository.findByIsNotDiscente();
		return listEntity.stream().map(entity -> getDtoFromEntity(entity)).collect(Collectors.toList());
	}

}
