package org.nucleodevel.sisacad.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
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

	@Column(name = "codigo")
	private String codigo;

	@Column(name = "semestre")
	private Integer semestre;

	@ManyToOne
	@JoinColumn(name = "id_disciplina")
	private Disciplina disciplina;

	@ManyToOne
	@JoinColumn(name = "id_docente")
	private Docente docente;

	@OneToMany(mappedBy = "ofertaDisciplina")
	private List<Aula> listAula = new ArrayList<>();

	@OneToMany(mappedBy = "ofertaDisciplina")
	private List<Avaliacao> listAvaliacao = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "oferta_disciplina_turma", joinColumns = @JoinColumn(name = "id_oferta_disciplina"), inverseJoinColumns = @JoinColumn(name = "id_turma"))
	private List<Turma> listTurma = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "oferta_disciplina_discente", joinColumns = @JoinColumn(name = "id_oferta_disciplina"), inverseJoinColumns = @JoinColumn(name = "id_discente"))
	private List<Discente> listDiscente = new ArrayList<>();

	public OfertaDisciplina() {
		super();
	}

	public OfertaDisciplina(String codigo, Integer semestre, Disciplina disciplina, Docente docente) {
		super();
		this.codigo = codigo;
		this.semestre = semestre;
		this.disciplina = disciplina;
		this.docente = docente;
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

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
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

	public List<Aula> getListAula() {
		return listAula;
	}

	public List<Avaliacao> getListAvaliacao() {
		return listAvaliacao;
	}

	public List<Turma> getListTurma() {
		return listTurma;
	}

	public void setListTurma(List<Turma> listTurma) {
		this.listTurma = listTurma;
	}

	public void insertTurma(Turma turma) {
		boolean exists = false;

		for (Turma subItem : listTurma) {
			if (subItem.getId().equals(turma.getId())) {
				exists = true;
			}
		}

		if (!exists) {
			listTurma.add(turma);
		}
	}

	public void deleteTurma(Turma turma) {
		Integer position = null;

		for (int i = 0; i < listTurma.size(); i++) {
			Turma subItem = listTurma.get(i);
			if (subItem.getId().equals(turma.getId())) {
				position = i;
			}
		}

		if (position != null) {
			listTurma.remove(position.intValue());
		}
	}

	public List<Discente> getListDiscente() {
		return listDiscente;
	}

	public void setListDiscente(List<Discente> listDiscente) {
		this.listDiscente = listDiscente;
	}

	public void insertDiscente(Discente discente) {
		boolean exists = false;

		for (Discente subItem : listDiscente) {
			if (subItem.getId().equals(discente.getId())) {
				exists = true;
			}
		}

		if (!exists) {
			listDiscente.add(discente);
		}
	}

	public void deleteDiscente(Discente discente) {
		Integer position = null;

		for (int i = 0; i < listDiscente.size(); i++) {
			Discente subItem = listDiscente.get(i);
			if (subItem.getId().equals(discente.getId())) {
				position = i;
			}
		}

		if (position != null) {
			listDiscente.remove(position.intValue());
		}
	}

}
