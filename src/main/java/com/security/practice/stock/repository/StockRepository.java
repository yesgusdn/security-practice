package com.security.practice.stock.repository;

import org.springframework.stereotype.Repository;

import com.security.practice.stock.entity.Stock;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StockRepository extends JpaRepository<Stock, String>{
	Optional<Stock> findByStockCd(String stockCd);
	Optional<List<Stock>> findByCountry(String country);
}
