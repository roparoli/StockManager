package br.inatel.idplab.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import br.inatel.idplab.model.Quote;
import br.inatel.idplab.model.Stock;

public class StockDTO {

	private String id;
	
	private String stockId;
	
	private Map<LocalDate, BigDecimal> quotes = new HashMap<>();
	
	public StockDTO() {
	}

	public StockDTO(Stock stock) {
		this.id = stock.getId();
		this.stockId = stock.getStockId();
		stock.getQuotes().forEach(q -> {
			this.quotes.put(q.getDate(), q.getPrice());
		});
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

	public Map<LocalDate, BigDecimal> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<LocalDate, BigDecimal> quotes) {
		this.quotes = quotes;
	}
	
	public Stock toModel() {
		Stock stock = new Stock();
		stock.setStockId(this.stockId);
		stock.setId(this.id);
		this.quotes.entrySet()
			.stream()
			.map(e -> new Quote(stock, e.getKey(), e.getValue()))
			.forEach(q -> stock.addQuote(q));
		
		return stock;
	}
}
