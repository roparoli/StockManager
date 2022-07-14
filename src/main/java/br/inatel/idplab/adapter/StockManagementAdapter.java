package br.inatel.idplab.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.inatel.idplab.dto.StockManagementDTO;
import br.inatel.idplab.notification.Notification;
import reactor.core.publisher.Flux;

@Service
public class StockManagementAdapter {
	private static final String URI_STOCKMANAGEMENT = "http://localhost:8080";

	@Cacheable(value = "cacheStockManagement")
	public List<StockManagementDTO> listAllStockOnManagement() {

		List<StockManagementDTO> registeredStocks = new ArrayList<>();

		try {
			Flux<StockManagementDTO> fluxRegisteredStocks = WebClient.create(URI_STOCKMANAGEMENT).get().uri("/stock")
					.retrieve().bodyToFlux(StockManagementDTO.class);

			fluxRegisteredStocks.subscribe(f -> registeredStocks.add(f));
			fluxRegisteredStocks.blockLast();

		} catch (Exception e) {
			e.getMessage();
		}

		return registeredStocks;
	}

	@CacheEvict(value = "cacheStockManagement", allEntries = true)
	public ResponseEntity<StockManagementDTO> registerStockOnManagement(StockManagementDTO stock) {
		StockManagementDTO toRegisterStock = new StockManagementDTO(stock.getId(), stock.getDescription());

		ResponseEntity<Void> response = WebClient.create(URI_STOCKMANAGEMENT)
				.post()
				.uri("/stock")
				.bodyValue(toRegisterStock)
				.retrieve()
				.toBodilessEntity()
				.block();

		return ResponseEntity.status(response.getStatusCode()).body(toRegisterStock);

	}
	
	public void notification() {
		
		Notification notification = new Notification("localhost", 8081);
		
		try {
			
			WebClient.create()
			.post()
			.uri("/notification")
			.bodyValue(notification)
			.retrieve()
			.toBodilessEntity()
			.block();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
