package com.db.tradestore.util;

import com.db.tradestore.model.Trade;

public interface ITradeValidationChain {

	void setNextChain(ITradeValidationChain itradeValidationChain);
	
	boolean validateTrade(Trade trade);
}
