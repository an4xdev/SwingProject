package edu.uws.ii.project.configurations;

import edu.uws.ii.project.services.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/styles/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/403").permitAll()
                        .requestMatchers("/404").permitAll()
                        .requestMatchers("/500").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/search").permitAll()
                )
                .authorizeHttpRequests((requests -> requests
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/recipes/**").authenticated()
                        .requestMatchers("/user/**").authenticated()
                        .requestMatchers("/profile/**").authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .exceptionHandling((exceptions) -> exceptions
                        .accessDeniedPage("/403")
                )
                .userDetailsService(userDetailsService);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}