package org.nucleodevel.sisacad.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Curso extends AbstractEntity<Integer> {

	private static final long serialVersionUID = -488641359677871633L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "codigo")
	private String codigo;

	@Column(name = "nome")
	private String nome;

	@OneToMany(mappedBy = "curso")
	private List<EstruturaCurricular> listEstruturaCurricular = new ArrayList<>();

	public Curso() {
		super();
	}

	public Curso(String codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
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

	public List<EstruturaCurricular> getListEstruturaCurricular() {
		return listEstruturaCurricular;
	}

}
