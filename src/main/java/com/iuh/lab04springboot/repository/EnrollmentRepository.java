package com.iuh.lab04springboot.repository;

import com.iuh.lab04springboot.model.Enrollment;
import com.iuh.lab04springboot.model.Student;
import com.iuh.lab04springboot.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    List<Enrollment> findByStudent(Student student);
    List<Enrollment> findBySubject(Subject subject);
    Optional<Enrollment> findByStudentAndSubject(Student student, Subject subject);
    boolean existsByStudentAndSubject(Student student, Subject subject);
}
