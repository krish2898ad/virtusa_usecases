// Custom exception thrown when a user tries to spend more money than they currently have in their wallet.
public class InSufficientFundsException extends Exception {
    public InSufficientFundsException(String message) {
        super(message);
    }
}



