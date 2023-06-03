package ca.sheridancollege.musleho.ShineTennis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired UserDetailsServiceImpl userDetailsService;
    @Autowired LoginAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //////////////////////////////////////////
        //DANGER REMOVE BEFORE PROD///////////////
        http.csrf().disable();
        http.headers().frameOptions().disable();
        //////////////////////////////////////////
        http
            .authorizeHttpRequests((authz) -> authz
            .antMatchers("/book").hasRole("VENDOR")
            .antMatchers("/edit/**").hasAnyRole("VENDOR")
            .antMatchers(HttpMethod.GET, "/delete/**").hasRole("VENDOR")
            .antMatchers(HttpMethod.GET, "/uranerd").hasRole("VENDOR")
            .antMatchers(HttpMethod.GET, "/view").hasAnyRole("VENDOR", "GUEST")
            .antMatchers("/","/register/**","/h2-console/**").permitAll()
            .antMatchers("/assets/**", "/scripts/**", "/").permitAll()
            .anyRequest().authenticated())
            .formLogin()
                .loginPage("/login")
                .successForwardUrl("/")
                .permitAll()
            .and()
            .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            .and()
            .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
        return http.build();
    }

    @Autowired private BCryptPasswordEncoder encoder;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }
}
