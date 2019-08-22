package quotes.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import quotes.api.model.QuoteLog;

public interface QuoteLogRepository extends JpaRepository<QuoteLog, Long> {

}
