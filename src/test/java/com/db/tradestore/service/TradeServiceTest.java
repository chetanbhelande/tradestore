package com.db.tradestore.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.db.tradestore.exception.TradeInvalidException;
import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepository;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

	@InjectMocks
	private TradeService tradeService;

	@Mock
	private TradeRepository tradeRepository;

	@Test
	public void retrieveAlltrade() {

		when(tradeRepository.findAll()).thenReturn(
				Arrays.asList(createTrade("T3", 1, LocalDate.now()), createTrade("T2", 1, LocalDate.now())));

		List<Trade> trades = tradeService.findAll();

		String TRADE_ID = "T3";
		String TRADE_ID2 = "T2";

		assertEquals(TRADE_ID, trades.get(0).getTradeId());
		assertEquals(TRADE_ID2, trades.get(1).getTradeId());
	}

	@Test
	public void process_and_save_trade() {

		Trade trade = createTrade("T3", 3, LocalDate.now());

		when(tradeRepository.findFirstByTradeIdOrderByVersionDesc("T3"))
				.thenReturn(Optional.of(createTrade("T3", 2, LocalDate.now())));
		when(tradeRepository.save(trade)).thenReturn(trade);

		Trade saveTrade = tradeService.processAndSaveTrade(trade);

		String TRADE_ID = "T3";
		String TRADE_ID2 = "T2";

		assertEquals(TRADE_ID, saveTrade.getTradeId());
	}

	@Test
	public void process_and_save_trade_with_lower_version_trade_exception() {

		Trade trade = createTrade("T3", 1, LocalDate.now());

		when(tradeRepository.findFirstByTradeIdOrderByVersionDesc("T3"))
				.thenReturn(Optional.of(createTrade("T3", 2, LocalDate.now())));
		// when(tradeRepository.save(trade)).thenReturn(trade);

		Exception exception = assertThrows(TradeInvalidException.class, () -> {
			tradeService.processAndSaveTrade(trade);
		});

		String expectedMessage = "T3 lower version is being received by the store";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	private Trade createTrade(String tradeId, int version, LocalDate maturityDate) {
		Trade trade = new Trade();
		trade.setTradeId(tradeId);
		trade.setBookId(tradeId + "B1");
		trade.setVersion(version);
		trade.setCounterParty(tradeId + "Cpty");
		trade.setMaturityDate(maturityDate);
		trade.setExpiredFlag("Y");
		return trade;
	}
}
