package br.inatel.idplab.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.inatel.idplab.controller.docs.StockManagerControllerDocs;
import br.inatel.idplab.dto.StockDTO;
import br.inatel.idplab.model.Stock;
import br.inatel.idplab.service.StockService;

@RestController
@RequestMapping("/stock")
public class StockController implements StockManagerControllerDocs{

	private StockService service;

	public StockController(StockService service) {
		super();
		this.service = service;
	}
	
	@GetMapping
	public List<StockDTO> listAll(){
		List<Stock> stocks = service.findAllStocksAndQuotes();
		
		List<StockDTO> dtoList = stocks.stream()
			.map(s -> new StockDTO(s))
			.collect(Collectors.toList());
		
		return dtoList;
	}
	
	@GetMapping("/{stockId}")
	public StockDTO searchByStockId(@PathVariable String stockId){
		Stock stock = service.findStockById(stockId);
		
		if(stock == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} else {
			
			StockDTO stockDTO = new StockDTO(stock);
			return stockDTO;
		}
	}
	
	@PostMapping
	public StockDTO registerNewStock(@RequestBody @Valid StockDTO stockDTO){
		
		Stock stock = stockDTO.toModel();
		
		try {
			Stock savedStock = service.saveStockAndQuotes(stock);
			StockDTO savedStockDTO = new StockDTO(savedStock);
			return savedStockDTO;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@DeleteMapping("/{stockId}")
	public void deleteStock(@PathVariable String stockId) {
		service.deleteStock(stockId);
	}
	
}
