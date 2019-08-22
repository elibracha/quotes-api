package quotes.api.repositories;

import org.springframework.data.repository.CrudRepository;

import quotes.api.model.Item;

public interface ItemRepository extends CrudRepository<Item, Long> {

}
