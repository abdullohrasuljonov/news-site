package uz.pdp.newssite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.newssite.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Long id);
}
