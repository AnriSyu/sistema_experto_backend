package com.fet.service;

import com.fet.model.DiagnosisResult;
import com.fet.model.Metric;
import com.fet.model.Rule;
import com.fet.model.Student;
import com.fet.repository.DiagnosisResultRepository;
import com.fet.repository.RuleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RuleEngineService {

    private final DiagnosisResultRepository diagnosisRepository;
    private final RuleRepository ruleRepository;

    public RuleEngineService(DiagnosisResultRepository diagnosisRepository,
                             RuleRepository ruleRepository) {
        this.diagnosisRepository = diagnosisRepository;
        this.ruleRepository = ruleRepository;
    }

    public List<DiagnosisResult> evaluateAll(Student student, List<Metric> metrics) {
        List<Rule> rules = ruleRepository.findAll();

        return rules.stream()
                .map(rule -> {
                    int exerciseNumber = mapRuleToExercise(rule.getCondition());
                    String expectedAnswer = expectedAnswerForRule(rule.getCondition()); // "yes" o "no"

                    Metric metric = metrics.stream()
                            .filter(m -> m.getExerciseNumber() == exerciseNumber)
                            .findFirst()
                            .orElse(null);

                    if (metric != null && expectedAnswer.equalsIgnoreCase(metric.getAnswer())) {
                        DiagnosisResult dr = new DiagnosisResult();
                        dr.setStudent(student);
                        dr.setDate(LocalDateTime.now());
                        dr.setDifficultyLevel(rule.getDifficulty());
                        dr.setRecommendation(rule.getRecommendation());
                        diagnosisRepository.save(dr);
                        return dr;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private String expectedAnswerForRule(String condition) {
        return switch (condition) {
            case "progressBetweenAttempts" -> "no"; // solo esta regla espera "no"
            default -> "yes"; // todas las demás esperan "yes"
        };
    }



    private int mapRuleToExercise(String condition) {
        return switch (condition) {
            case "syntaxLogicErrors" -> 1;
            case "activityMotivation" -> 2;
            case "resolutionTime" -> 3;
            case "progressBetweenAttempts" -> 4;
            case "experimentationPattern" -> 5;
            case "practiceVsTheory" -> 6;
            case "evaluationAnxiety" -> 7;
            case "noProgress" -> 8;
            default -> 0;
        };
    }


}
