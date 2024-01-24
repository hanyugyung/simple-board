package org.example.han.common.config;

import org.example.han.common.auth.JwtAuthFilter;
import org.example.han.common.util.Utils;
import org.example.han.interfaces.CommonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.secret-key}")
    private String key;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/api-docs/**")
                , new AntPathRequestMatcher("/api-docs")
                , new AntPathRequestMatcher("/swagger-ui/**")
                , new AntPathRequestMatcher("/h2-console/**")
                , new AntPathRequestMatcher("/h2-console")
                , new AntPathRequestMatcher("/favicon.ico")
                , new AntPathRequestMatcher("/api/users/login")
                , new AntPathRequestMatcher("/api/users/sign-up")
                , new AntPathRequestMatcher("/upload-file/**")
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authorizeHttpRequests) ->
                    authorizeHttpRequests
                        .requestMatchers(
                                new AntPathRequestMatcher("/api/boards")
                                , new AntPathRequestMatcher("/api/boards/**")
                                , new AntPathRequestMatcher("/api/users/my-page")
                        ).hasRole("user")
                        .anyRequest().permitAll()
                )
                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin(withDefaults())
                .sessionManagement(
                    (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthFilter(key), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((handling) ->
                    handling
                            .authenticationEntryPoint(((request, response, authException) -> {
                                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                response.setContentType("application/json");
                                Utils.getObjectMapper().writeValue(
                                        response.getOutputStream(), CommonResponse.successWithError(CommonResponse.CustomError.USER_FAIL_AUTHORIZATION));
                            }))
                            .accessDeniedHandler(((request, response, accessDeniedException) -> {
                                response.setStatus(HttpStatus.FORBIDDEN.value());
                                response.setContentType("application/json");
                                Utils.getObjectMapper().writeValue(
                                        response.getOutputStream(), CommonResponse.successWithError(CommonResponse.CustomError.USER_FAIL_ACCESS));
                            }))
                )
                .build();
    }

}
