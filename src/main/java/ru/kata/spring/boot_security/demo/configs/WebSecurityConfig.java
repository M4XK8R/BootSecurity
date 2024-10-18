package ru.kata.spring.boot_security.demo.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final SuccessUserHandler successUserHandler;
  private final UserDetailsService userDetailsService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("user/info/*").hasAnyRole(
            Role.ADMIN.name(), Role.USER.name()
        )
        .antMatchers("/admin/**").hasRole(Role.ADMIN.name())
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/auth/login")
        .loginProcessingUrl("/login")
        .successHandler(successUserHandler)
        .permitAll()
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/bootstrap/auth/login");
  }

  @Override
  @SuppressWarnings("deprecation")
  protected void configure(
      AuthenticationManagerBuilder auth
  ) throws Exception {
    auth.userDetailsService(userDetailsService)
        .passwordEncoder(NoOpPasswordEncoder.getInstance());
  }
}