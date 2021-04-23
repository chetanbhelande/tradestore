package com.db.tradestore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.db.tradestore.model.Trade;
import com.db.tradestore.model.TradeId;

@Repository
public interface TradeRepository extends JpaRepository<Trade,TradeId> {
	Optional<Trade> findFirstByTradeIdOrderByVersionDesc(String id);
}
