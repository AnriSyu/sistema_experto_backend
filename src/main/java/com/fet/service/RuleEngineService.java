package com.fet.service;

import com.fet.model.DiagnosisResult;
import com.fet.model.Metric;
import com.fet.model.Rule;
import com.fet.model.Student;
import com.fet.repository.DiagnosisResultRepository;
import com.fet.repository.RuleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RuleEngineService {

    private final DiagnosisResultRepository diagnosisRepository;
    private final RuleRepository ruleRepository;

    public RuleEngineService(DiagnosisResultRepository diagnosisRepository,
                             RuleRepository ruleRepository) {
        this.diagnosisRepository = diagnosisRepository;
        this.ruleRepository = ruleRepository;
    }

    public DiagnosisResult evaluate(Student student, List<Metric> metrics) {
        DiagnosisResult result = new DiagnosisResult();
        result.setStudent(student);
        result.setDate(LocalDateTime.now());

        List<Rule> rules = ruleRepository.findAll();

        for (Rule rule : rules) {
            if (rule.getCondition().equals("errors>=3")) {
                boolean match = metrics.stream().anyMatch(m -> m.getErrors() >= 3);
                if (match) {
                    result.setDifficultyLevel(rule.getDifficulty());
                    result.setRecommendation(rule.getRecommendation());
                    break;
                }
            }
        }

        diagnosisRepository.save(result);
        return result;
    }
}
