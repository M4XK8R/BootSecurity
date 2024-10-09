package ru.kata.spring.boot_security.demo.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.test.MainTestHelper;
import ru.kata.spring.boot_security.demo.test.service.TestService;

@Controller
@RequestMapping("/users/test")
@RequiredArgsConstructor
final class TestUsersController {

  private final UserService userService;
  private final TestService testService;
  private final MainTestHelper testHelper;

  @PostMapping("/add")
  String createSomeTestUsers() {
    testHelper.createAndInsertSomeUsers(userService, testService);
    return "redirect:/users/list";
  }
}
