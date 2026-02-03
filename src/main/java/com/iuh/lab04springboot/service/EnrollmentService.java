package com.iuh.lab04springboot.service;

import com.iuh.lab04springboot.model.Enrollment;
import com.iuh.lab04springboot.model.Student;
import com.iuh.lab04springboot.model.Subject;
import com.iuh.lab04springboot.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository repository;

    public List<Enrollment> getAll() {
        return repository.findAll();
    }

    public Enrollment getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Enrollment> getByStudent(Student student) {
        return repository.findByStudent(student);
    }

    public List<Enrollment> getBySubject(Subject subject) {
        return repository.findBySubject(subject);
    }

    public Enrollment save(Enrollment enrollment) {
        return repository.save(enrollment);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public boolean existsByStudentAndSubject(Student student, Subject subject) {
        return repository.existsByStudentAndSubject(student, subject);
    }
}
