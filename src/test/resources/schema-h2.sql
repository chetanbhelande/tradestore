CREATE TABLE Trades(trade_id VARCHAR, version INT,counter_party VARCHAR, book_id VARCHAR, maturity_date DATE,created_date date,expired_flag varchar,PRIMARY KEY(trade_id,version));