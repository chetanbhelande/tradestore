package com.db.tradestore.model;

import java.io.Serializable;

public class TradeId implements Serializable {

	private static final long serialVersionUID = 1L;
	private String tradeId;
	private int version;

	public TradeId(String tradeId, int version) {
		this.tradeId = tradeId;
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tradeId == null) ? 0 : tradeId.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TradeId other = (TradeId) obj;
		if (tradeId == null) {
			if (other.tradeId != null)
				return false;
		} else if (!tradeId.equals(other.tradeId))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	public TradeId() {
		super();
	}

	
}