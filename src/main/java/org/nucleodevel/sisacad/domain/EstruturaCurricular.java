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
public class EstruturaCurricular extends AbstractEntity<Integer> {

	private static final long serialVersionUID = -890527768561265389L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ano_inicio")
	private Integer anoInicio;

	@Column(name = "ano_termino")
	private Integer anoTermino;

	@ManyToOne
	@JoinColumn(name = "id_curso")
	private Curso curso;

	@OneToMany(mappedBy = "estruturaCurricular")
	private List<OfertaCurso> listOfertaCurso = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "estrutura_curricular_disciplina", joinColumns = @JoinColumn(name = "id_estrutura_curricular"), inverseJoinColumns = @JoinColumn(name = "id_disciplina"))
	private List<Disciplina> listDisciplina = new ArrayList<>();

	public EstruturaCurricular() {
		super();
	}

	public EstruturaCurricular(Integer anoInicio, Integer anoTermino, Curso curso) {
		super();
		this.anoInicio = anoInicio;
		this.anoTermino = anoTermino;
		this.curso = curso;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAnoInicio() {
		return anoInicio;
	}

	public void setAnoInicio(Integer anoInicio) {
		this.anoInicio = anoInicio;
	}

	public Integer getAnoTermino() {
		return anoTermino;
	}

	public void setAnoTermino(Integer anoTermino) {
		this.anoTermino = anoTermino;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<OfertaCurso> getListOfertaCurso() {
		return listOfertaCurso;
	}

	public List<Disciplina> getListDisciplina() {
		return listDisciplina;
	}

	public void setListDisciplina(List<Disciplina> listDisciplina) {
		this.listDisciplina = listDisciplina;
	}

	public void insertDisciplina(Disciplina disciplina) {
		boolean exists = false;

		for (Disciplina subItem : listDisciplina) {
			if (subItem.getId().equals(disciplina.getId())) {
				exists = true;
			}
		}

		if (!exists) {
			listDisciplina.add(disciplina);
		}
	}

	public void deleteDisciplina(Disciplina disciplina) {
		Integer position = null;

		for (int i = 0; i < listDisciplina.size(); i++) {
			Disciplina subItem = listDisciplina.get(i);
			if (subItem.getId().equals(disciplina.getId())) {
				position = i;
			}
		}

		if (position != null) {
			listDisciplina.remove(position.intValue());
		}
	}

}
