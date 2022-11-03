package org.nucleodevel.sisacad.dto;

import java.io.Serializable;

import org.nucleodevel.sisacad.domain.Usuario;
import org.nucleodevel.sisacad.domain.Vestibulando;

public class VestibulandoUsuarioDto implements Serializable {

	private static final long serialVersionUID = 8835147583838231255L;

	private Integer id;
	private VestibulandoDto vestibulando;
	private UsuarioDto usuario;

	public void copyFromEntity(Vestibulando vestibulando, Usuario usuario) {
		this.id = vestibulando.getId();

		this.vestibulando = new VestibulandoDto();
		this.vestibulando.copyFromEntity(vestibulando);

		this.usuario = new UsuarioDto();
		this.usuario.copyFromEntity(usuario);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public VestibulandoDto getVestibulando() {
		return vestibulando;
	}

	public void setVestibulando(VestibulandoDto vestibulando) {
		this.vestibulando = vestibulando;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

}
