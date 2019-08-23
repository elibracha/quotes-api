package quotes.api.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "quote", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Quote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
	@GenericGenerator(name = "native", strategy = "increment")
	private long id;
	@NotEmpty(message = "name field can not be empty")
	private String name;
	@Min(value = 0, message = "price can not be a negative number")
	private long price;
	private boolean deleted = false;
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private List<Item> items;

	public Quote() {
		super();
	}

	public Quote(String name, long price, List<Item> items) {
		super();
		this.name = name;
		this.price = price;
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public long getId() {
		return id;
	}

	@JsonIgnore
	public boolean isDeleted() {
		return deleted;
	}

	@Override
	public String toString() {
		return "Quote [id=" + id + ", name=" + name + ", price=" + price + ", items=" + items + "]";
	}

}
