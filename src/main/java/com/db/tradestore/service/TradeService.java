package com.db.tradestore.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepository;

@Service
public class TradeService {

	private static final Logger log = LoggerFactory.getLogger(TradeService.class);

	@Autowired
	private TradeRepository tradeRepository;

	public List<Trade> findAll() {
		log.info("Start findAll ");
		return tradeRepository.findAll();
	}

	public Trade findTradeById(String id) {
		log.info("Start findTradeById ");
		Optional<Trade> tradeOptional = tradeRepository.findFirstByTradeIdOrderByVersionDesc(id);
		return tradeOptional.get();
	}

}
