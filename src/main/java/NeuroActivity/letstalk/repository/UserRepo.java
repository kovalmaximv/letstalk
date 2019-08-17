package NeuroActivity.letstalk.repository;

import NeuroActivity.letstalk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String Username);

}
