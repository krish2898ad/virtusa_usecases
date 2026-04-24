import java.util.Scanner;

public class FinSafeApp {

    private Scanner scanner = new Scanner(System.in);
    private AccountService service = new AccountService();

    private Account currentAccount = null;
    private boolean isLoggedIn = false;

    // Password validation
    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[@#$%^&+=!].*");
    }

    // Create Account
    public void createAccount() {
        System.out.print("Enter username: ");
        String name = scanner.nextLine();

        System.out.print("Enter opening balance: ");
        double balance;

        try {
            balance = Double.parseDouble(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid balance.\n");
            return;
        }

        System.out.print("Set password: ");
        String password = scanner.nextLine();

        if (!isValidPassword(password)) {
            System.out.println("Password must contain:");
            System.out.println("At least 8 characters");
            System.out.println("Uppercase, lowercase, digit, special character\n");
            return;
        }

        if (service.createAccount(name, balance, password)) {
            System.out.println("Account created successfully!\n");
        } else {
            System.out.println("Username already exists!\n");
        }
    }

    // Login
    public void login() {

        System.out.print("Enter username: ");
        String name = scanner.nextLine();

        System.out.print("Enter password: ");
        String pass = scanner.nextLine();

        Account acc = service.login(name, pass);

        if (acc != null) {
            currentAccount = acc;
            isLoggedIn = true;
            System.out.println("Login successful!\n");
        } else {
            System.out.println("Invalid credentials\n");
        }
    }

    // Deposit
    public void deposit() {
        if (!isLoggedIn) {
            System.out.println("Login first!\n");
            return;
        }

        System.out.print("Enter amount: ");
        try {
            double amt = Double.parseDouble(scanner.nextLine());
            currentAccount.deposit(amt);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Withdraw
    public void withdraw() {
        if (!isLoggedIn) {
            System.out.println("Login first!\n");
            return;
        }

        System.out.print("Enter amount: ");
        try {
            double amt = Double.parseDouble(scanner.nextLine());
            currentAccount.processTransaction(amt);
        } catch (InSufficientFundsException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // Statement
    public void statement() {
        if (!isLoggedIn) {
            System.out.println("Login first!\n");
            return;
        }
        currentAccount.printMiniStatement();
    }

    // Main loop moved to instance method
    public void start() {

        while (true) {
            System.out.println("FinSafe Wallet");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Mini Statement");
            System.out.println("6. Exit");

            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createAccount();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    deposit();
                    break;
                case "4":
                    withdraw();
                    break;
                case "5":
                    statement();
                    break;
                case "6":
                    System.out.println("Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice\n");
            }
        }
    }

    // Only main remains static
    public static void main(String[] args) {
        FinSafeApp app = new FinSafeApp();
        app.start();
    }
}