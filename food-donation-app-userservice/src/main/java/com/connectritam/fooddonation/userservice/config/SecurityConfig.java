package com.connectritam.fooddonation.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.connectritam.fooddonation.userservice.filter.JwtRequestFilter;
import com.connectritam.fooddonation.userservice.service.CustomUserDetailsService;
import com.connectritam.fooddonation.userservice.util.JwtUtil;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

        @Autowired
        private CustomUserDetailsService customUserDetailsService;

        @Autowired
        private JwtUtil jwtUtil;

        @Autowired
        private JwtRequestFilter jwtRequestFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                                .requestMatchers(
                                                                "/api/v0.1/auth/**",
                                                                "/api/v0.2/auth/**",
                                                                "/swagger-ui/**",
                                                                "/v3/api-docs/**",
                                                                "/swagger-resources/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .sessionManagement(sessionManagement -> sessionManagement
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();

                // Note : same as above
                /*
                 * public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
                 * Exception {
                 * http.csrf().disable();
                 * 
                 * http.authorizeRequests()
                 * .antMatchers("/api/v0.1/auth/**").permitAll()
                 * .antMatchers("/swagger-ui/**").permitAll()
                 * .antMatchers("/v3/api-docs/**").permitAll()
                 * .antMatchers("/swagger-resources/**").permitAll()
                 * .antMatchers("/swagger-ui.html").permitAll()
                 * .antMatchers("/webjars/**").permitAll()
                 * .antMatchers("/swagger-resources/configuration/ui").permitAll()
                 * .antMatchers("/swagger-resources/configuration/security").permitAll()
                 * .anyRequest().authenticated();
                 * 
                 * http.sessionManagement()
                 * .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                 * 
                 * return http.build();
                 * }
                 */

        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder,
                        UserDetailsService userDetailsService) throws Exception {
                AuthenticationManagerBuilder authenticationManagerBuilder = http
                                .getSharedObject(AuthenticationManagerBuilder.class);
                authenticationManagerBuilder
                                .userDetailsService(userDetailsService)
                                .passwordEncoder(passwordEncoder);

                return authenticationManagerBuilder.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}