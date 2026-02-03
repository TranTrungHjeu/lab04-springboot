package com.iuh.lab04springboot.controller;

import com.iuh.lab04springboot.model.Subject;
import com.iuh.lab04springboot.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    private SubjectService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("subjects", service.getAll());
        return "subjects";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "subject-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("subject") Subject subject, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "subject-form";
        }

        boolean isNew = subject.getId() == null;
        service.save(subject);

        if (isNew) {
            redirectAttributes.addFlashAttribute("message", "Thêm môn học thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Cập nhật môn học thành công!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        }

        return "redirect:/subjects";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Subject subject = service.getById(id);
        if (subject != null) {
            model.addAttribute("subject", subject);
            return "subject-form";
        }
        return "redirect:/subjects";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        service.delete(id);
        redirectAttributes.addFlashAttribute("message", "Xóa môn học thành công!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/subjects";
    }
}
