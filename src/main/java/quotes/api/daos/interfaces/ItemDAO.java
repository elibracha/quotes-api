package quotes.api.daos.interfaces;

import java.util.List;

import quotes.api.model.Item;

public interface ItemDAO {

	public void createItems(List<Item> items);
}
