package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.repositories.VestibulandoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VestibulandoService extends AbstractService<Vestibulando, Integer, VestibulandoRepository> {

	@Autowired
	private OfertaCursoService ofertaCursoService;

	@Override
	public void validadeForInsertUpdate(Vestibulando entity) {
		String error = "";

		if (entity.getCpf() == null) {
			error += "CPF pendente; ";
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

		Optional<Vestibulando> similar = repository.findSimilarByCpfAndOfertaCurso(entity.getId(), myCpf,
				myOfertaCurso);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um cadastro com esse CPF nessa oferta de curso!");
		});
	}

	@Override
	public List<Vestibulando> findAll() {
		return repository.findByOrderByNomeAsc();
	}

	public List<Vestibulando> findListByIsNotDiscente() {
		return repository.findByIsNotDiscente();
	}

}
