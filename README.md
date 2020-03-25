# bank-transactions

This is my bank-transactions application. I've used Java 8 with Spring Boot (and other Spring modules) to create this project. For the DB I've used H2.

Before continuing, the assumptions I've done:
  - When a channel is not present, they default will be 'CLIENT'.
  - The default sorting is 'DESC'
  - Checking the status is performed through a POST (as they take a Payload)
  - I've included a unique transaction GET (to get only 1 transaction, as I assume this could be important)

To get straight to the point, this are the different endpoints to test the app (assuming you've started it on localhost):
  - GET: http://localhost:8080/transactions ; which searches a list of transactions and has two optional request params (account_id and sort [that can be ASC or DESC -- if mistaken, will be taken as DESC])
  - GET: http://localhost:8080/transactions/{reference} ; which searches a unique transaction by its reference
  - POST: GET: http://localhost:8080/transactions ; which takes a Transaction object and creates it (if possible)
  - POST: http://localhost:8080/transactions/status ; which takes the status input (reference and channel [opt]) and gives back the required status output.

In order to create this application, which has several functionalities (find all transactions, find one transaction, create a transaction, check transaction status), the DB model I've created consists of two entities (transactions and accounts) that are related through a FK in transactions.

To end my explanation, I believe that, even though the test coverage is really high, testing could be improved as I've left out an integration test for verifying sorting because I found a problem when receiving the JSonArray in the test (and I didn't have the time to look deeper into it).
  
