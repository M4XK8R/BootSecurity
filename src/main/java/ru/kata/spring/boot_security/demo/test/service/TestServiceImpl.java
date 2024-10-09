package ru.kata.spring.boot_security.demo.test.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.test.dao.TestDao;

@Service
@RequiredArgsConstructor
@Transactional
public class TestServiceImpl implements TestService {

  private final TestDao testDao;

  @Override
  public Optional<Long> getLastUserId() {
    return testDao.getLastUserId();
  }
}

