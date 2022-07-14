package org.nucleodevel.sisacad.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class OfertaCurso extends AbstractEntity<Integer> {

	private static final long serialVersionUID = -168493590958846949L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ano")
	private Integer ano;

	@ManyToOne
	@JoinColumn(name = "id_estrutura_curricular")
	private EstruturaCurricular estruturaCurricular;

	@ManyToOne
	@JoinColumn(name = "id_vestibular")
	private Vestibular vestibular;

	@JsonIgnore
	@OneToOne(mappedBy = "ofertaCurso")
	private Turma turma;

	@JsonIgnore
	@OneToMany(mappedBy = "ofertaCurso")
	private List<Vestibulando> listaVestibulando = new ArrayList<>();

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

	public EstruturaCurricular getEstruturaCurricular() {
		return estruturaCurricular;
	}

	public void setEstruturaCurricular(EstruturaCurricular estruturaCurricular) {
		this.estruturaCurricular = estruturaCurricular;
	}

	public Vestibular getVestibular() {
		return vestibular;
	}

	public void setVestibular(Vestibular vestibular) {
		this.vestibular = vestibular;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<Vestibulando> getListaVestibulando() {
		return listaVestibulando;
	}

	public void setListaVestibulando(List<Vestibulando> listaVestibulando) {
		this.listaVestibulando = listaVestibulando;
	}

}