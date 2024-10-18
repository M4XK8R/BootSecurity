package ru.kata.spring.boot_security.demo;

import java.util.Set;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.UserService;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(
        SpringBootSecurityDemoApplication.class,
        args
    );

    UserService userService = context.getBean(UserService.class);

    userService.upsert(
        new User(
            0,
            "userFirstName",
            "userLastName",
            (byte) 11,
            "user@mail.ru",
            "user",
            Set.of(Role.USER)
        )
    );

    userService.upsert(
        new User(
            0,
            "adminFirstName",
            "adminLastName",
            (byte) 22,
            "admin@mail.ru",
            "admin",
            Set.of(Role.ADMIN, Role.USER)
        )
    );
  }
}
