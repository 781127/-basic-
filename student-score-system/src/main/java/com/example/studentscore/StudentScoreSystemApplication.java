package com.example.studentscore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentScoreSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentScoreSystemApplication.class, args);
        System.out.println("========================================");
        System.out.println("学生成绩管理系统启动成功！");
        System.out.println("访问地址: http://localhost:8081/login");
        System.out.println("========================================");
    }
}