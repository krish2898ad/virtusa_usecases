import java.util.Scanner;

public class FinSafeApp {

    private static Scanner scanner = new Scanner(System.in);
    private static Account account = null;
    private static boolean isLoggedIn = false;

    // Password validation
    private static boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*")
                && password.matches(".*\\d.*") && password.matches(".*[@#$%^&+=!].*");
    }

    // Create Account
    public static void createAccount() {
        System.out.print("Enter name: ");
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

        account = new Account(name, balance, password);
        System.out.println("Account created successfully!\n");
    }

    // Login
    public static void login() {
        if (account == null) {
            System.out.println("No account found.\n");
            return;
        }
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String pass = scanner.nextLine();

        if (account.getAccountHolder().equals(name) &&
                account.getPassword().equals(pass)) {

            isLoggedIn = true;
            System.out.println("Login successful!\n");

        } else {
            System.out.println("Invalid credentials\n");
        }
    }

    // Deposit
    public static void deposit() {
        if (!isLoggedIn) {
            System.out.println("Login first!\n");
            return;
        }

        System.out.print("Enter amount: ");
        try {
            double amt = Double.parseDouble(scanner.nextLine());
            account.deposit(amt);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Withdraw
    public static void withdraw() {
        if (!isLoggedIn) {
            System.out.println("Login first!\n");
            return;
        }

        System.out.print("Enter amount: ");
        try {
            double amt = Double.parseDouble(scanner.nextLine());
            account.processTransaction(amt);
        } catch (InSufficientFundsException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    // Statement
    public static void statement() {
        if (!isLoggedIn) {
            System.out.println("Login first!\n");
            return;
        }
        account.printMiniStatement();
    }

    public static void main(String[] args) {

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
}