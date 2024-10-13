package ru.kata.spring.boot_security.demo.test;

import java.util.HashSet;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.test.service.TestService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.DevelopHelper;

@Component
public final class MainTestHelper {

  private static final int TEST_USERS_AMOUNT = 10;

  public static void main(String[] args) {
    for (int i = 0; i < 100_000; i++) {
      byte random = getRandomNumberFrom0to99();
      if (random == 100) {
        System.out.print(random + " ");
      }
    }
  }

  public void createAndInsertSomeUsers(
      UserService userService,
      TestService testService
  ) {
    final long userId = testService.getLastUserId().orElse(0L);

    for (long _userId = userId; _userId < userId + TEST_USERS_AMOUNT; _userId++) {
      Role role1 = getRandomUserRole(getRandomNumberFrom0to99());
      Role role2 = getRandomUserRole(getRandomNumberFrom0to99());
      final long someMagicNumber = _userId + 1;

      userService.upsert(
          new User(
              0,
              "name_" + someMagicNumber,
              "secondName_" + someMagicNumber,
              "username_" + someMagicNumber,
              "password_" + someMagicNumber,
              new HashSet<>(
                  List.of(role1, role2)
              )
          )
      );
    }
  }

  /*
  Private sector
   */
  private static Role getRandomUserRole(byte number) {
    Role role;

    if (number >= 0 && number < 25) {
      role = Role.GUEST;
    } else if (number >= 25 && number < 50) {
      role = Role.USER;
    } else if (number >= 50 && number < 75) {
      role = Role.MODERATOR;
    } else if (number >= 75 && number < 100) {
      role = Role.ADMIN;
    } else {
      throw new RuntimeException(
          DevelopHelper.createExceptionMessage(
              "TestHelper",
              "getRandomUserRole",
              "smth went wrong..."
          )
      );
    }

    return role;
  }

  private static byte getRandomNumberFrom0to99() {
    return (byte) Math.floor(Math.random() * 100);
  }
}
