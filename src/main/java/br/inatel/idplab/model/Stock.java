package br.inatel.idplab.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "stock")
public class Stock {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	
	@NotNull
	@Size(max = 10)
	private String stockId;
	
	@OneToMany(mappedBy = "stock", fetch = FetchType.LAZY)
	private List<Quote> quotes = new ArrayList<Quote>();

	public Stock() {
	}

	public Stock(String id, @NotNull @Size(max = 10) String stockId, List<Quote> quotes) {
		super();
		this.id = id;
		this.stockId = stockId;
		this.quotes = quotes;
	}
	
	public void addQuote(Quote quote) {
		this.quotes.add(quote);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(id, quotes, stockId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		return Objects.equals(id, other.id) && Objects.equals(quotes, other.quotes)
				&& Objects.equals(stockId, other.stockId);
	}
	
}
