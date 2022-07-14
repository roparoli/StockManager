package br.inatel.idplab.controller.docs;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import br.inatel.idplab.dto.StockDTO;
import br.inatel.idplab.model.Stock;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface StockManagerControllerDocs {
	
	
	@ApiOperation(value = "list all stocks")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Stock List found")
	})
	List<StockDTO> listAll();
	
	@ApiOperation(value = "Stock find by ID")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success stock found"),
			@ApiResponse(code = 404, message = "Stock not found error")
	})
	StockDTO searchByStockId(@PathVariable String stockId);
	
	@ApiOperation(value = "Stock creation operation")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Success stock creation"),
			@ApiResponse(code = 400, message = "Missing required fields or wrong field range value")
	})
	StockDTO registerNewStock(@RequestBody @Valid StockDTO stockDTO);
	
	@ApiOperation(value = "Stock delete operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Stock by stockId successfully deleted"),
            @ApiResponse(code = 404, message = "Stock not found error")
    })
	void deleteStock(@PathVariable String stockId);
	
}
