package ru.kata.spring.boot_security.demo.model;

import com.sun.istack.NotNull;
import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = User.TABLE_NAME)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public final class User {

  public static final String TABLE_NAME = "users";
  public static final String SUB_TABLE_NAME = "user_roles";
  public static final byte UNDEFINED_ID = 0;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  @NotNull
  private String firstName;

  @Column(nullable = false)
  @NotNull
  private String lastName;

  @Column(nullable = false)
  private byte age;

  @Column
  private String email;

  @Column
  private String password;

  @CollectionTable(
      name = SUB_TABLE_NAME,
      joinColumns = @JoinColumn(name = "id")
  )
  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private Set<Role> roles;

  public boolean containsRole(String roleStr) {
    for (Role role : roles) {
      if (role.name().contains(roleStr)) {
        return true;
      }
    }
    return false;
  }

  public List<Role> getCurrentRolesInUse() {
    return List.of(Role.USER, Role.ADMIN);
  }
}
