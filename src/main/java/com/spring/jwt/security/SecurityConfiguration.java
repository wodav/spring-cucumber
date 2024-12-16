package com.spring.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalAuthentication
public class SecurityConfiguration {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    private static final String[] WHITE_LIST_URL = {"/h2-console/**", "/login", "/register"};
    private static final String[] WHITE_LIST_URL_USER = {"/users/**","suggestions/**"};
    private static final String[] WHITE_LIST_URL_ADMIN = {"/users-by-username/**"};
    private static final String[] WHITE_LIST_URL_MODERATOR = {"/admin/**"};
//TODO: DOC first allow authorities here and then close in restcontroller. No double configuration of endpoints. if you want two roles use || or user rolehierachy bean
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (request) -> request
                                .requestMatchers(WHITE_LIST_URL).permitAll()
                                .requestMatchers(WHITE_LIST_URL_USER).hasRole("USER")
                                .requestMatchers(WHITE_LIST_URL_ADMIN).hasRole("ADMIN")
                                .requestMatchers(WHITE_LIST_URL_MODERATOR).hasRole("MODERATOR")
                        )
                       // .requestMatchers(WHITE_LIST_URL_USER).access(new WebExpressionAuthorizationManager("hasRole('USER') && hasAuthority('CONFIRMED')")))


                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(
                        authenticationJwtTokenFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );

        return httpSecurity.build();
    }

    @Bean
    static RoleHierarchy roleHierarchy(){
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("MODERATOR")
                .role("MODERATOR").implies("USER")
             //   .role("ADMIN").implies("MODERATOR")
                .build();
    }

    /*
    // Our public endpoints
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/author/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/author/search").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/book/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/book/search").permitAll()
     */

}
