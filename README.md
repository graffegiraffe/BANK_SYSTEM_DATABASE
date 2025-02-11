# Bank Account Management System using JDBC

## Project Description
This is a Java application designed to interact with a database using JDBC. The application allows for managing bank accounts, including creating new accounts, viewing account balances by account number, and transferring funds between accounts. Funds transfer is implemented as a transaction to ensure data integrity (either the whole operation succeeds or no changes are made in case of an error). Additionally, all transactions are logged in a separate table.

---

## Database Structure

### Table `accounts`
Stores information about bank accounts.

| Column           | Type         | Description                  |
|-------------------|--------------|------------------------------|
| `id`             | `BIGSERIAL`  | Primary Key (PK)             |
| `account_number` | `VARCHAR`    | Bank account number          |
| `balance`        | `DECIMAL`    | Account balance              |

### Table `transactions`
Logs all fund transfers.

| Column            | Type         | Description                        |
|--------------------|--------------|------------------------------------|
| `transaction_id`  | `BIGSERIAL`  | Primary Key (PK)                   |
| `from_account`    | `VARCHAR`    | Sender's account number            |
| `to_account`      | `VARCHAR`    | Recipient's account number         |
| `amount`          | `DECIMAL`    | Amount transferred                 |
| `timestamp`       | `TIMESTAMP`  | Timestamp of the transaction        |

---

## Key Application Features

### 1. Create an Account
A method to add a new account with an initial balance.

- **Description**: Creates a new entry in the `accounts` table with the specified account number and initial balance.
- **Validation**: Ensures account numbers are unique to avoid duplicates.

### 2. View Account Balance
A method to retrieve the current balance of an account by its account number.

- **Description**: Retrieves the account's balance from the `accounts` table.

### 3. Transfer Funds
A method to transfer money from one account to another.

- **Description**:
  - Checks that both the sender and recipient accounts exist.
  - Verifies that the sender has sufficient funds for the transfer.
  - Updates the balances of both accounts in the `accounts` table.
  - Logs the transaction in the `transactions` table.
  - Ensures data integrity by implementing the transfer as a transaction.
- **Validation**:
  - Amount to transfer must be greater than zero.
  - Ensures both accounts exist before proceeding.
  - Rolls back the transaction if the transfer fails for any reason.

---

## Transactions

The funds transfer operation is implemented as a transaction:
- All changes (deducting the sender's balance, adding to the recipient's balance, and logging the transaction) are completed as a single unit of work.
- If any part of the transaction fails (insufficient funds, SQL errors, or logic errors), **all changes are rolled back**.

### Workflow:
1. Disable auto-commit mode for the connection.
2. Validate the balances and update the records in the `accounts` table.
3. Insert a record into the `transactions` table with the details of the operation.
4. Commit the transaction.
5. On any error, rollback the transaction.

---

## Project Configuration

### 1. Database Configuration
The application uses PostgreSQL as its database. The database credentials are defined in the `DatabaseConfig.java` file:

- Database connection URL: `jdbc:postgresql://localhost:5432/postgres`
- Username: `katusha`
- Password: `katusha`

You may adjust these credentials based on your environment.

### 2. Dependency Management
The project uses Maven for dependency management. Ensure the dependency for PostgreSQL is included in your `pom.xml`:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.4</version>
</dependency>
```

---

## How to Test the Application

### Sample Scenario:
1. Create two accounts:
   - `25112005` with an initial balance of `1000`.
   - `50021152` with an initial balance of `500`.
2. Display both account balances:
   - Balance for `25112005`: `1000`.
   - Balance for `50021152`: `500`.
3. Transfer `200` from account `25112005` to account `50021152`.
4. Display balances after the transfer:
   - Balance for `25112005`: `800`.
   - Balance for `50021152`: `700`.

### Expected Behavior:
- The transfer completes successfully if the transfer amount is greater than zero and if the sender has sufficient funds.
- A record of the transaction is added to the `transactions` table, including details such as the sender, recipient, amount, and timestamp.

---

## Error Handling

### Possible Exceptions:
1. Sender’s account has insufficient funds.
2. Either the sender or recipient account does not exist.
3. Transfer amount is less than or equal to zero.
4. SQL connection or query execution errors.

**All exceptions are appropriately logged and handled to ensure the application remains stable.**

---

## Additional Features

To improve traceability, a `transactions` table was implemented to log every fund transfer. This includes details such as:
- Sender's account number.
- Recipient's account number.
- Transfer amount.
- Timestamp of the operation.

---

## Requirements
- **Java**: Version 21.
- **PostgreSQL JDBC Driver**: Version 42.7.4.
- **PostgreSQL**: Database server.

---

## Project Setup and Structure

### 1. Build the Project
The project can be built and tested using Maven:
```bash
mvn clean install
```

### 2. Run the Application
Run the `main` method in the `Application` class to execute the program.

---

## Possible Future Improvements
- Add more operations like closing accounts and viewing transaction history.
- Implement a REST API to interact with the system via HTTP.
- Introduce a connection pool for improved performance and scalability (e.g., using HikariCP).

---

Developed by **Kate Rublevskaya**.
