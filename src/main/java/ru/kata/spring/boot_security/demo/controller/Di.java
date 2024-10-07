package ru.kata.spring.boot_security.demo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.RedirectView;

@Configuration
class Di {

  @Bean
  RedirectView provideListRedirectView() {
    return new RedirectView("/list");
  }
}
