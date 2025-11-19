package com.fet.component;

import com.fet.model.Metric;
import com.fet.model.Rule;
import com.fet.model.Student;
import com.fet.repository.MetricRepository;
import com.fet.repository.RuleRepository;
import com.fet.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RuleRepository ruleRepository;
    private final StudentRepository studentRepository;
    private final MetricRepository metricRepository;

    public DataLoader(RuleRepository ruleRepository, StudentRepository studentRepository, MetricRepository metricRepository) {
        this.ruleRepository = ruleRepository;
        this.studentRepository = studentRepository;
        this.metricRepository = metricRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (ruleRepository.count() == 0) {

            Rule r1 = new Rule("syntaxLogicErrors", "Persistente", "Repaso guiado con tutor y ejercicios de pseudocódigo.");
            Rule r2 = new Rule("activityMotivation", "Persistente", "Generar alerta de baja motivación y sesión de orientación.");
            Rule r3 = new Rule("resolutionTime", "Persistente", "Recomendar ejercicios de pseudocódigo o diagramas de flujo.");
            Rule r4 = new Rule("progressBetweenAttempts", "Leve", "Mantener seguimiento normal.");
            Rule r5 = new Rule("experimentationPattern", "Leve", "Aprendizaje activo, sin intervención inmediata.");
            Rule r6 = new Rule("practiceVsTheory", "Leve", "Reforzar lectura conceptual y evaluación escrita.");
            Rule r7 = new Rule("evaluationAnxiety", "Leve", "Evaluación alternativa sin límite de tiempo y apoyo emocional.");
            Rule r8 = new Rule("noProgress", "Crítica", "Derivar a apoyo psicopedagógico.");

            ruleRepository.save(r1);
            ruleRepository.save(r2);
            ruleRepository.save(r3);
            ruleRepository.save(r4);
            ruleRepository.save(r5);
            ruleRepository.save(r6);
            ruleRepository.save(r7);
            ruleRepository.save(r8);
        }

        if (studentRepository.count() == 0) {
            Student s1 = new Student();
            s1.setName("Juan Pérez");
            s1.setEmail("juan@correo.com");
            studentRepository.save(s1);

            Student s2 = new Student();
            s2.setName("María Gómez");
            s2.setEmail("maria@correo.com");
            studentRepository.save(s2);

            Student s3 = new Student();
            s3.setName("Carlos López");
            s3.setEmail("carlos@correo.com");
            studentRepository.save(s3);

            // Crear métricas simuladas con "yes"/"no"
            Metric m1 = new Metric();
            m1.setStudent(s1);
            m1.setExerciseNumber(1);
            m1.setAnswer("no");
            metricRepository.save(m1);

            Metric m2 = new Metric();
            m2.setStudent(s2);
            m2.setExerciseNumber(1);
            m2.setAnswer("yes");
            metricRepository.save(m2);

            Metric m3 = new Metric();
            m3.setStudent(s3);
            m3.setExerciseNumber(1);
            m3.setAnswer("no");
            metricRepository.save(m3);

            // Métricas adicionales para casos frontera
            Metric m4 = new Metric();
            m4.setStudent(s2);
            m4.setExerciseNumber(2);
            m4.setAnswer("no");
            metricRepository.save(m4);

            Metric m5 = new Metric();
            m5.setStudent(s3);
            m5.setExerciseNumber(2);
            m5.setAnswer("yes");
            metricRepository.save(m5);
        }

        System.out.println("Setup inicial completado: reglas, estudiantes y métricas cargados, incluyendo casos frontera.");
    }
}
