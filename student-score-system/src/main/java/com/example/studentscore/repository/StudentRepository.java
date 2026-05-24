package com.example.studentscore.repository;

import com.example.studentscore.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // 按成绩降序排序
    List<Student> findAllByOrderByScoreDesc();

    // 根据姓名查询
    List<Student> findByNameContaining(String name);

    // 根据年龄查询
    List<Student> findByAge(Integer age);

    // 根据性别查询
    List<Student> findByGender(String gender);

    // 根据成绩范围查询
    List<Student> findByScoreBetween(Double min, Double max);

    // 综合查询（姓名或年龄或性别或成绩）
    @Query("SELECT s FROM Student s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(s.age AS string) LIKE CONCAT('%', :keyword, '%') OR " +
            "s.gender LIKE CONCAT('%', :keyword, '%') OR " +
            "CAST(s.score AS string) LIKE CONCAT('%', :keyword, '%')")
    List<Student> searchByAnyField(@Param("keyword") String keyword);
}