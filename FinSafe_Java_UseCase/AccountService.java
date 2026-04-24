import java.util.HashMap;

public class AccountService {

    private HashMap<String, Account> accounts = new HashMap<>();

    public boolean createAccount(String name, double balance, String password) {
        if (accounts.containsKey(name)) {
            return false;
        }

        accounts.put(name, new Account(name, balance, password));
        return true;
    }

    public Account login(String name, String password) {
        Account acc = accounts.get(name);

        if (acc != null && acc.getPassword().equals(password)) {
            return acc;
        }
        return null;
    }
}