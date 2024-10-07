package ru.kata.spring.boot_security.demo.dao;

import java.util.List;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserDao {

  void createTable();

  void dropTable();

  void save(User user);

  void update(User user);

  void removeById(long id);

  List<User> getAll();

  void cleanTable();
}
