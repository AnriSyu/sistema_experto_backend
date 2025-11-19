package com.fet.controller;

import com.fet.model.DiagnosisResult;
import com.fet.model.Metric;
import com.fet.model.Student;
import com.fet.repository.MetricRepository;
import com.fet.repository.StudentRepository;
import com.fet.service.RuleEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class DiagnosisController {

    private final RuleEngineService ruleEngineService;
    private final StudentRepository studentRepository;
    private final MetricRepository metricRepository;

    public DiagnosisController(RuleEngineService ruleEngineService,
                               StudentRepository studentRepository,
                               MetricRepository metricRepository) {
        this.ruleEngineService = ruleEngineService;
        this.studentRepository = studentRepository;
        this.metricRepository = metricRepository;
    }



    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student saved = studentRepository.save(student);
        return ResponseEntity.ok(saved);
    }


    @PostMapping("/students/{id}/metrics")
    public ResponseEntity<List<DiagnosisResult>> submitMetrics(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> answers = (List<Map<String, Object>>) payload.get("answers");

        List<Metric> metrics = answers.stream().map(a -> {
            Metric m = new Metric();
            m.setExerciseNumber((int) a.get("exerciseNumber"));
            m.setAnswer((String) a.get("answer"));
            m.setStudent(student);
            return m;
        }).toList();

        metricRepository.saveAll(metrics);

        List<DiagnosisResult> results = ruleEngineService.evaluateAll(student, metrics);

        return ResponseEntity.ok(results);
    }




    @GetMapping("/students/{id}/diagnoses")
    public ResponseEntity<List<DiagnosisResult>> getDiagnoses(@PathVariable Long id) {
        List<DiagnosisResult> history = studentRepository.findById(id)
                .map(Student::getDiagnoses)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return ResponseEntity.ok(history);
    }
}
