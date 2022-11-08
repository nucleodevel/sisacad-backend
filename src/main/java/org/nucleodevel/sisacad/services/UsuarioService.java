package org.nucleodevel.sisacad.services;

import java.util.List;
import java.util.Optional;

import org.nucleodevel.sisacad.domain.Usuario;
import org.nucleodevel.sisacad.repositories.UsuarioRepository;
import org.nucleodevel.sisacad.services.exceptions.DataIntegrityException;
import org.nucleodevel.sisacad.services.exceptions.FieldValidationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UsuarioService extends AbstractService<Usuario, Integer, UsuarioRepository> {

	@Override
	public void validadeForInsertUpdate(Usuario entity) {
		String error = "";

		if (!StringUtils.hasText(entity.getUsername())) {
			error += "Username pendente; ";
		}

		if (!StringUtils.hasText(entity.getPassword())) {
			error += "Password pendente; ";
		}

		if (!StringUtils.hasText(entity.getNome())) {
			error += "Nome pendente; ";
		}

		if (!StringUtils.hasText(entity.getEmail())) {
			error += "E-mail pendente; ";
		}

		if (!error.isEmpty()) {
			throw new FieldValidationException(entity.getId(), getEntityClass(), error);
		}

		String myUsername = entity.getUsername();

		Optional<Usuario> similarByUsername = repository.findSimilarByUsername(entity.getId(), myUsername);
		similarByUsername.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um Usuario com esse username!");
		});

		String myEmail = entity.getEmail();

		Optional<Usuario> similarByEmail = repository.findSimilarByEmail(entity.getId(), myEmail);
		similarByEmail.ifPresent(obj -> {
			throw new DataIntegrityException("Já existe um Usuario com esse e-mail!");
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
