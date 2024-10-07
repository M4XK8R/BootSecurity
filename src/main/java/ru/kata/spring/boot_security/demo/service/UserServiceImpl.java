package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional()
public class UserServiceImpl implements UserService {

  private final UserDao userDao;

  public UserServiceImpl(
      @Qualifier("userDaoHibernateJpaImpl") UserDao userDao
  ) {
    this.userDao = userDao;
  }


  @Override
  public void upsert(User user) {
    if (user.getId() == User.UNDEFINED_ID) {
      userDao.save(user);
    } else {
      userDao.update(user);
    }
  }

  @Override
  public void removeById(long id) {
    userDao.removeById(id);
  }

  @Override
  public void removeAll() {
    userDao.cleanTable();
  }

  @Transactional(readOnly = true)
  @Override
  public List<User> getAll() {
    return userDao.getAll();
  }
}