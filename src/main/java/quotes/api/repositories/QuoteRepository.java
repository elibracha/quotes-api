package quotes.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import quotes.api.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

	@Modifying
	@Query(value = "update quote a set a.deleted = 'true' where a.id = :id", nativeQuery = true)
	void delete(@Param("id") Long id);

}
