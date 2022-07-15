package org.nucleodevel.sisacad.dto;

import java.util.Date;

import org.nucleodevel.sisacad.domain.Avaliacao;

public class AvaliacaoDto extends AbstractDto<Avaliacao, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private String descricao;
	private Date inicio;
	private Date termino;
	private Integer ofertaDisciplina;

	@Override
	public void copyFromEntity(Avaliacao entity) {
		this.id = entity.getId();
		this.descricao = entity.getDescricao();
		this.inicio = entity.getInicio();
		this.termino = entity.getTermino();
		this.ofertaDisciplina = entity.getOfertaDisciplina().getId();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getTermino() {
		return termino;
	}

	public void setTermino(Date termino) {
		this.termino = termino;
	}

	public Integer getOfertaDisciplina() {
		return ofertaDisciplina;
	}

	public void setOfertaDisciplina(Integer ofertaDisciplina) {
		this.ofertaDisciplina = ofertaDisciplina;
	}

}
