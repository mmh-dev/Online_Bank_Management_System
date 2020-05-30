import java.io.Serializable;
import java.util.List;

public class Bank implements Serializable {
    private String bankName;
    private List<User> customers;
    private List<Account> accounts;

    public Bank(String bankName, List<User> customers, List<Account> accounts) {
        this.bankName = bankName;
        this.customers = customers;
        this.accounts = accounts;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public List<User> getCustomers() {
        return customers;
    }

    public void setCustomers(List<User> customers) {
        this.customers = customers;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
