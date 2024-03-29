package com.application.ptsdwebapplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.application.ptsdwebapplication.security.MySimpleUrlAuthenticationSuccessHandler;
import com.application.ptsdwebapplication.services.PersonDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Настройка авторизации и обработка формы входа
        http.authorizeRequests(requests -> requests
                .antMatchers("/adminpage", "/adminpage/users", "/adminpage/admins", "/adminpage/user/{id}", "/adminpage/user/{id}/edit").hasRole("ADMIN")
                .antMatchers("/", "/login").permitAll()
                .anyRequest().hasAnyRole("USER"))
            .formLogin(login -> login.loginPage("/login")
                .loginProcessingUrl("/process_login")
                 .successHandler(myAuthenticationSuccessHandler())
                .failureUrl("/login?error"))
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login"));
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    // Настраивает аутентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService);
        /*auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());*/
    }

    // Шифрование пароля
    @Bean
    public PasswordEncoder getPasswordEncoder() {
         return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }
    
}
