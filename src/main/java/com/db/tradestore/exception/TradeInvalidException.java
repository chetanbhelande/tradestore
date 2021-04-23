package com.db.tradestore.exception;

public class TradeInvalidException extends RuntimeException {

	private static final long serialVersionUID = -2131021174516518100L;
	private final String id;

	public TradeInvalidException(final String id) {
		super("Invalid Trade: " + id);
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
