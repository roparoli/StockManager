package br.inatel.idplab.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.idplab.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long>{

	Optional<Quote> findByDateAndStockId(LocalDate date, String id);
	
	List<Quote> findByStockId(String id);
}
