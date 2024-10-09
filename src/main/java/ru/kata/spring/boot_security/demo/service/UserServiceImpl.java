package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserDao userDao;

  @Override
  public void upsert(User user) {
    if (user.getId() == User.UNDEFINED_ID) {
      userDao.save(user);
    } else {
      userDao.update(user);
    }
  }

  @Override
  public void deleteById(long id) {
    userDao.removeById(id);
  }

  @Override
  public void deleteAll() {
    userDao.cleanTable();
  }

  @Transactional(readOnly = true)
  @Override
  public List<User> getAll() {
    return userDao.getAll();
  }

  @Override
  public Optional<User> getById(long id) {
    return userDao.getById(id);
  }
}