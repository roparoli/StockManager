package br.inatel.idplab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.idplab.model.Stock;

public interface StockRepository extends JpaRepository<Stock, String>{

	Optional<Stock> findByStockId(String stockId);
	
	
}
