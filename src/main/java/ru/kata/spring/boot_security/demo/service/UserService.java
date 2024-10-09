package ru.kata.spring.boot_security.demo.service;

import java.util.Optional;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {

  void upsert(User user);

  void deleteById(long id);

  void deleteAll();

  List<User> getAll();

  Optional<User> getById(long id);
}
