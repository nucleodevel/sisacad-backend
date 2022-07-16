package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.Vestibulando;

public class VestibulandoDto extends AbstractDto<Vestibulando, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private String nome;
	private Integer ofertaCurso;
	private Integer avaliacaoVestibulando;
	private Integer discente;

	@Override
	public void copyFromEntity(Vestibulando entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.ofertaCurso = entity.getOfertaCurso().getId();
		this.avaliacaoVestibulando = entity.getAvaliacaoVestibulando().getId();
		this.discente = entity.getDiscente().getId();
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

	public Integer getAvaliacaoVestibulando() {
		return avaliacaoVestibulando;
	}

	public void setAvaliacaoVestibulando(Integer avaliacaoVestibulando) {
		this.avaliacaoVestibulando = avaliacaoVestibulando;
	}

	public Integer getDiscente() {
		return discente;
	}

	public void setDiscente(Integer discente) {
		this.discente = discente;
	}

}
