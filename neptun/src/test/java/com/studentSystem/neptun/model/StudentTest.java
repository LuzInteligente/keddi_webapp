package com.studentSystem.neptun.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StudentTest {

    @Test
    public void testStudentGetterSetter() {
        // Arrange
        Student student = new Student();
        student.setId(1);
        student.setName("John");
        student.setPassword("pass123");
        student.setRole(Student.Role.valueOf("STUDENT"));

        // Act
        int id = student.getId();
        String name = student.getName();
        String password = student.getPassword();
        String role = String.valueOf(student.getRole());

        // Assert
        Assertions.assertEquals(1, id);
        Assertions.assertEquals("John", name);
        Assertions.assertEquals("pass123", password);
        Assertions.assertEquals("STUDENT", role);
    }
}