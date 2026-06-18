package com.college.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig {

    @Value("${app.api.key:}")
    private String apiKey;

    @Value("${management.security.user.name:admin}")
    private String managementUser;

    @Value("${management.security.user.password:password}")
    private String managementPassword;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, Environment env) throws Exception {
        // If an API key is configured, apply the filter
        if (apiKey != null && !apiKey.isBlank()) {
            http.addFilterBefore(new ApiKeyFilter(apiKey), UsernamePasswordAuthenticationFilter.class);
        }

        // If running with 'prod' profile, require a non-default management password to avoid insecure startup
        boolean prodActive = false;
        for (String p : env.getActiveProfiles()) {
            if ("prod".equalsIgnoreCase(p)) {
                prodActive = true;
                break;
            }
        }
        if (prodActive) {
            if (managementPassword == null || managementPassword.isBlank() || "password".equals(managementPassword)) {
                throw new IllegalStateException("Production requires a strong management password. Set 'management.security.user.password' (env: MANAGEMENT_SECURITY_USER_PASSWORD) before starting.");
            }
        }

        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**").authenticated()
                .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername(managementUser)
                .password(passwordEncoder().encode(managementPassword))
                .roles("ACTUATOR")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
