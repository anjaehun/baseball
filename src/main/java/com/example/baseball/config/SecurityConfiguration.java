package com.example.baseball.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    protected SecurityFilterChain sequrityFilterChain(HttpSecurity http) throws Exception {


        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/team/list").permitAll()
                .antMatchers("/api/v1/team/list/**").permitAll()
                .antMatchers("/api/v1/hitter/**").permitAll()
                .antMatchers("/api/v1/pitcher/**").permitAll()
                .antMatchers("/api/v1/team/teamMember/list/**").permitAll()
                .antMatchers("/api/v1/team/teamMember/{teamId}/membership/application").hasAuthority("USER")
                .antMatchers("/api/v1/team/create").hasAuthority("USER")
                .antMatchers("/api/v1/stadium/create").hasAuthority("STADIUM_USER")
                 // 여기에 경로와 권한 설정 추가
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
