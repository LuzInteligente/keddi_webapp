package com.studentSystem.neptun.service;

import com.studentSystem.neptun.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    public Student saveStudent(Student student);

    public List<Student> getAllStudents();

    Optional<Student> authenticate(String name, String password);

    boolean deleteStudent(int id);
}
