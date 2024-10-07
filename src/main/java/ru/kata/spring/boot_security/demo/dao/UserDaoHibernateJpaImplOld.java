package ru.kata.spring.boot_security.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.util.LoggingUtil;

@Repository
public class UserDaoHibernateJpaImplOld implements UserDao {

//  private final EntityManagerFactory entityManagerFactory;
//
//  public UserDaoHibernateJpaImpl(EntityManagerFactory entityManagerFactory) {
//    this.entityManagerFactory = entityManagerFactory;
//  }

  @Override
  public void createTable() {
    run(entityManager ->
        entityManager.createNativeQuery(
            "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(255), " +
                "second_name VARCHAR(255), " +
                "PRIMARY KEY (id)" +
                ")"
        ).executeUpdate()
    );
  }

  @Override
  public void dropTable() {
    run(entityManager ->
        entityManager.createNativeQuery(
            "DROP TABLE IF EXISTS users"
        ).executeUpdate()
    );
  }

  @Override
  public void save(User user) {
    run(entityManager ->
        entityManager.persist(user));
  }

  @Override
  public void update(User user) {
    run(entityManager -> {
      boolean isUserInDatabase = entityManager.find(
          User.class,
          user.getId()
      ) != null;
      if (isUserInDatabase) {
        entityManager.merge(user);
      }
    });
  }

  @Override
  public void removeById(long id) {
    run(entityManager -> {
      User user = entityManager.find(User.class, id);
      if (user != null) {
        entityManager.remove(user);
      }
    });
  }

  @Override
  public List<User> getAll() {
    List<User> users = new ArrayList<>();

    run(entityManager ->
        users.addAll(
            entityManager.createQuery(
                "from User",
                User.class
            ).getResultList()
        ));

    return users;
  }

  @Override
  public void cleanTable() {
    run(entityManager ->
        entityManager.createQuery(
            "DELETE FROM User"
        ).executeUpdate()
    );
  }

  /*
Private sector
 */
  private void run(Consumer<EntityManager> entityManagerConsumer) {
    if (entityManagerConsumer == null) {
      return;
    }
    EntityManager entityManager = null;
    EntityTransaction transaction = null;

    try {
//      entityManager = entityManagerFactory.createEntityManager();
      transaction = entityManager.getTransaction();
      entityManagerConsumer.accept(entityManager);
      transaction.commit();
    } catch (Exception e) {
      LoggingUtil.printExceptionInfo(
          "UserDaoHibernateJpaImpl",
          "run",
          e
      );
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
      throw (e);
    } finally {
      if (entityManager != null && entityManager.isOpen()) {
        try {
          entityManager.close();
        } catch (Exception e) {
          LoggingUtil.printExceptionInfo(
              "UserDaoHibernateJpaImpl",
              "run",
              e
          );
          throw (e);
        }
      }
    }
  }
}
