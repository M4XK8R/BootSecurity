package ru.kata.spring.boot_security.demo.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;
import ru.kata.spring.boot_security.demo.util.DevelopHelper;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserDao userDao;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> userOptional = userDao.getByEmail(email);

    if (userOptional.isPresent()) {
      return new UserDetailsImpl(userOptional.get());
    } else {
      throw new UsernameNotFoundException(
          DevelopHelper.createExceptionMessage(
              "UserDetailsServiceImpl",
              "getByEmail",
              "User not found"
          )
      );
    }
  }
}
