package com.db.tradestore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import com.db.tradestore.model.Trade;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TradeRepositoryTest {

	@Autowired
	private TradeRepository tradeRepository;

	@Test
	@Order(1)
	public void should_find_all_trades() {

		Iterable<Trade> trades = tradeRepository.findAll();

		int nOfTrade = 3;

		assertThat(trades).hasSize(nOfTrade);
	}

	@Test
	@Order(2)
	public void save_trades() {

		Trade trade = createTrade("T3", 1, LocalDate.now());

		tradeRepository.save(trade);

		Iterable<Trade> trades = tradeRepository.findAll();
		int nOfTrade = 4;
		assertThat(trades).hasSize(nOfTrade);
	}

	@Test
	@Order(3)
	public void remove_trades() {

		Trade trade = createTrade("T3", 1, LocalDate.now());

		tradeRepository.delete(trade);

		Iterable<Trade> trades = tradeRepository.findAll();
		int nOfTrade = 3;
		assertThat(trades).hasSize(nOfTrade);
	}

	@Test
	@Order(4)
	public void find_trade_with_latestest_version() {

		Optional<Trade> trade = tradeRepository.findFirstByTradeIdOrderByVersionDesc("T2");

		assertThat(trade.get().getVersion()).isEqualTo(2);
	}

	private Trade createTrade(String tradeId, int version, LocalDate maturityDate) {
		Trade trade = new Trade();
		trade.setTradeId(tradeId);
		trade.setBookId(tradeId);
		trade.setVersion(version);
		trade.setCounterParty(tradeId + "Cpty");
		trade.setMaturityDate(maturityDate);
		trade.setExpiredFlag("Y");
		return trade;
	}
}
