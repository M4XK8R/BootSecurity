package ru.kata.spring.boot_security.demo.dao;

import java.util.List;
import java.util.Optional;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserDao {

  void createTable();

  void dropTable();

  void save(User user);

  void update(User user);

  void removeById(long id);

  Optional<User> getById(long id);

  Optional<User> getByEmail(String email);

  List<User> getAll();

  void cleanTable();
}
