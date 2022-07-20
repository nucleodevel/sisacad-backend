package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.Avaliacao;

public class AvaliacaoDto extends AbstractDto<Avaliacao, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private String descricao;
	private Long inicio;
	private Long termino;
	private Integer ofertaDisciplina;

	@Override
	public void copyFromEntity(Avaliacao entity) {
		this.id = entity.getId();
		this.descricao = entity.getDescricao();
		this.inicio = entity.getInicio() == null ? null : entity.getInicio().getTime();
		this.termino = entity.getTermino() == null ? null : entity.getTermino().getTime();
		this.ofertaDisciplina = entity.getOfertaDisciplina().getId();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getInicio() {
		return inicio;
	}

	public void setInicio(Long inicio) {
		this.inicio = inicio;
	}

	public Long getTermino() {
		return termino;
	}

	public void setTermino(Long termino) {
		this.termino = termino;
	}

	public Integer getOfertaDisciplina() {
		return ofertaDisciplina;
	}

	public void setOfertaDisciplina(Integer ofertaDisciplina) {
		this.ofertaDisciplina = ofertaDisciplina;
	}

}
