package org.nucleodevel.sisacad.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Turma extends AbstractEntity<Integer> {

	private static final long serialVersionUID = 8978057724076254716L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "id_oferta_curso")
	private OfertaCurso ofertaCurso;

	@ManyToMany
	@JoinTable(name = "oferta_disciplina_turma", joinColumns = @JoinColumn(name = "id_turma"), inverseJoinColumns = @JoinColumn(name = "id_oferta_disciplina"))
	private List<OfertaDisciplina> listOfertaDisciplina = new ArrayList<>();

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public OfertaCurso getOfertaCurso() {
		return ofertaCurso;
	}

	public void setOfertaCurso(OfertaCurso ofertaCurso) {
		this.ofertaCurso = ofertaCurso;
	}

	public List<OfertaDisciplina> getListOfertaDisciplina() {
		return listOfertaDisciplina;
	}

	public void setListOfertaDisciplina(List<OfertaDisciplina> listOfertaDisciplina) {
		this.listOfertaDisciplina = listOfertaDisciplina;
	}

}
