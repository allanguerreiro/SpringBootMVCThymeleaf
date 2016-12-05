package br.spring.persistence;

import br.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * Created by allan on 30/11/16.
 */
public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Query(value = "SELECT name FROM user LIMIT 10", nativeQuery = true)
    List<String> getUserNames();

    @Query(value = "SELECT user FROM User user")
    Set<User> findAllUsers();

    @Query("select user from User user where user.name = ?1")
    User findUserByName(String name);

    @Query("select user from User user where user.name like %?1")
    List<User> findByNameEndsWith(String name);
}
