package task.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import task.project.entity.User;

public interface UserInterface extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
}
