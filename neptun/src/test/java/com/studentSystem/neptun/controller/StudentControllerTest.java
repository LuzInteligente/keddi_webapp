package com.studentSystem.neptun.controller;

import com.studentSystem.neptun.model.Student;
import com.studentSystem.neptun.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class StudentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }
    @Test
    public void testAddStudent() throws Exception {
        Student student = new Student();
        student.setName("John");
        student.setRole(Student.Role.valueOf("STUDENT"));

        when(studentService.saveStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/student/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"department\": \"Pti\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Student successfully added"));

        verify(studentService, times(1)).saveStudent(any(Student.class));
    }


    @Test
    public void testGetAllStudents() throws Exception {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student());
        studentList.get(0).setName("Alice");
        studentList.get(0).setRole(Student.Role.valueOf("STUDENT"));
        studentList.add(new Student());
        studentList.get(1).setName("Bob");
        studentList.get(1).setRole(Student.Role.valueOf("TEACHER"));

        when(studentService.getAllStudents()).thenReturn(studentList);

        mockMvc.perform(get("/student/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[0].role").value("STUDENT"))
                .andExpect(jsonPath("$[1].name").value("Bob"))
                .andExpect(jsonPath("$[1].role").value("TEACHER"));

        verify(studentService, times(1)).getAllStudents();
    }
}