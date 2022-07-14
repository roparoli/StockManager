package br.inatel.idplab.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.inatel.idplab.adapter.StockManagementAdapter;
import br.inatel.idplab.dto.StockManagementDTO;
import br.inatel.idplab.model.Quote;
import br.inatel.idplab.model.Stock;
import br.inatel.idplab.repository.QuoteRepository;
import br.inatel.idplab.repository.StockRepository;

@Service
@Transactional
public class StockService {

	private QuoteRepository quoteRepository;
	private StockRepository stockRepository;
	private StockManagementAdapter adapter;

	public StockService(QuoteRepository quoteRepository, StockRepository stockRepository,
			StockManagementAdapter adapter) {
		super();
		this.quoteRepository = quoteRepository;
		this.stockRepository = stockRepository;
		this.adapter = adapter;
	}

	@Cacheable(value = "stockCache")
	public List<Stock> findAllStocksAndQuotes() {
		
		List<Stock> stocks = stockRepository.findAll();
		
		stocks.forEach(s -> s.getQuotes().size());
		
		return stocks;
	}

	public Stock findStockById(String stockId) {
		Optional<Stock> stockById = stockRepository.findByStockId(stockId);
		Stock stock = new Stock();
		
		
		if (stockById.isPresent()) {
			
			
			stock = stockById.get();
			List<Quote> quotes = quoteRepository.findByStockId(stock.getId());
			stock.setQuotes(quotes);
			return stock;
			
		}
		
		return null;
		
	}

	@CacheEvict(value = "stockCache", allEntries = true)
	public Stock saveStockAndQuotes(Stock stock) {
		boolean exists = verifyifStockIsOnManagement(stock);
		List<Quote> listQuote = stock.getQuotes();
		if (exists) {

			Stock savedStock = saveStock(stock);
			listQuote.forEach(q -> saveQuotes(q));
			savedStock.setQuotes(quoteRepository.findByStockId(savedStock.getId()));
			
			return savedStock;
		} else {
			throw new RuntimeException("Stock não encontrado no stock management! Favor criar um stock");
		}
	}
	
	private Stock saveStock(Stock stock) {
		
		Optional<Stock> findedStock = stockRepository.findByStockId(stock.getStockId());
		
		if(!findedStock.isPresent()) {
		
		Stock savedStock = stockRepository.save(stock);
		
		return savedStock;
		
		}
		
		
		Stock auxStock = findedStock.get();
		stock.setId(auxStock.getId());
		return stock;
		
	}

	private Quote saveQuotes(Quote quote) {
		
		
		Optional<Quote> quoteOpt = quoteRepository.findByDateAndStockId(quote.getDate(), quote.getStock().getId());
			
			if(!quoteOpt.isPresent()){
				
				quoteRepository.save(quote);
				return quote;
			}
			
		Quote quoteExt = quoteOpt.get();
		quoteExt.setPrice(quote.getPrice());
		return quoteExt;
		
	}

	@CacheEvict(value = "stockCache", allEntries = true)
	public void deleteStock(String stockId) {
		Optional<Stock> stock = stockRepository.findByStockId(stockId);

		if (stock.isPresent()) {
			List<Quote> quotes = quoteRepository.findByStockId(stock.get().getId());

			for (Quote quote : quotes) {
				quoteRepository.delete(quote);
			}

			Stock deletedStock = stock.get();
			stockRepository.delete(deletedStock);

		} else {
			throw new RuntimeException("Não existe stock com esse StockId");
		}
	}

	public boolean verifyifStockIsOnManagement(Stock stock) {
		List<StockManagementDTO> stockManagements = adapter.listAllStockOnManagement();

		boolean exists = false;

		for (StockManagementDTO stockManagement : stockManagements) {

			if (stockManagement.getId().equals(stock.getStockId())) {
				exists = true;
			}
		}

		return exists;
	}
}
