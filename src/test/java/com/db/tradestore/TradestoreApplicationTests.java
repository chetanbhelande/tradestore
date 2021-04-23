package com.db.tradestore;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.db.tradestore.controller.TradeController;
import com.db.tradestore.exception.TradeInvalidException;
import com.db.tradestore.model.Trade;
import com.db.tradestore.repository.TradeRepository;
import com.db.tradestore.service.TradeService;

@SpringBootTest
class TradestoreApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private TradeController tradeController;

	@InjectMocks
	private TradeService tradeService;

	@Mock
	private TradeRepository tradeRepository;

	@Test
	@Order(1)
	public void retrieveAll_trade() {

		ResponseEntity<List<Trade>> responseEntity = tradeController.findAllTrades();

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		List<Trade> tradeList = responseEntity.getBody();
		Assertions.assertEquals(3, tradeList.size());

	}

	@Test
	@Order(2)
	void testTradeValidateAndStore_successful() {
		ResponseEntity responseEntity = tradeController.processAndSaveTrade(createTrade("T4", 1, LocalDate.now()));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(), responseEntity);

		ResponseEntity<Trade> resEntity = tradeController.findTradeById("T4");
		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Trade tradeList = resEntity.getBody();

		Assertions.assertEquals("T4", tradeList.getTradeId());

	}

	@Test
	void testTradeStore_WhenMaturityDate_Past() {

		Exception exception = assertThrows(TradeInvalidException.class, () -> {
			LocalDate localDate = getLocalDate(2015, 05, 21);
			tradeController.processAndSaveTrade(createTrade("T5", 1, localDate));
		});

		String expectedMessage = "T5 trade has less maturity date then today date";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testTradeStore_WhenOldVersion_trade() {

		// t2 trade with version 2 is already loaded into hsql db by sql script.

		Exception exception = assertThrows(TradeInvalidException.class, () -> {
			tradeController.processAndSaveTrade(createTrade("T2", 1, LocalDate.now()));
		});

		String expectedMessage = "T2 lower version is being received by the store";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testTradeValidateAndUpdate_successful() {

		ResponseEntity responseEntity = tradeController.processAndSaveTrade(createTrade("T5", 1, LocalDate.now()));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(), responseEntity);

		LocalDate localDate = LocalDate.now();

		ResponseEntity responseEntity2 = tradeController.processAndSaveTrade(createTrade("T5", 1, localDate));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(), responseEntity2);

		ResponseEntity<Trade> resEntity = tradeController.findTradeById("T5");
		Assertions.assertEquals(HttpStatus.OK, resEntity.getStatusCode());
		Trade trade = resEntity.getBody();

		Assertions.assertEquals(localDate, trade.getMaturityDate());

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

	public static LocalDate getLocalDate(int year, int month, int day) {
		LocalDate localDate = LocalDate.of(year, month, day);
		return localDate;
	}

}
