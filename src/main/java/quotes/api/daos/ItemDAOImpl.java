package quotes.api.daos;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quotes.api.daos.interfaces.ItemDAO;
import quotes.api.exceptions.MissMatchItemNameException;
import quotes.api.model.Item;
import quotes.api.repositories.ItemRepository;

@Service
public class ItemDAOImpl implements ItemDAO {

	private ItemRepository itemRepository;

	public ItemDAOImpl(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@Transactional
	public void createItems(List<Item> items) throws RuntimeException {
		if (items != null)
			for (Item item : items) {
				if (!itemRepository.existsById(item.getId())) {
					itemRepository.save(item);
				} else if (!itemRepository.findById(item.getId()).get().getName().equals(item.getName())) {
					throw new MissMatchItemNameException();
				}
			}
	}
}
