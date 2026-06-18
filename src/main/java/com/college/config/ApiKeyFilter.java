package com.college.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Objects;

public class ApiKeyFilter extends OncePerRequestFilter {

    private final String configuredKey;

    public ApiKeyFilter(String configuredKey) {
        this.configuredKey = configuredKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (configuredKey == null || configuredKey.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("X-API-KEY");
        if (Objects.equals(configuredKey, header)) {
            filterChain.doFilter(request, response);
            return;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"Unauthorized\"}");
    }
}
