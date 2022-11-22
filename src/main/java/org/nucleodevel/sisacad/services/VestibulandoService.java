package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.repositories.VestibulandoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.nucleodevel.sisacad.utils.CpfValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class VestibulandoService extends AbstractService<Vestibulando, Integer, VestibulandoRepository> {

	@Autowired
	private OfertaCursoService ofertaCursoService;

	@Override
	public void validadeForInsertUpdate(Vestibulando entity) {
		String error = "";

		if (!StringUtils.hasText(entity.getCpf())) {
			error += "CPF pendente; ";
		}

		if (StringUtils.hasText(entity.getCpf()) && !CpfValidator.isValid(entity.getCpf())) {
			error += "CPF inválido; ";
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

		OfertaCurso myOfertaCurso = null;
		if (entity.getOfertaCurso() == null) {
			error += "Oferta de curso pendente; ";
		} else {
			try {
				myOfertaCurso = ofertaCursoService.find(entity.getOfertaCurso().getId());
			} catch (ObjectNotFoundException e) {
				error += "Oferta de curso com ID " + entity.getOfertaCurso().getId() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		String myCpf = entity.getCpf();

		Optional<Vestibulando> similar = repository.findSimilarByCpfAndVestibular(entity.getId(), myCpf,
				myOfertaCurso.getVestibular());
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um cadastro com esse CPF nesse vestibular!");
		});
	}

	@Override
	public List<Vestibulando> findAll() {
		return repository.findByOrderByNomeAsc();
	}

	public Vestibulando findByUsername(String username) {
		Optional<Vestibulando> entity = repository.findByUsername(username);
		return entity.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado: " + username));
	}

	public List<Vestibulando> findListByAprovado() {
		return repository.findByAprovado();
	}

	public List<Vestibulando> findListByIsNotDiscente() {
		return repository.findByIsNotDiscente();
	}

	public List<Vestibulando> findListByAprovadoAndIsNotDiscente() {
		return repository.findByAprovadoAndIsNotDiscente();
	}

}
