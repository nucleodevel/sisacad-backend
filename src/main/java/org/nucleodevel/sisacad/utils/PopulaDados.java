package org.nucleodevel.sisacad.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;

import org.nucleodevel.sisacad.domain.Aula;
import org.nucleodevel.sisacad.domain.Avaliacao;
import org.nucleodevel.sisacad.domain.AvaliacaoVestibulando;
import org.nucleodevel.sisacad.domain.Curso;
import org.nucleodevel.sisacad.domain.Discente;
import org.nucleodevel.sisacad.domain.Disciplina;
import org.nucleodevel.sisacad.domain.Docente;
import org.nucleodevel.sisacad.domain.EstruturaCurricular;
import org.nucleodevel.sisacad.domain.OfertaCurso;
import org.nucleodevel.sisacad.domain.OfertaDisciplina;
import org.nucleodevel.sisacad.domain.ParticipacaoAula;
import org.nucleodevel.sisacad.domain.ParticipacaoAvaliacao;
import org.nucleodevel.sisacad.domain.Turma;
import org.nucleodevel.sisacad.domain.Vestibulando;
import org.nucleodevel.sisacad.domain.Vestibular;
import org.nucleodevel.sisacad.repositories.AulaRepository;
import org.nucleodevel.sisacad.repositories.AvaliacaoRepository;
import org.nucleodevel.sisacad.repositories.AvaliacaoVestibulandoRepository;
import org.nucleodevel.sisacad.repositories.CursoRepository;
import org.nucleodevel.sisacad.repositories.DiscenteRepository;
import org.nucleodevel.sisacad.repositories.DisciplinaRepository;
import org.nucleodevel.sisacad.repositories.DocenteRepository;
import org.nucleodevel.sisacad.repositories.EstruturaCurricularRepository;
import org.nucleodevel.sisacad.repositories.OfertaCursoRepository;
import org.nucleodevel.sisacad.repositories.OfertaDisciplinaRepository;
import org.nucleodevel.sisacad.repositories.ParticipacaoAulaRepository;
import org.nucleodevel.sisacad.repositories.ParticipacaoAvaliacaoRepository;
import org.nucleodevel.sisacad.repositories.TurmaRepository;
import org.nucleodevel.sisacad.repositories.VestibulandoRepository;
import org.nucleodevel.sisacad.repositories.VestibularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PopulaDados {

	@Autowired
	private AulaRepository aulaRepository;
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	@Autowired
	private AvaliacaoVestibulandoRepository avaliacaoVestibulandoRepository;
	@Autowired
	private CursoRepository cursoRepository;
	@Autowired
	private DiscenteRepository discenteRepository;
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	@Autowired
	private DocenteRepository docenteRepository;
	@Autowired
	private EstruturaCurricularRepository estruturaCurricularRepository;
	@Autowired
	private OfertaCursoRepository ofertaCursoRepository;
	@Autowired
	private OfertaDisciplinaRepository ofertaDisciplinaRepository;
	@Autowired
	private ParticipacaoAulaRepository participacaoAulaRepository;
	@Autowired
	private ParticipacaoAvaliacaoRepository participacaoAvaliacaoRepository;
	@Autowired
	private TurmaRepository turmaRepository;
	@Autowired
	private VestibulandoRepository vestibulandoRepository;
	@Autowired
	private VestibularRepository vestibularRepository;

	@PostConstruct
	public void cadastrar() throws ParseException {

		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

		Curso cur1 = new Curso("Engenharia da Computação");
		Curso cur2 = new Curso("Ciência da Computação");

		cursoRepository.saveAll(List.of(cur1, cur2));

		Disciplina dcp1 = new Disciplina("Compiladores");
		Disciplina dcp2 = new Disciplina("Banco de Dados");
		Disciplina dcp3 = new Disciplina("Engenharia de Software");
		Disciplina dcp4 = new Disciplina("Programação Orientada a Objetos");

		disciplinaRepository.saveAll(List.of(dcp1, dcp2, dcp3, dcp4));

		EstruturaCurricular ec1 = new EstruturaCurricular(1999, 2012, cur1);
		ec1.setListDisciplina(List.of(dcp1, dcp2));
		EstruturaCurricular ec2 = new EstruturaCurricular(2013, 2026, cur1);
		ec1.setListDisciplina(List.of(dcp1, dcp2, dcp4));
		EstruturaCurricular ec3 = new EstruturaCurricular(2018, 2031, cur2);
		ec1.setListDisciplina(List.of(dcp1, dcp2, dcp3, dcp4));

		estruturaCurricularRepository.saveAll(List.of(ec1, ec2, ec3));

		Vestibular vst1 = new Vestibular(2021);

		vestibularRepository.saveAll(List.of(vst1));

		OfertaCurso oc1 = new OfertaCurso(2022, ec2, vst1);
		OfertaCurso oc2 = new OfertaCurso(2022, ec3, vst1);

		ofertaCursoRepository.saveAll(List.of(oc1, oc2));

		Turma trm1 = new Turma(oc1);
		Turma trm2 = new Turma(oc2);

		turmaRepository.saveAll(List.of(trm1, trm2));

		Docente doc1 = new Docente("João Santos");
		Docente doc2 = new Docente("Carlos Pereira");
		Docente doc3 = new Docente("Marcos Benevides");

		docenteRepository.saveAll(List.of(doc1, doc2, doc3));

		Vestibulando vnd01 = new Vestibulando("Rosana Martins", oc1);
		Vestibulando vnd02 = new Vestibulando("Osvaldo Picci", oc1);
		Vestibulando vnd03 = new Vestibulando("José Albertino", oc1);
		Vestibulando vnd04 = new Vestibulando("Kelvin Smith", oc1);
		Vestibulando vnd05 = new Vestibulando("Melinda Flores", oc1);
		Vestibulando vnd06 = new Vestibulando("Guilherme Toledo", oc2);
		Vestibulando vnd07 = new Vestibulando("Homero Assis", oc2);
		Vestibulando vnd08 = new Vestibulando("Carlos Balbuena", oc2);
		Vestibulando vnd09 = new Vestibulando("Teo Lee", oc2);
		Vestibulando vnd10 = new Vestibulando("Neusa Feltrin", oc2);

		vestibulandoRepository.saveAll(List.of(vnd01, vnd02, vnd03, vnd04, vnd05, vnd06, vnd07, vnd08, vnd09, vnd10));

		AvaliacaoVestibulando av01 = new AvaliacaoVestibulando(8.5, vnd01);
		AvaliacaoVestibulando av02 = new AvaliacaoVestibulando(8.0, vnd02);
		AvaliacaoVestibulando av03 = new AvaliacaoVestibulando(9.0, vnd03);
		AvaliacaoVestibulando av04 = new AvaliacaoVestibulando(4.5, vnd04);
		AvaliacaoVestibulando av05 = new AvaliacaoVestibulando(7.5, vnd05);
		AvaliacaoVestibulando av06 = new AvaliacaoVestibulando(5.8, vnd06);
		AvaliacaoVestibulando av07 = new AvaliacaoVestibulando(2.5, vnd07);
		AvaliacaoVestibulando av08 = new AvaliacaoVestibulando(9.7, vnd08);
		AvaliacaoVestibulando av09 = new AvaliacaoVestibulando(6.2, vnd09);
		AvaliacaoVestibulando av10 = new AvaliacaoVestibulando(8.1, vnd10);

		avaliacaoVestibulandoRepository.saveAll(List.of(av01, av02, av03, av04, av05, av06, av07, av08, av09, av10));

		Discente dct1 = new Discente(vnd01);
		Discente dct2 = new Discente(vnd02);
		Discente dct3 = new Discente(vnd03);
		Discente dct4 = new Discente(vnd05);
		Discente dct5 = new Discente(vnd06);
		Discente dct6 = new Discente(vnd08);
		Discente dct7 = new Discente(vnd09);
		Discente dct8 = new Discente(vnd10);

		discenteRepository.saveAll(List.of(dct1, dct2, dct3, dct4, dct5, dct6, dct7, dct8));

		OfertaDisciplina od1 = new OfertaDisciplina(dcp1, doc1);
		od1.setListTurma(List.of(trm1));
		od1.setListDiscente(List.of(dct1, dct2, dct3, dct4));
		OfertaDisciplina od2 = new OfertaDisciplina(dcp1, doc1);
		od2.setListTurma(List.of(trm2));
		od2.setListDiscente(List.of(dct5, dct6, dct7, dct8));
		OfertaDisciplina od3 = new OfertaDisciplina(dcp2, doc3);
		od3.setListTurma(List.of(trm1, trm2));
		od3.setListDiscente(List.of(dct1, dct2, dct3, dct4, dct5, dct7, dct8));
		OfertaDisciplina od4 = new OfertaDisciplina(dcp3, doc2);
		od4.setListTurma(List.of(trm2));
		od4.setListDiscente(List.of(dct5, dct6, dct7, dct8));
		OfertaDisciplina od5 = new OfertaDisciplina(dcp4, doc2);
		od5.setListTurma(List.of(trm1, trm2));
		od5.setListDiscente(List.of(dct1, dct2, dct4, dct6, dct7, dct8));

		ofertaDisciplinaRepository.saveAll(List.of(od1, od2, od3, od4, od5));

		Avaliacao avl1 = new Avaliacao();
		avl1.setInicio(sdfDate.parse("2022-05-20"));
		avl1.setTermino(sdfDate.parse("2022-05-30"));
		avl1.setDescricao("Projeto Integrado");
		avl1.setOfertaDisciplina(od1);

		avaliacaoRepository.saveAll(List.of(avl1));

		ParticipacaoAvaliacao pav1 = new ParticipacaoAvaliacao();
		pav1.setAvaliacao(avl1);
		pav1.setDiscente(dct1);

		participacaoAvaliacaoRepository.saveAll(List.of(pav1));

		Aula aul1 = new Aula();
		aul1.setInicio(sdfDate.parse("2022-05-20"));
		aul1.setTermino(sdfDate.parse("2022-05-30"));
		aul1.setOfertaDisciplina(od1);

		aulaRepository.saveAll(List.of(aul1));

		ParticipacaoAula pal1 = new ParticipacaoAula();
		pal1.setAula(aul1);
		pal1.setDiscente(dct1);

		participacaoAulaRepository.saveAll(List.of(pal1));
	}

}
