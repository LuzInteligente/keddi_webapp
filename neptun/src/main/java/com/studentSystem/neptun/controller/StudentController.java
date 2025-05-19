package com.studentSystem.neptun.controller;

import com.studentSystem.neptun.auth.JwtUtil;
import com.studentSystem.neptun.model.Student;
import com.studentSystem.neptun.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> registerAndLogin(@RequestBody Student student) {
        // Mentés adatbázisba
        studentService.saveStudent(student);

        // JWT token generálás
        String token = jwtUtil.generateToken(student.getName(), String.valueOf(student.getRole()));

        // Válasz összeállítása
        Map<String, String> response = new HashMap<>();
        response.put("message", "User created and logged in");
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public List<Student> getAllStudents() {
        try {
            return studentService.getAllStudents();
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Student loginData) {
        Optional<Student> student = studentService.authenticate(loginData.getName(), loginData.getPassword());

        if (student.isPresent()) {
            String token = jwtUtil.generateToken(student.get().getName(), String.valueOf(student.get().getRole()));

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>("Token hiányzik", HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            return new ResponseEntity<>("Érvénytelen token", HttpStatus.UNAUTHORIZED);
        }

        String role = jwtUtil.extractUserRole(token);
        if (!"TEACHER".equalsIgnoreCase(role)) {
            return new ResponseEntity<>("Nincs jogosultság a törléshez", HttpStatus.FORBIDDEN);
        }

        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return new ResponseEntity<>("Felhasználó sikeresen törölve", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Felhasználó nem található", HttpStatus.NOT_FOUND);
        }
    }


}
