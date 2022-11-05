package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.repositories.DocenteRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DocenteService extends AbstractService<Docente, Integer, DocenteRepository> {

	@Override
	public void validadeForInsertUpdate(Docente entity) {
		String error = "";

		if (!StringUtils.hasText(entity.getCpf())) {
			error += "CPF pendente; ";
		}

		if (entity.getDataNascimento() == null) {
			error += "Data de nascimento pendente; ";
		}

		if (!StringUtils.hasText(entity.getEndereco())) {
			error += "Endereço pendente; ";
		}

		if (!StringUtils.hasText(entity.getTelefones())) {
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

	public Docente findByUsername(String username) {
		Optional<Docente> entity = repository.findByUsername(username);
		return entity.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado: " + username));
	}

}
