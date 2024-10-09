package ru.kata.spring.boot_security.demo.util;

public final class DevelopHelper {

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
        exception.getMessage().toLowerCase()
    );
  }

  public static String createExceptionMessage(
      String className,
      String methodName,
      String message
  ) {
    return String.format(
        "%s %s(): %s",
        className, methodName, message
    );
  }
}
