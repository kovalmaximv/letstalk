package NeuroActivity.letstalk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface UserDetailsRepo extends JpaRepository<User, Long> {
}
