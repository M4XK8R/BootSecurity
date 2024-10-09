package ru.kata.spring.boot_security.demo.test.dao;

import java.util.Optional;

public interface TestDao {

  Optional<Long> getLastUserId();
}
