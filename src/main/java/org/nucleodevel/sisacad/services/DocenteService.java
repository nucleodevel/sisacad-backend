package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.repositories.DocenteRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.stereotype.Service;

@Service
public class DocenteService extends AbstractService<Docente, Integer, DocenteRepository> {

	@Override
	public void validadeForInsertUpdate(Docente entity) {
		String error = "";

		if (entity.getCpf() == null) {
			error += "CPF pendente; ";
		}

		if (entity.getNome() == null) {
			error += "Nome pendente; ";
		}

		if (entity.getDataNascimento() == null) {
			error += "Data de nascimento pendente; ";
		}

		if (entity.getEndereco() == null) {
			error += "Endereço pendente; ";
		}

		if (entity.getTelefones() == null) {
			error += "Telefones pendentes; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		String myCpf = entity.getCpf();

		Optional<Docente> similar = repository.findSimilarByCpf(entity.getId(), myCpf);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um docente com esse CPF!");
		});
	}

	@Override
	public List<Docente> findAll() {
		return repository.findByOrderByNomeAsc();
	}

}
