package com.db.tradestore.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.DirtiesContext;
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

}
