package ru.kata.spring.boot_security.demo.test.service;

import java.util.Optional;

public interface TestService {

  Optional<Long> getLastUserId();
}
