package com.db.tradestore.util;

import java.util.Optional;

import com.db.tradestore.exception.TradeInvalidException;
import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepository;

public class TradeVersionValidator implements ITradeValidationChain {

	ITradeValidationChain itradeValidationChain;
	Trade dbTrade;
	TradeRepository tradeRepository;

	public TradeVersionValidator(TradeRepository tradeRepository) {
		this.tradeRepository = tradeRepository;
	}

	@Override
	public void setNextChain(ITradeValidationChain itradeValidationChain) {
		this.itradeValidationChain = itradeValidationChain;

	}

	@Override
	public boolean validateTrade(Trade trade) {
		Optional<Trade> dbTrade = tradeRepository.findFirstByTradeIdOrderByVersionDesc(trade.getTradeId());
		if (dbTrade.isPresent()) 
			return validateVersion(trade, dbTrade.get());
		return true;
	}
	
    private boolean validateVersion(Trade trade,Trade dbTrade) {
        if(trade.getVersion() >= dbTrade.getVersion())
            return true;
        throw new TradeInvalidException(trade.getTradeId() + " lower version is being received by the store");
    }

}
