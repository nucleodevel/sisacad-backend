package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.Vestibular;
import org.nucleodevel.sisacad.repositories.OfertaCursoRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.nucleodevel.sisacad.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfertaCursoService extends AbstractService<OfertaCurso, Integer, OfertaCursoRepository> {

	@Autowired
	private EstruturaCurricularService estruturaCurricularService;
	@Autowired
	private VestibularService vestibularService;

	@Override
	public void validadeForInsertUpdate(OfertaCurso entity) {
		String error = "";

		if (entity.getCodigo() == null) {
			error += "Código pendente; ";
		}

		if (entity.getAno() == null) {
			error += "Ano pendente; ";
		}

		EstruturaCurricular myEstruturaCurricular = null;
		if (entity.getEstruturaCurricular() == null) {
			error += "Estrutura curricular pendente; ";
		} else {
			try {
				myEstruturaCurricular = estruturaCurricularService.find(entity.getEstruturaCurricular().getId());
			} catch (ObjectNotFoundException e) {
				error += "Estrutura curricular com ID " + entity.getEstruturaCurricular().getId() + " não existente; ";
			}
		}

		Vestibular myVestibular = null;
		if (entity.getVestibular() == null) {
			error += "Vestibular pendente; ";
		} else {
			try {
				myVestibular = vestibularService.find(entity.getVestibular().getId());
			} catch (ObjectNotFoundException e) {
				error += "Vestibular com ID " + entity.getVestibular().getId() + " não existente; ";
			}
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		String myCodigo = entity.getCodigo();

		Optional<OfertaCurso> similar = repository.findSimilarByCodigoOrEstruturaCurricularAndVestibular(entity.getId(),
				myCodigo, myEstruturaCurricular, myVestibular);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException(
					"Já existe uma oferta de curso com esse código ou nesse vestibular para essa estrutura curricular!");
		});
	}

	@Override
	public List<OfertaCurso> findAll() {
		return repository.findByOrderByAnoDescCursoAsc();
	}

}
