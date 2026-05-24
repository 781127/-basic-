package com.example.studentscore.controller;

import com.example.studentscore.entity.Admin;
import com.example.studentscore.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("admin", new Admin());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Admin admin = adminRepository.findByUsername(username).orElse(null);

        if (admin != null && admin.getPassword().equals(password)) {
            session.setAttribute("admin", admin);
            return "redirect:/index";
        } else {
            model.addAttribute("error", "用户名或密码错误！");
            model.addAttribute("admin", new Admin());
            return "login";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           Model model) {
        // 验证密码是否一致
        if (!password.equals(confirmPassword)) {
            model.addAttribute("registerError", "两次输入的密码不一致！");
            model.addAttribute("admin", new Admin());
            return "login";
        }

        // 验证用户名是否已存在
        if (adminRepository.existsByUsername(username)) {
            model.addAttribute("registerError", "用户名已存在！");
            model.addAttribute("admin", new Admin());
            return "login";
        }

        // 注册新用户
        Admin newAdmin = new Admin(username, password);
        adminRepository.save(newAdmin);
        model.addAttribute("success", "注册成功，请登录！");
        model.addAttribute("admin", new Admin());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}