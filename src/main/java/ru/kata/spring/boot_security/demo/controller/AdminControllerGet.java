package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
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
@RequestMapping("/admin/users")
@RequiredArgsConstructor
final class AdminControllerGet {

  private final UserService userService;

  @GetMapping("/list")
  String showUsers(
      ModelMap model,
      Principal principal
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
    List<User> currentUsers = userService.getAll();

    if (currentUsers.isEmpty() || !currentUsers.contains(currentUser)) {
      throw new RuntimeException(
          DevelopHelper.createExceptionMessage(
              "AdminControllerGet",
              "showUsers",
              "Smth went wrong..."
          )
      );
    }
    model
        .addAttribute(
            "user",
            currentUser
        )
        .addAttribute(
            "users",
            currentUsers
        );
    return "bootstrap/admin/list";
  }

  @GetMapping("/add")
  String showUserForm(Model model) {
    model.addAttribute(
        "user",
        new User()
    );
    return "users/form";
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
    return "users/form";
  }

  @GetMapping("/info")
  String showCurrentUserInfo(
      Principal principal,
      Model model
  ) {
    User currentUser = userService.getByEmail(
        principal.getName()
    ).orElseThrow();

    model.addAttribute(
        "user",
        currentUser
    );
    return "bootstrap/admin/detail";
  }
}
