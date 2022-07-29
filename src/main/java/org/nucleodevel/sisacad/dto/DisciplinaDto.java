package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.Disciplina;

public class DisciplinaDto extends AbstractDto<Disciplina, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private String codigo;
	private String nome;

	@Override
	public void copyFromEntity(Disciplina entity) {
		this.id = entity.getId();
		this.codigo = entity.getCodigo();
		this.nome = entity.getNome();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
