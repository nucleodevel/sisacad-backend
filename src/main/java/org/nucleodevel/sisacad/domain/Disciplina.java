package org.nucleodevel.sisacad.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Disciplina extends AbstractEntity<Integer> {

	private static final long serialVersionUID = -477986579635110766L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@JsonIgnore
	@OneToMany(mappedBy = "disciplina")
	private List<OfertaDisciplina> listaOfertaDisciplina = new ArrayList<>();

	@ManyToMany(mappedBy = "listaDisciplina")
	private List<EstruturaCurricular> listaEstruturaCurricular = new ArrayList<>();

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<OfertaDisciplina> getListaOfertaDisciplina() {
		return listaOfertaDisciplina;
	}

	public void setListaOfertaDisciplina(List<OfertaDisciplina> listaOfertaDisciplina) {
		this.listaOfertaDisciplina = listaOfertaDisciplina;
	}

	public List<EstruturaCurricular> getListaEstruturaCurricular() {
		return listaEstruturaCurricular;
	}

	public void setListaEstruturaCurricular(List<EstruturaCurricular> listaEstruturaCurricular) {
		this.listaEstruturaCurricular = listaEstruturaCurricular;
	}

}
