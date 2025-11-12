package com.fet.component;

import com.fet.model.Rule;
import com.fet.model.Student;
import com.fet.repository.RuleRepository;
import com.fet.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RuleRepository ruleRepository;
    private final StudentRepository studentRepository;

    public DataLoader(RuleRepository ruleRepository, StudentRepository studentRepository) {
        this.ruleRepository = ruleRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (ruleRepository.count() == 0) {
            Rule r1 = new Rule();
            r1.setCondition("errors>=3");
            r1.setDifficulty("Persistente");
            r1.setRecommendation("Repaso guiado con tutor y ejercicios de pseudocódigo.");
            ruleRepository.save(r1);

            Rule r2 = new Rule();
            r2.setCondition("not_completed>50%");
            r2.setDifficulty("Persistente");
            r2.setRecommendation("Generar alerta de baja motivación y sesión de orientación.");
            ruleRepository.save(r2);

            Rule r3 = new Rule();
            r3.setCondition("timeSpent>200%");
            r3.setDifficulty("Persistente");
            r3.setRecommendation("Recomendar ejercicios de pseudocódigo o diagramas de flujo.");
            ruleRepository.save(r3);

            Rule r4 = new Rule();
            r4.setCondition("progressive_improvement");
            r4.setDifficulty("Leve");
            r4.setRecommendation("Mantener seguimiento normal.");
            ruleRepository.save(r4);

            Rule r5 = new Rule();
            r5.setCondition("high_attempts_distinct_errors");
            r5.setDifficulty("Leve");
            r5.setRecommendation("Aprendizaje activo, sin intervención inmediata.");
            ruleRepository.save(r5);

            Rule r6 = new Rule();
            r6.setCondition("good_practical_low_theory");
            r6.setDifficulty("Leve");
            r6.setRecommendation("Reforzar lectura conceptual y evaluación escrita.");
            ruleRepository.save(r6);

            Rule r7 = new Rule();
            r7.setCondition("theory_but_anxiety");
            r7.setDifficulty("Leve");
            r7.setRecommendation("Evaluación alternativa sin límite de tiempo y apoyo emocional.");
            ruleRepository.save(r7);

            Rule r8 = new Rule();
            r8.setCondition("no_progress_3_weeks");
            r8.setDifficulty("Crítica");
            r8.setRecommendation("Derivar a apoyo psicopedagógico.");
            ruleRepository.save(r8);
        }

        if (studentRepository.count() == 0) {
            Student s = new Student();
            s.setName("Juan Pérez");
            s.setEmail("juan@correo.com");
            studentRepository.save(s);
        }

        System.out.println("Setup inicial completado: reglas y estudiante cargados.");
    }
}
