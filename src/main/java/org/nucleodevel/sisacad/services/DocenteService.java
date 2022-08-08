package org.nucleodevel.sisacad.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.dto.DocenteDto;
import org.nucleodevel.sisacad.repositories.DocenteRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.stereotype.Service;

@Service
public class DocenteService extends AbstractService<Docente, Integer, DocenteDto, DocenteRepository> {

	@Override
	public Docente mergeDtoIntoEntity(DocenteDto dto, Docente entity) {
		entity.setId(dto.getId());
		entity.setCpf(dto.getCpf());
		entity.setNome(dto.getNome());
		entity.setDataNascimento(dto.getDataNascimento() == null ? null : new Date(dto.getDataNascimento()));
		entity.setEndereco(dto.getEndereco());
		entity.setTelefones(dto.getTelefones());

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(DocenteDto dto) {
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

		if (!error.isEmpty()) {
			throw new FieldValidationException(dto.getId(), getEntityClass(), error);
		}

		String myCpf = dto.getCpf();

		Optional<Docente> similar = repository.findSimilarByCpf(dto.getId(), myCpf);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um docente com esse CPF!");
		});
	}

	@Override
	protected List<Docente> findAll() {
		return repository.findByOrderByNome();
	}

}
