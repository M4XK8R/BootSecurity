package ru.kata.spring.boot_security.demo.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.DevelopHelper;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
final class ShowerUsersController {

  private final UserService userService;

  @GetMapping("/list")
  String showUsers(ModelMap model) {
    model.addAttribute(
        "users",
        userService.getAll()
    );
    return "user/list";
  }

  @GetMapping("/add")
  String showUserForm(Model model) {
    model.addAttribute(
        "user",
        new User()
    );
    return "user/form";
  }

  @GetMapping("/update/{id}")
  String showUserForm(
      @PathVariable long id,
      Model model
  ) throws RuntimeException {
    if (id == 0) {
      throw new RuntimeException(
          DevelopHelper.createExceptionMessage(
              "ShowerUsersController",
              "showUserForm",
              "userId should not be 0; abnormal behavior"
          )
      );
    }
    Optional<User> user = userService.getById(id);

    if (user.isPresent()) {
      model.addAttribute(
          "user",
          user
      );
    } else {
      throw new RuntimeException(
          DevelopHelper.createExceptionMessage(
              "ShowerUsersController",
              "showUserForm",
              "user is absent in database; abnormal behavior"
          )
      );
    }
    return "user/form";
  }

  @GetMapping("/get/{id}")
  String showOneUser(
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
    return "user/detail";
  }
}
