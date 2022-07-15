package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.Vestibulando;

public class VestibulandoDto extends AbstractDto<Vestibulando, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private String nome;
	private Integer ofertaCurso;

	@Override
	public void copyFromEntity(Vestibulando entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.ofertaCurso = entity.getOfertaCurso().getId();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getOfertaCurso() {
		return ofertaCurso;
	}

	public void setOfertaCurso(Integer ofertaCurso) {
		this.ofertaCurso = ofertaCurso;
	}

}
