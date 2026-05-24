package com.example.studentscore.controller;

import com.example.studentscore.entity.Student;
import com.example.studentscore.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // 登录拦截检查
    private boolean checkLogin(HttpSession session) {
        return session.getAttribute("admin") != null;
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        if (!checkLogin(session)) {
            return "redirect:/login";
        }

        List<Student> students = studentRepository.findAllByOrderByScoreDesc();
        model.addAttribute("students", students);
        model.addAttribute("student", new Student());
        return "index";
    }

    // 添加学生
    @PostMapping("/add")
    public String addStudent(@Valid @ModelAttribute Student student,
                             BindingResult result,
                             Model model,
                             HttpSession session) {
        if (!checkLogin(session)) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            List<Student> students = studentRepository.findAllByOrderByScoreDesc();
            model.addAttribute("students", students);
            model.addAttribute("error", "添加失败：" + result.getAllErrors().get(0).getDefaultMessage());
            return "index";
        }

        studentRepository.save(student);
        return "redirect:/index";
    }

    // 删除学生
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, HttpSession session) {
        if (!checkLogin(session)) {
            return "redirect:/login";
        }

        studentRepository.deleteById(id);
        return "redirect:/index";
    }

    // 获取修改表单
    @GetMapping("/edit/{id}")
    @ResponseBody
    public Student editStudent(@PathVariable Long id, HttpSession session) {
        if (!checkLogin(session)) {
            return null;
        }
        return studentRepository.findById(id).orElse(null);
    }

    // 更新学生信息
    @PostMapping("/update")
    public String updateStudent(@Valid @ModelAttribute Student student,
                                BindingResult result,
                                HttpSession session,
                                Model model) {
        if (!checkLogin(session)) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            List<Student> students = studentRepository.findAllByOrderByScoreDesc();
            model.addAttribute("students", students);
            model.addAttribute("updateError", "更新失败：" + result.getAllErrors().get(0).getDefaultMessage());
            model.addAttribute("student", new Student());
            return "index";
        }

        studentRepository.save(student);
        return "redirect:/index";
    }

    // 搜索功能
    @PostMapping("/search")
    public String searchStudent(@RequestParam String keyword,
                                Model model,
                                HttpSession session) {
        if (!checkLogin(session)) {
            return "redirect:/login";
        }

        if (keyword == null || keyword.trim().isEmpty()) {
            return "redirect:/index";
        }

        List<Student> students = studentRepository.searchByAnyField(keyword.trim());
        model.addAttribute("students", students);
        model.addAttribute("student", new Student());
        model.addAttribute("searchKeyword", keyword);
        return "index";
    }
}