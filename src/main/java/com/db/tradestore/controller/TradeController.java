package com.db.tradestore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.db.tradestore.model.Trade;
import com.db.tradestore.service.TradeService;

@RestController
public class TradeController {

	@Autowired
	TradeService tradeService;

	// this is method used to store the trade into our db.
	@PostMapping("/trade")
	public ResponseEntity<String> processAndSaveTrade(@RequestBody Trade trade) {
		tradeService.processAndSaveTrade(trade);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/trade")
	public ResponseEntity<List<Trade>> findAllTrades() {
		List<Trade> trades = tradeService.findAll();
		return new ResponseEntity<List<Trade>>(trades, HttpStatus.OK);
	}

	//this method is used to get trade with trade id and latested version 
	@GetMapping("/trade/{id}")
	public ResponseEntity<Trade> findTradeById(@PathVariable("id") String id) {
		Trade trade = tradeService.findTradeById(id);
		return new ResponseEntity<Trade>(trade, HttpStatus.OK);
	}
}
