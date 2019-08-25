package quotes.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import quotes.api.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
	@Override
	@Query("select a from Quote a where a.deleted = 0")
	public List<Quote> findAll();

	@Override
	@Query("select a from Quote a where a.id = :id and a.deleted = 0")
	public Optional<Quote> findById(@Param("id") Long id);

	@Query("select a from Quote a where a.deleted = 1 and a.name = :name")
	public Quote findDeletedByName(@Param("name") String name);

	@Query("update Quote a set a.deleted = 1 where a.id = :id")
	@Modifying
	public void softDelete(@Param("id") Long id);

	@Query("update Quote a set a.deleted = 0 where a.id = :id")
	@Modifying
	public void recycle(@Param("id") Long id);

}
