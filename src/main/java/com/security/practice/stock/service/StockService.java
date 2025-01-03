package com.security.practice.stock.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.security.practice.stock.entity.Stock;
import com.security.practice.stock.repository.StockRepository;

import jakarta.transaction.Transactional;

@Service
public class StockService {
	
	private StockRepository stockRepository;

	public StockService(StockRepository stockRepository) {
		super();
		this.stockRepository = stockRepository;
	}
	
	@Transactional
	public List<Stock> getStockByCountry(String country) {
		return stockRepository.findByCountry(country).orElseThrow(()-> new RuntimeException("no stock"));
	}
	
	@Transactional
	public Stock findByStockCd(String stockCd) {
		return stockRepository.findByStockCd(stockCd).orElseThrow(() -> new RuntimeException("no stock"));
	}
	
}
