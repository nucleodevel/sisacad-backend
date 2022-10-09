package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Usuario;
import org.nucleodevel.sisacad.repositories.UsuarioRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends AbstractService<Usuario, Integer, UsuarioRepository> {

	@Override
	public void validadeForInsertUpdate(Usuario entity) {
		String error = "";

		if (entity.getUsername() == null) {
			error += "Username pendente; ";
		}

		if (entity.getPassword() == null) {
			error += "Password pendente; ";
		}

		if (entity.getNome() == null) {
			error += "Nome pendente; ";
		}

		if (entity.getEmail() == null) {
			error += "E-mail pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		String myUsername = entity.getUsername();

		Optional<Usuario> similar = repository.findSimilarByUsername(entity.getId(), myUsername);
		similar.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um Usuario com esse username!");
		});
	}

	@Override
	public List<Usuario> findAll() {
		return repository.findByOrderByUsernameAsc();
	}

	public Usuario findByUsername(String username) {
		return repository.findByUsername(username);
	}

}
