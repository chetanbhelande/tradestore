package com.db.tradestore.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.tradestore.exception.TradeInvalidException;
import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepository;
import com.db.tradestore.util.ITradeValidationChain;
import com.db.tradestore.util.TradeMaturityDateValidator;
import com.db.tradestore.util.TradeVersionValidator;

@Service
public class TradeService {

	private static final Logger log = LoggerFactory.getLogger(TradeService.class);

	@Autowired
	private TradeRepository tradeRepository;

	public Trade processAndSaveTrade(Trade trade) {
		log.info("Start processAndSaveTrade ");
		// here i have used chain of resposbility design pattern to validate trade.
		getTradeValidationChain().validateTrade(trade);

		trade.setCreatedDate(LocalDate.now());
		return tradeRepository.save(trade);
	}

	private ITradeValidationChain getTradeValidationChain() {
		log.info("Start getTradeValidationChain ");
		ITradeValidationChain tradeMaturityDateValidator = new TradeMaturityDateValidator();
		ITradeValidationChain tradeVersionValidator = new TradeVersionValidator(tradeRepository);

		tradeMaturityDateValidator.setNextChain(tradeVersionValidator);
		return tradeMaturityDateValidator;
	}

	public List<Trade> findAll() {
		log.info("Start findAll ");
		return tradeRepository.findAll();
	}

	public Trade findTradeById(String id) {
		log.info("Start findTradeById ");
		Optional<Trade> tradeOptional = tradeRepository.findFirstByTradeIdOrderByVersionDesc(id);
		return tradeOptional.get();
	}

	public void updateExpiryFlagOfTrade() {
		log.info("Schedular start");
		
		ITradeValidationChain tradeMaturityDateValidator = new TradeMaturityDateValidator();
		tradeRepository.findAll().stream().forEach(t -> {

			try {
				tradeMaturityDateValidator.validateTrade(t);
			} catch (TradeInvalidException e) {
				t.setExpiredFlag("Y");
				tradeRepository.save(t);
			}

		});
	}
}
