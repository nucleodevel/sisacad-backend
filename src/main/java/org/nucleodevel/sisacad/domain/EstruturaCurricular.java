package org.nucleodevel.sisacad.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@JsonIgnore
	@OneToMany(mappedBy = "estruturaCurricular")
	private List<OfertaCurso> listaOfertaCurso = new ArrayList<>();

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "estrutura_curricular_disciplina", joinColumns = @JoinColumn(name = "id_estrutura_curricular"), inverseJoinColumns = @JoinColumn(name = "id_disciplina"))
	private List<Disciplina> listaDisciplina = new ArrayList<>();

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

	public List<OfertaCurso> getListaOfertaCurso() {
		return listaOfertaCurso;
	}

	public void setListaOfertaCurso(List<OfertaCurso> listaOfertaCurso) {
		this.listaOfertaCurso = listaOfertaCurso;
	}

	public List<Disciplina> getListaDisciplina() {
		return listaDisciplina;
	}

	public void setListaDisciplina(List<Disciplina> listaDisciplina) {
		this.listaDisciplina = listaDisciplina;
	}

}
