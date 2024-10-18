package ru.kata.spring.boot_security.demo.configs;

import java.io.IOException;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;

@Component
class SuccessUserHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Authentication authentication
  ) throws IOException {
    Set<String> roles = AuthorityUtils.authorityListToSet(
        authentication.getAuthorities()
    );

    if (roles.contains(Role.ADMIN.getAuthority())) {
      httpServletResponse.sendRedirect(
          "/admin/users/list"
      );
    } else if (roles.contains(Role.USER.getAuthority())) {
      httpServletResponse.sendRedirect(
          "/user/info"
      );
    } else {
      httpServletResponse.sendRedirect("/login");
    }
  }
}