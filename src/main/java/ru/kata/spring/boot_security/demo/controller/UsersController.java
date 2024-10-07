package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;

@Controller
final class UsersController {

  private final UserService userService;
  private final RedirectView listRedirectView;

  UsersController(
      UserService userService,
      RedirectView listRedirectView
  ) {
    this.userService = userService;
    this.listRedirectView = listRedirectView;
  }

  @GetMapping("/list")
  String getUsers(ModelMap model) {
    model.addAttribute(
        "users",
        userService.getAll()
    );
    return "users";
  }

  @GetMapping("/add")
  RedirectView addUser(
      @RequestParam(name = "name") String name,
      @RequestParam(name = "second_name") String secondName
  ) {
    userService.upsert(new User(name, secondName));
    return listRedirectView;
  }

  @GetMapping("/update")
  RedirectView updateUser(
      @RequestParam(name = "id") int id,
      @RequestParam(name = "name") String name,
      @RequestParam(name = "second_name") String secondName
  ) {
    userService.upsert(new User(id, name, secondName));
    return listRedirectView;
  }

  @GetMapping("/delete")
  RedirectView deleteUser(
      @RequestParam(name = "id") int id
  ) {
    userService.removeById(id);
    return listRedirectView;
  }
}
