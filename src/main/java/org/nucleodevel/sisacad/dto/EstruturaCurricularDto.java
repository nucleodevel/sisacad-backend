package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.EstruturaCurricular;

public class EstruturaCurricularDto extends AbstractDto<EstruturaCurricular, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private Integer anoInicio;
	private Integer anoTermino;
	private Integer curso;

	@Override
	public void copyFromEntity(EstruturaCurricular entity) {
		this.id = entity.getId();
		this.anoInicio = entity.getAnoInicio();
		this.anoTermino = entity.getAnoTermino();
		this.curso = entity.getCurso().getId();
	}

	public Integer getAnoInicio() {
		return anoInicio;
	}

	public void setAnoInicio(Integer anoInicio) {
		this.anoInicio = anoInicio;
	}

	public Integer getAnoTermino() {
		return anoTermino;
	}

	public void setAnoTermino(Integer anoTermino) {
		this.anoTermino = anoTermino;
	}

	public Integer getCurso() {
		return curso;
	}

	public void setCurso(Integer curso) {
		this.curso = curso;
	}

}
