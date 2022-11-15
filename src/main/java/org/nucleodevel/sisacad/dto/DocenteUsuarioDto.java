package org.nucleodevel.sisacad.dto;

import java.io.Serializable;

import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.domain.Usuario;

public class DocenteUsuarioDto implements Serializable {

	private static final long serialVersionUID = 8710695291653699216L;

	private Integer id;
	private DocenteDto docente;
	private UsuarioDto usuario;

	public void copyFromEntity(Docente docente, Usuario usuario) {
		this.id = docente.getId();

		this.docente = new DocenteDto();
		this.docente.copyFromEntity(docente);

		this.usuario = new UsuarioDto();
		this.usuario.copyFromEntity(usuario);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DocenteDto getDocente() {
		return docente;
	}

	public void setDocente(DocenteDto docente) {
		this.docente = docente;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

}
