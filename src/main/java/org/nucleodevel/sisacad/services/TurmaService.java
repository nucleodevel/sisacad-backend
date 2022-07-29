package org.nucleodevel.sisacad.services;

import java.util.Optional;

import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Turma;
import org.nucleodevel.sisacad.dto.TurmaDto;
import org.nucleodevel.sisacad.repositories.TurmaRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurmaService extends AbstractService<Turma, Integer, TurmaDto, TurmaRepository> {

	@Autowired
	private OfertaCursoService ofertaCursoService;

	@Override
	public Turma mergeDtoIntoEntity(TurmaDto dto, Turma entity) {
		entity.setId(dto.getId());
		entity.setCodigo(dto.getCodigo());
		entity.setOfertaCurso(ofertaCursoService.find(dto.getOfertaCurso()));

		return entity;
	}

	@Override
	public void validadeForInsertUpdate(TurmaDto dto) {
		String error = "";

		if (dto.getCodigo() == null) {
			error += "Código pendente; ";
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

		String myCodigo = dto.getCodigo();

		Optional<Turma> similar = repository.findSimilarByCodigoOrOfertaCurso(dto.getId(), myCodigo, myOfertaCurso);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe uma turma com esse código ou para essa oferta de curso!");
		});
	}

}
