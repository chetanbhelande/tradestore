# tradestore

Problem Statement There is a scenario where thousands of trades are flowing into one store, assume any way of transmission of trades. We need to create a one trade store, which  stores the trade in the following order


#Service 

1. http://localhost:9090/trade { "tradeId":"T1", "version":1, "counterParty":"counterParty1",
"bookId": "bookId", "maturityDate": "2020-05-20", "expiredFlag":"Y" }

2. http://localhost:9090/trade to get all trade but pagination has not implemented in big system its not recommeded.

3. http://localhost:9090/trade/T1 : To get trade by ID 

#Test Cases Output
All test Case Passed.
    
