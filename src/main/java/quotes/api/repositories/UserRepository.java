package quotes.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import quotes.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
