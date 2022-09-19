package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.Usuario;

public class UsuarioDto extends AbstractDto<Usuario, Integer> {

	private static final long serialVersionUID = -8969212925884702265L;

	private String username;
	private String password;

	@Override
	public void copyFromEntity(Usuario entity) {
		this.id = entity.getId();
		this.username = entity.getUsername();
		this.password = entity.getPassword();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
