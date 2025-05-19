package com.studentSystem.neptun.service;

import com.studentSystem.neptun.model.Student;
import com.studentSystem.neptun.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class StudentServiceImplementationTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImplementation studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveStudent() {
        // Arrange
        Student student = new Student();
        student.setName("John");
        student.setPassword("pass123");
        student.setRole(Student.Role.valueOf("TEACHER"));

        when(studentRepository.save(student)).thenReturn(student);

        // Act
        Student savedStudent = studentService.saveStudent(student);

        // Assert
        verify(studentRepository, times(1)).save(student);
        assert savedStudent != null;
        assert savedStudent.getName().equals("John");
        assert savedStudent.getPassword().equals("pass123");
        assert savedStudent.getRole().equals("TEACHER");
    }
    @Test
    public void testGetAllStudents() {
        // Arrange
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student());
        studentList.get(0).setName("Alice");
        studentList.get(0).setPassword("pass456");
        studentList.get(0).setRole(Student.Role.valueOf("TEACHER"));
        studentList.add(new Student());
        studentList.get(1).setName("Bob");
        studentList.get(1).setPassword("pass789");
        studentList.get(1).setRole(Student.Role.valueOf("STUDENT"));

        when(studentRepository.findAll()).thenReturn(studentList);

        // Act
        List<Student> retrievedStudents = studentService.getAllStudents();

        // Assert
        verify(studentRepository, times(1)).findAll();
        assert retrievedStudents != null;
        assert retrievedStudents.size() == 2;
        assert retrievedStudents.get(0).getName().equals("Alice");
        assert retrievedStudents.get(1).getName().equals("Bob");
    }
}