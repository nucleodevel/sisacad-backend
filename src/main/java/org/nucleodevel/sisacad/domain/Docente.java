package org.nucleodevel.sisacad.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Docente extends AbstractEntity<Integer> {

	private static final long serialVersionUID = -5627371372664777035L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@JsonIgnore
	@OneToMany(mappedBy = "docente")
	private List<OfertaDisciplina> listaOfertaDisciplina = new ArrayList<>();

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

}