package NeuroActivity.letstalk.repository;

import NeuroActivity.letstalk.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String Username);

    @EntityGraph(attributePaths = { "subscriptions", "subscribers" })
    Optional<User> findById(Long aLong);
}
