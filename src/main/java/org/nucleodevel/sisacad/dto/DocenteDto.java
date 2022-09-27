package org.nucleodevel.sisacad.dto;

import org.nucleodevel.sisacad.domain.Docente;

public class DocenteDto extends AbstractDto<Docente, Integer> {

	private static final long serialVersionUID = 8835147583838231255L;

	private String cpf;
	private Long dataNascimento;
	private String endereco;
	private String telefones;
	private Integer usuario;

	@Override
	public void copyFromEntity(Docente entity) {
		this.id = entity.getId();
		this.cpf = entity.getCpf();
		this.dataNascimento = entity.getDataNascimento() == null ? null : entity.getDataNascimento().getTime();
		this.endereco = entity.getEndereco();
		this.telefones = entity.getTelefones();
		this.usuario = entity.getUsuario().getId();
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Long getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Long dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefones() {
		return telefones;
	}

	public void setTelefones(String telefones) {
		this.telefones = telefones;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

}
