package quotes.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import quotes.api.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
	@Override
	@Query("select a from Quote a where a.deleted = 'false'")
	public List<Quote> findAll();

	@Query("select a from Quote a where a.deleted = 'true' and a.name = :name")
	public Quote findDeletedByName(@Param("name") String name);

	@Query("update Quote a set a.deleted = 'true' where a.id = :id")
	@Modifying
	public void softDelete(@Param("id") Long id);

	@Query("update Quote a set a.deleted = 'false' where a.id = :id")
	@Modifying
	public void recycle(@Param("id") Long id);

}
