import java.util.ArrayList;

// Handles deposits, withdrawals, and keeps a mini statement
public class Account {

    private String accountHolder;
    private double balance;
    private String password;

    // Stores last 5 transactions
    private ArrayList<Double> transactionHistory;
    private static final int MAX_HISTORY = 5;

    // Constructor
    public Account(String accountHolder, double balance, String password) {
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.password = password;
        this.transactionHistory = new ArrayList<>();
    }

    // Getters
    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    // Withdraw
    public void processTransaction(double amount) throws InSufficientFundsException {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }

        if (amount > balance) {
            throw new InSufficientFundsException(
                    "Insufficient balance! Available: Rs." + balance);
        }

        balance -= amount;
        recordTransaction(-amount);

        System.out.println("Withdrawal of Rs." + amount + " successful.");
    }

    // Deposit
    public void deposit(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit must be positive.");
        }

        balance += amount;
        recordTransaction(amount);

        System.out.println("Deposit of Rs." + amount + " successful.");
    }

    // Maintain last 5 transactions
    private void recordTransaction(double amount) {
        if (transactionHistory.size() == MAX_HISTORY) {
            transactionHistory.remove(0);
        }
        transactionHistory.add(amount);
    }

    // Mini Statement
    public void printMiniStatement() {

        System.out.println("\nMINI STATEMENT");
        System.out.println("Account Holder: " + accountHolder);

        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            int i = 1;
            for (double txn : transactionHistory) {
                if (txn > 0) {
                    System.out.printf("%d. CREDIT  + Rs.%.2f%n", i, txn);
                } else {
                    System.out.printf("%d. DEBIT   - Rs.%.2f%n", i, Math.abs(txn));
                }
                i++;
            }
        }

        System.out.printf("Current Balance: Rs.%.2f%n", balance);
    }
}