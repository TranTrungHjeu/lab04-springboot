package com.iuh.lab04springboot.controller;

import com.iuh.lab04springboot.model.Student;
import com.iuh.lab04springboot.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("students", service.getAll());
        return "students";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") Student student, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "student-form";
        }
        boolean isNew = student.getId() == null;
        service.save(student);

        if (isNew) {
            redirectAttributes.addFlashAttribute("message", "Thêm sinh viên thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin sinh viên thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        }

        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Student student = service.getById(id);
        if (student != null) {
            model.addAttribute("student", student);
            return "student-form";
        }
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        service.delete(id);
        redirectAttributes.addFlashAttribute("message", "Xóa sinh viên thành công!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/students";
    }
}
