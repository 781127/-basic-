package com.example.studentscore.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "姓名不能为空")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "年龄不能为空")
    @Min(value = 1, message = "年龄必须大于0")
    @Max(value = 150, message = "年龄不能超过150")
    private Integer age;

    @NotBlank(message = "性别不能为空")
    @Pattern(regexp = "^[男女]$", message = "性别只能是男或女")
    private String gender;

    @NotNull(message = "成绩不能为空")
    @Min(value = 0, message = "成绩不能小于0")
    @Max(value = 100, message = "成绩不能大于100")
    private Double score;

    public Student() {}

    public Student(String name, Integer age, String gender, Double score) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.score = score;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
}