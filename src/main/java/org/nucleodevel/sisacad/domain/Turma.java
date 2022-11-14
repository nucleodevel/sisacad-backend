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
import javax.persistence.OneToOne;

@Entity
public class Turma extends AbstractEntity<Integer> {

	private static final long serialVersionUID = 8978057724076254716L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "codigo")
	private String codigo;

	@OneToOne
	@JoinColumn(name = "id_oferta_curso")
	private OfertaCurso ofertaCurso;

	@ManyToMany
	@JoinTable(name = "oferta_disciplina_turma", joinColumns = @JoinColumn(name = "id_turma"), inverseJoinColumns = @JoinColumn(name = "id_oferta_disciplina"))
	private List<OfertaDisciplina> listOfertaDisciplina = new ArrayList<>();

	public Turma() {
		super();
	}

	public Turma(String codigo, OfertaCurso ofertaCurso) {
		super();
		this.codigo = codigo;
		this.ofertaCurso = ofertaCurso;
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

	public void insertOfertaDisciplina(OfertaDisciplina ofertaDisciplina) {
		boolean exists = false;

		for (OfertaDisciplina subItem : listOfertaDisciplina) {
			if (subItem.getId().equals(ofertaDisciplina.getId())) {
				exists = true;
			}
		}

		if (!exists) {
			listOfertaDisciplina.add(ofertaDisciplina);
		}
	}

	public void deleteOfertaDisciplina(OfertaDisciplina ofertaDisciplina) {
		Integer position = null;

		for (int i = 0; i < listOfertaDisciplina.size(); i++) {
			OfertaDisciplina subItem = listOfertaDisciplina.get(i);
			if (subItem.getId().equals(ofertaDisciplina.getId())) {
				position = i;
			}
		}

		if (position != null) {
			listOfertaDisciplina.remove(position.intValue());
		}
	}

	@Override
	public String toComparableString() {
		return getOfertaCurso().toComparableString() + "//" + getCodigo();
	}

}
