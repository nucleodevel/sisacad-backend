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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class OfertaDisciplina extends AbstractEntity<Integer> {

	private static final long serialVersionUID = 3156925925567473121L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_disciplina")
	private Disciplina disciplina;

	@ManyToOne
	@JoinColumn(name = "id_docente")
	private Docente docente;

	@OneToMany(mappedBy = "ofertaDisciplina")
	private List<Aula> listaAula = new ArrayList<>();

	@OneToMany(mappedBy = "ofertaDisciplina")
	private List<Avaliacao> listaAvaliacao = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "oferta_disciplina_turma", joinColumns = @JoinColumn(name = "id_oferta_disciplina"), inverseJoinColumns = @JoinColumn(name = "id_turma"))
	private List<Turma> listaTurma = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "oferta_disciplina_discente", joinColumns = @JoinColumn(name = "id_oferta_disciplina"), inverseJoinColumns = @JoinColumn(name = "id_discente"))
	private List<Discente> listaDiscente = new ArrayList<>();

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(Docente docente) {
		this.docente = docente;
	}

	public List<Aula> getListaAula() {
		return listaAula;
	}

	public List<Avaliacao> getListaAvaliacao() {
		return listaAvaliacao;
	}

	public List<Turma> getListaTurma() {
		return listaTurma;
	}

	public void setListaTurma(List<Turma> listaTurma) {
		this.listaTurma = listaTurma;
	}

	public List<Discente> getListaDiscente() {
		return listaDiscente;
	}

	public void setListaDiscente(List<Discente> listaDiscente) {
		this.listaDiscente = listaDiscente;
	}

}
