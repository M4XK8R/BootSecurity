package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {

  void upsert(User user);

  void removeById(long id);

  void removeAll();

  List<User> getAll();
}
