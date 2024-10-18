package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.DevelopHelper;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
final class AdminController {

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
                "AdminController",
                "showUsers",
                "User not found"
            )
        )
    );
    List<User> currentUsers = userService.getAll();

    if (currentUsers.isEmpty() || !currentUsers.contains(currentUser)) {
      throw new RuntimeException(
          DevelopHelper.createExceptionMessage(
              "AdminController",
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
    return "admin/list";
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
    return "admin/detail";
  }

  @PostMapping("/upsert")
  String upsertUser(
      @ModelAttribute User user
  ) {
    userService.upsert(user);
    return "redirect:/admin/users/list";
  }

  @PostMapping("/delete/{id}")
  String deleteUserById(
      @PathVariable long id
  ) {
    userService.deleteById(id);
    return "redirect:/admin/users/list";
  }
}
