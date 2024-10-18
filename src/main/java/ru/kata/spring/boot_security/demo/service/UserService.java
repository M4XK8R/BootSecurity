package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import java.util.Optional;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserService {

  void upsert(User user);

  Optional<User> getById(long id);

  Optional<User> getByEmail(String email);

  List<User> getAll();

  void deleteById(long id);

  void deleteAll();
}