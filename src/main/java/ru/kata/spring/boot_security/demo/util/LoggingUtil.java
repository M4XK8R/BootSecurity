package ru.kata.spring.boot_security.demo.util;

public final class LoggingUtil {

  public static void printExceptionInfo(
      String className,
      String methodName,
      Exception exception
  ) {
    System.err.printf(
        "%s %s() "
            + "\n"
            + "\t caught: %s,"
            + "\n"
            + "\t message: \"%s\"."
            + "\n",
        className,
        methodName,
        exception.getClass(),
        exception.getMessage()
            .toLowerCase()
    );
  }
}
