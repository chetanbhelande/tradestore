package com.db.tradestore.util;

import java.time.LocalDate;

import com.db.tradestore.exception.TradeInvalidException;
import com.db.tradestore.model.Trade;

public class TradeMaturityDateValidator implements ITradeValidationChain {

	ITradeValidationChain itradeValidationChain;

	@Override
	public void setNextChain(ITradeValidationChain itradeValidationChain) {
		this.itradeValidationChain = itradeValidationChain;
	}

	@Override
	public boolean validateTrade(Trade trade) {
		if (trade.getMaturityDate().isBefore(LocalDate.now()))
			throw new TradeInvalidException(trade.getTradeId() + " trade has less maturity date then today date");
		else
			return itradeValidationChain.validateTrade(trade);

	}

}
