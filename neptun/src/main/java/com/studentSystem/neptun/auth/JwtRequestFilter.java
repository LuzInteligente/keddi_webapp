package com.studentSystem.neptun.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtRequestFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();
        String method = req.getMethod();

        if ("OPTIONS".equalsIgnoreCase(method)) {
            res.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
            return;
        }

        if (path.equals("/student/add") || path.equals("/student/login")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.isTokenValid(token)) {
                chain.doFilter(request, response);
                return;
            }
        }

        res.sendError(HttpServletResponse.SC_FORBIDDEN, "Missing or invalid JWT");
    }
}