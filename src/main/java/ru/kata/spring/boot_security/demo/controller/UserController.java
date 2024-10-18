package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.DevelopHelper;

@Controller()
@RequestMapping("/user")
@RequiredArgsConstructor
final class UserController {

  private final UserService userService;

  @GetMapping("/info")
  String showUserInfo(
      Principal principal,
      Model model
  ) {
    User currentUser = userService.getByEmail(
        principal.getName()
    ).orElseThrow(() ->
        new NoSuchElementException(
            DevelopHelper.createExceptionMessage(
                "AdminControllerGet",
                "showUsers",
                "User not found"
            )
        )
    );
    model.addAttribute(
        "user",
        currentUser
    );
    return "bootstrap/user/detail";
  }
}

