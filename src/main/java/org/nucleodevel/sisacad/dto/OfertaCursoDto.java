package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.OfertaCurso;

public class OfertaCursoDto extends AbstractDto<OfertaCurso, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private Integer ano;
	private Integer estruturaCurricular;
	private Integer vestibular;
	private Integer turma;

	@Override
	public void copyFromEntity(OfertaCurso entity) {
		this.id = entity.getId();
		this.ano = entity.getAno();
		this.estruturaCurricular = entity.getEstruturaCurricular().getId();
		this.vestibular = entity.getVestibular().getId();
		this.turma = entity.getTurma() == null? null: entity.getTurma().getId();
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getEstruturaCurricular() {
		return estruturaCurricular;
	}

	public void setEstruturaCurricular(Integer estruturaCurricular) {
		this.estruturaCurricular = estruturaCurricular;
	}

	public Integer getVestibular() {
		return vestibular;
	}

	public void setVestibular(Integer vestibular) {
		this.vestibular = vestibular;
	}

	public Integer getTurma() {
		return turma;
	}

	public void setTurma(Integer turma) {
		this.turma = turma;
	}

}
