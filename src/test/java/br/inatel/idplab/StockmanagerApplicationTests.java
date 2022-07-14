package br.inatel.idplab;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;


import br.inatel.idplab.dto.StockDTO;
import br.inatel.idplab.model.Stock;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class StockmanagerApplicationTests {

	@Autowired
	private WebTestClient clientTest;

	@Test
	void givenStocksInDb_whenListStocks_thenReturnStocks() {
		clientTest.get().uri("/stock").exchange().expectStatus().isOk().expectBody().returnResult();
	}

	@Test
	void givenAStockId_whenFindByStockId_returnStockByStockId() {
		String stockId = "petr4";

		StockDTO stockDTO = clientTest.get().uri("/stock/" + stockId).exchange().expectStatus().isOk().expectBody(StockDTO.class)
				.returnResult().getResponseBody();

		assertEquals(stockDTO.getStockId(), stockId);
	}

	@Test
	void givenUnexistentStockId_whenFindByStockId_thenReturnStatusNotFound() {
		String stockId = "petr2";

		clientTest.get().uri("/stock/" + stockId).exchange().expectStatus().isNotFound();
	}

	@Test
	void givenValidStock_whenCreateStock_thenReturnTheStockBody() {

		StockDTO stockDTO = new StockDTO();
		Map<LocalDate, BigDecimal> quote = new HashMap<>();
		quote.put(LocalDate.of(2015, Month.DECEMBER, 22), new BigDecimal(25.32));

		stockDTO.setQuotes(quote);
		stockDTO.setStockId("petr4");
		
		

		clientTest.post().uri("/stock").body(BodyInserters.fromValue(stockDTO)).exchange().expectStatus().isOk();
	}

	void givenAnInvalidStock_whenOneOrMoreFieldsAreInvalidOrNull_thenReturnBadRequestStatus() {

		StockDTO stockDTO = new StockDTO();

		Map<LocalDate, BigDecimal> quote = new HashMap<>();
		quote.put(LocalDate.of(2015, Month.DECEMBER, 22), new BigDecimal(25.32));

		stockDTO.setQuotes(quote);
		stockDTO.setStockId("vale9");
		stockDTO.setStockId(null);

		clientTest.post().uri("/stock").body(BodyInserters.fromValue(stockDTO)).exchange().expectStatus()
				.isBadRequest();
	}
}
