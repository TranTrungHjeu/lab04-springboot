package com.iuh.lab04springboot.service;

import com.iuh.lab04springboot.model.Subject;
import com.iuh.lab04springboot.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository repository;

    public List<Subject> getAll() {
        return repository.findAll();
    }

    public Subject getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Subject getByCode(String code) {
        return repository.findByCode(code).orElse(null);
    }

    public Subject save(Subject subject) {
        return repository.save(subject);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }
}
