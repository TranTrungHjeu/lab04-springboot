package com.iuh.lab04springboot.controller;

import com.iuh.lab04springboot.model.Enrollment;
import com.iuh.lab04springboot.model.Student;
import com.iuh.lab04springboot.model.Subject;
import com.iuh.lab04springboot.service.EnrollmentService;
import com.iuh.lab04springboot.service.StudentService;
import com.iuh.lab04springboot.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("enrollments", enrollmentService.getAll());
        return "enrollments";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("enrollment", new Enrollment());
        model.addAttribute("students", studentService.getAll());
        model.addAttribute("subjects", subjectService.getAll());
        return "enrollment-form";
    }

    @PostMapping("/save")
    public String save(@RequestParam("studentId") Integer studentId,
                       @RequestParam("subjectId") Integer subjectId,
                       @RequestParam("semester") String semester,
                       RedirectAttributes redirectAttributes) {

        Student student = studentService.getById(studentId);
        Subject subject = subjectService.getById(subjectId);

        if (student == null || subject == null) {
            redirectAttributes.addFlashAttribute("message", "Sinh viên hoặc môn học không tồn tại!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/enrollments/new";
        }

        if (enrollmentService.existsByStudentAndSubject(student, subject)) {
            redirectAttributes.addFlashAttribute("message", "Sinh viên đã đăng ký môn học này!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/enrollments/new";
        }

        Enrollment enrollment = new Enrollment(student, subject, semester);
        enrollmentService.save(enrollment);

        redirectAttributes.addFlashAttribute("message", "Đăng ký môn học thành công!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/enrollments";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        enrollmentService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Hủy đăng ký môn học thành công!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/enrollments";
    }

    @GetMapping("/student/{studentId}")
    public String viewByStudent(@PathVariable Integer studentId, Model model) {
        Student student = studentService.getById(studentId);
        if (student != null) {
            model.addAttribute("student", student);
            model.addAttribute("enrollments", enrollmentService.getByStudent(student));
            return "student-enrollments";
        }
        return "redirect:/students";
    }
}
