package org.nucleodevel.sisacad.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

		Curso cur1 = new Curso("CRS01", "Engenharia da Computação");
		Curso cur2 = new Curso("CRS02", "Ciência da Computação");

		cursoRepository.saveAll(List.of(cur1, cur2));

		Disciplina dcp1 = new Disciplina("DISC001", "Compiladores");
		Disciplina dcp2 = new Disciplina("DISC002", "Banco de Dados");
		Disciplina dcp3 = new Disciplina("DISC003", "Engenharia de Software");
		Disciplina dcp4 = new Disciplina("DISC004", "Programação Orientada a Objetos");

		disciplinaRepository.saveAll(List.of(dcp1, dcp2, dcp3, dcp4));

		EstruturaCurricular ec1 = new EstruturaCurricular("EC01", 1999, 2012, cur1);
		ec1.setListDisciplina(List.of(dcp1, dcp2));
		EstruturaCurricular ec2 = new EstruturaCurricular("EC02", 2013, 2026, cur1);
		ec2.setListDisciplina(List.of(dcp1, dcp2, dcp4));
		EstruturaCurricular ec3 = new EstruturaCurricular("EC03", 2018, 2031, cur2);
		ec3.setListDisciplina(List.of(dcp1, dcp2, dcp3, dcp4));

		estruturaCurricularRepository.saveAll(List.of(ec1, ec2, ec3));

		Vestibular vst1 = new Vestibular(2021);

		vestibularRepository.saveAll(List.of(vst1));

		OfertaCurso oc1 = new OfertaCurso("OC001", 2022, ec2, vst1);
		OfertaCurso oc2 = new OfertaCurso("OC002", 2022, ec3, vst1);

		ofertaCursoRepository.saveAll(List.of(oc1, oc2));

		Turma trm1 = new Turma("TRM001", oc1);
		Turma trm2 = new Turma("TRM002", oc2);

		turmaRepository.saveAll(List.of(trm1, trm2));

		Docente doc1 = new Docente("42262698023", "João Santos");
		Docente doc2 = new Docente("57482695036", "Carlos Pereira");
		Docente doc3 = new Docente("13200083050", "Marcos Benevides");

		docenteRepository.saveAll(List.of(doc1, doc2, doc3));

		Vestibulando vnd01 = new Vestibulando("12198374072", "Rosana Martins", oc1);
		Vestibulando vnd02 = new Vestibulando("27411575020", "Osvaldo Picci", oc1);
		Vestibulando vnd03 = new Vestibulando("43169615009", "José Albertino", oc1);
		Vestibulando vnd04 = new Vestibulando("62635262039", "Kelvin Smith", oc1);
		Vestibulando vnd05 = new Vestibulando("75592129050", "Melinda Flores", oc1);
		Vestibulando vnd06 = new Vestibulando("43166274065", "Guilherme Toledo", oc2);
		Vestibulando vnd07 = new Vestibulando("58351444073", "Homero Assis", oc2);
		Vestibulando vnd08 = new Vestibulando("78151416092", "Carlos Balbuena", oc2);
		Vestibulando vnd09 = new Vestibulando("91370952007", "Teo Lee", oc2);
		Vestibulando vnd10 = new Vestibulando("40843830077", "Neusa Feltrin", oc2);

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

		OfertaDisciplina od1 = new OfertaDisciplina("OD0001", dcp1, doc1);
		od1.setListTurma(List.of(trm1));
		od1.setListDiscente(List.of(dct1, dct2, dct3, dct4));
		OfertaDisciplina od2 = new OfertaDisciplina("OD0002", dcp1, doc1);
		od2.setListTurma(List.of(trm2));
		od2.setListDiscente(List.of(dct5, dct6, dct7, dct8));
		OfertaDisciplina od3 = new OfertaDisciplina("OD0003", dcp2, doc3);
		od3.setListTurma(List.of(trm1, trm2));
		od3.setListDiscente(List.of(dct1, dct2, dct3, dct4, dct5, dct7, dct8));
		OfertaDisciplina od4 = new OfertaDisciplina("OD0004", dcp3, doc2);
		od4.setListTurma(List.of(trm2));
		od4.setListDiscente(List.of(dct5, dct6, dct7, dct8));
		OfertaDisciplina od5 = new OfertaDisciplina("OD0005", dcp4, doc2);
		od5.setListTurma(List.of(trm1, trm2));
		od5.setListDiscente(List.of(dct1, dct2, dct4, dct6, dct7, dct8));

		ofertaDisciplinaRepository.saveAll(List.of(od1, od2, od3, od4, od5));

		Avaliacao avl01 = new Avaliacao("Projeto Integrado", sdfDate.parse("2022-05-20"), sdfDate.parse("2022-06-30"),
				od1);
		Avaliacao avl02 = new Avaliacao("Prova Final", sdfDate.parse("2022-05-30"), sdfDate.parse("2022-05-30"), od1);
		Avaliacao avl03 = new Avaliacao("Prova Final", sdfDate.parse("2022-05-28"), sdfDate.parse("2022-05-28"), od2);
		Avaliacao avl04 = new Avaliacao("Projeto Integrado", sdfDate.parse("2022-04-20"), sdfDate.parse("2022-05-28"),
				od3);
		Avaliacao avl05 = new Avaliacao("Prova Final", sdfDate.parse("2022-06-02"), sdfDate.parse("2022-06-02"), od3);
		Avaliacao avl06 = new Avaliacao("Projeto Integrado", sdfDate.parse("2022-05-18"), sdfDate.parse("2022-06-18"),
				od4);
		Avaliacao avl07 = new Avaliacao("Prova Final", sdfDate.parse("2022-06-15"), sdfDate.parse("2022-06-15"), od4);
		Avaliacao avl08 = new Avaliacao("Projeto Integrado", sdfDate.parse("2022-05-01"), sdfDate.parse("2022-06-14"),
				od5);
		Avaliacao avl09 = new Avaliacao("Prova Final", sdfDate.parse("2022-06-30"), sdfDate.parse("2022-06-30"), od5);

		avaliacaoRepository.saveAll(List.of(avl01, avl02, avl03, avl04, avl05, avl06, avl07, avl08, avl09));

		List<ParticipacaoAvaliacao> listParticipacaoAvaliacao = new ArrayList<>();

		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl01, dct1));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl01, dct2));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl01, dct3));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl01, dct4));

		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl02, dct1));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl02, dct2));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl02, dct3));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl02, dct4));

		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl03, dct5));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl03, dct6));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl03, dct7));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl03, dct8));

		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl04, dct1));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl04, dct2));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl04, dct3));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl04, dct4));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl04, dct5));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl04, dct7));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl04, dct8));

		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl05, dct1));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl05, dct2));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl05, dct3));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl05, dct4));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl05, dct5));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl05, dct7));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl05, dct8));

		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl06, dct5));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl06, dct6));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl06, dct7));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl06, dct8));

		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl07, dct5));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl07, dct6));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl07, dct7));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl07, dct8));

		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl08, dct1));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl08, dct2));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl08, dct4));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl08, dct6));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl08, dct7));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl08, dct8));

		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl09, dct1));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl09, dct2));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl09, dct4));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl09, dct6));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl09, dct7));
		listParticipacaoAvaliacao.add(new ParticipacaoAvaliacao(avl09, dct8));

		participacaoAvaliacaoRepository.saveAll(listParticipacaoAvaliacao);

		Aula aul01 = new Aula(sdfDate.parse("2022-05-20 13:00:00"), sdfDate.parse("2022-05-20 18:00:00"), od1);
		Aula aul02 = new Aula(sdfDate.parse("2022-05-30 08:00:00"), sdfDate.parse("2022-05-30 12:00:00"), od1);
		Aula aul03 = new Aula(sdfDate.parse("2022-05-28 19:00:00"), sdfDate.parse("2022-05-28 19:00:00"), od2);
		Aula aul04 = new Aula(sdfDate.parse("2022-04-20 13:00:00"), sdfDate.parse("2022-04-20 16:00:00"), od3);
		Aula aul05 = new Aula(sdfDate.parse("2022-06-02 10:00:00"), sdfDate.parse("2022-06-02 13:00:00"), od3);
		Aula aul06 = new Aula(sdfDate.parse("2022-05-18 09:00:00"), sdfDate.parse("2022-05-18 12:00:00"), od4);
		Aula aul07 = new Aula(sdfDate.parse("2022-06-15 14:00:00"), sdfDate.parse("2022-06-15 17:00:00"), od4);
		Aula aul08 = new Aula(sdfDate.parse("2022-05-01 20:00:00"), sdfDate.parse("2022-05-01 22:00:00"), od5);
		Aula aul09 = new Aula(sdfDate.parse("2022-06-30 14:00:00"), sdfDate.parse("2022-06-30 19:00:00"), od5);

		aulaRepository.saveAll(List.of(aul01, aul02, aul03, aul04, aul05, aul06, aul07, aul08, aul09));

		List<ParticipacaoAula> listParticipacaoAula = new ArrayList<>();

		listParticipacaoAula.add(new ParticipacaoAula(aul01, dct1));
		// listParticipacaoAula.add(new ParticipacaoAula(aul01, dct2));
		listParticipacaoAula.add(new ParticipacaoAula(aul01, dct3));
		listParticipacaoAula.add(new ParticipacaoAula(aul01, dct4));

		listParticipacaoAula.add(new ParticipacaoAula(aul02, dct1));
		listParticipacaoAula.add(new ParticipacaoAula(aul02, dct2));
		listParticipacaoAula.add(new ParticipacaoAula(aul02, dct3));
		listParticipacaoAula.add(new ParticipacaoAula(aul02, dct4));

		listParticipacaoAula.add(new ParticipacaoAula(aul03, dct5));
		listParticipacaoAula.add(new ParticipacaoAula(aul03, dct6));
		listParticipacaoAula.add(new ParticipacaoAula(aul03, dct7));
		listParticipacaoAula.add(new ParticipacaoAula(aul03, dct8));

		listParticipacaoAula.add(new ParticipacaoAula(aul04, dct1));
		listParticipacaoAula.add(new ParticipacaoAula(aul04, dct2));
		listParticipacaoAula.add(new ParticipacaoAula(aul04, dct3));
		listParticipacaoAula.add(new ParticipacaoAula(aul04, dct4));
		listParticipacaoAula.add(new ParticipacaoAula(aul04, dct5));
		listParticipacaoAula.add(new ParticipacaoAula(aul04, dct7));
		listParticipacaoAula.add(new ParticipacaoAula(aul04, dct8));

		listParticipacaoAula.add(new ParticipacaoAula(aul05, dct1));
		listParticipacaoAula.add(new ParticipacaoAula(aul05, dct2));
		listParticipacaoAula.add(new ParticipacaoAula(aul05, dct3));
		listParticipacaoAula.add(new ParticipacaoAula(aul05, dct4));
		listParticipacaoAula.add(new ParticipacaoAula(aul05, dct5));
		listParticipacaoAula.add(new ParticipacaoAula(aul05, dct7));
		listParticipacaoAula.add(new ParticipacaoAula(aul05, dct8));

		listParticipacaoAula.add(new ParticipacaoAula(aul06, dct5));
		listParticipacaoAula.add(new ParticipacaoAula(aul06, dct6));
		listParticipacaoAula.add(new ParticipacaoAula(aul06, dct7));
		listParticipacaoAula.add(new ParticipacaoAula(aul06, dct8));

		listParticipacaoAula.add(new ParticipacaoAula(aul07, dct5));
		listParticipacaoAula.add(new ParticipacaoAula(aul07, dct6));
		listParticipacaoAula.add(new ParticipacaoAula(aul07, dct7));
		listParticipacaoAula.add(new ParticipacaoAula(aul07, dct8));

		listParticipacaoAula.add(new ParticipacaoAula(aul08, dct1));
		listParticipacaoAula.add(new ParticipacaoAula(aul08, dct2));
		listParticipacaoAula.add(new ParticipacaoAula(aul08, dct4));
		listParticipacaoAula.add(new ParticipacaoAula(aul08, dct6));
		listParticipacaoAula.add(new ParticipacaoAula(aul08, dct7));
		listParticipacaoAula.add(new ParticipacaoAula(aul08, dct8));

		listParticipacaoAula.add(new ParticipacaoAula(aul09, dct1));
		listParticipacaoAula.add(new ParticipacaoAula(aul09, dct2));
		listParticipacaoAula.add(new ParticipacaoAula(aul09, dct4));
		listParticipacaoAula.add(new ParticipacaoAula(aul09, dct6));
		listParticipacaoAula.add(new ParticipacaoAula(aul09, dct7));
		listParticipacaoAula.add(new ParticipacaoAula(aul09, dct8));

		participacaoAulaRepository.saveAll(listParticipacaoAula);
	}

}
