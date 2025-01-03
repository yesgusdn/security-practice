package com.security.practice.stock.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.practice.stock.entity.Stock;
import com.security.practice.stock.service.StockService;

@RestController
@RequestMapping("/api/stock")
public class StockController {

	@Autowired
	private StockService stockService;
	
	@GetMapping("/{country}")
	public ResponseEntity<?> getStock(@PathVariable("country") String country){
		
		List<Stock> stockList = stockService.getStockByCountry(country);
		
		return new ResponseEntity<>(stockList, HttpStatus.OK);
	}
	
	@GetMapping("info/{stockCd}")
	public ResponseEntity<?> getStockTest(@PathVariable("stockCd") String stockCd){
		Stock stock = stockService.findByStockCd(stockCd);
		
		return new ResponseEntity<>(stock, HttpStatus.OK);
	}
	
}
