package br.inatel.idplab.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/stockcache")
public class CacheStockManagementController {

	@DeleteMapping
	public ResponseEntity<?> deleteCache(){
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
