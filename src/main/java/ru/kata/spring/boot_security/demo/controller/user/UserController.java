package ru.kata.spring.boot_security.demo.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller()
@RequestMapping("/user")
@RequiredArgsConstructor
final class UserController {

  private final UserService userService;

  @GetMapping("/info/{id}")
  String showInfo(
      @PathVariable long id,
      Model model
  ) {
    userService.getById(id)
        .ifPresent(user ->
            model.addAttribute(
                "user",
                user
            )
        );
    return "users/detail";
  }
}

