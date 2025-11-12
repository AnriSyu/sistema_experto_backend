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
    public ResponseEntity<DiagnosisResult> submitMetrics(
            @PathVariable Long id,
            @RequestBody List<Metric> metrics) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        metrics.forEach(m -> m.setStudent(student));
        metricRepository.saveAll(metrics);

        DiagnosisResult result = ruleEngineService.evaluate(student, metrics);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/students/{id}/diagnoses")
    public ResponseEntity<List<DiagnosisResult>> getDiagnoses(@PathVariable Long id) {
        List<DiagnosisResult> history = studentRepository.findById(id)
                .map(Student::getDiagnoses)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return ResponseEntity.ok(history);
    }
}
