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
public class Vestibular extends AbstractEntity<Integer> {

	private static final long serialVersionUID = -139937194785283199L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ano")
	private Integer ano;

	@JsonIgnore
	@OneToMany(mappedBy = "vestibular")
	private List<OfertaCurso> listaOfertaCurso = new ArrayList<>();

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public List<OfertaCurso> getListaOfertaCurso() {
		return listaOfertaCurso;
	}

	public void setListaOfertaCurso(List<OfertaCurso> listaOfertaCurso) {
		this.listaOfertaCurso = listaOfertaCurso;
	}

}
