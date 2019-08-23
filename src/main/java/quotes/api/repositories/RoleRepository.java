package quotes.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import quotes.api.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
