package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
final class ModifierUsersController {

  private final UserService userService;

  @PostMapping("/upsert")
  String upsertUser(
      @ModelAttribute User user
  ) {
    userService.upsert(user);
    return "redirect:/users/list";
  }

  @PostMapping("/delete/{id}")
  String deleteUserById(
      @PathVariable long id
  ) {
    userService.deleteById(id);
    return "redirect:/users/list";
  }

  @PostMapping("/wipe")
  String deleteAllUsers() {
    userService.deleteAll();
    return "redirect:/users/list";
  }
}
