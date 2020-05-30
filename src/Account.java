import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {
    private int id;
    private double balanceUSD;
    private double balanceKGS;
    private String name;
    private User accountHolder;
    List<Transaction> transactions;

    public Account(int id, String name, User accountHolder) {
        this.id = id;
        this.name = name;
        this.accountHolder = accountHolder;
    }

    public Account () {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalanceUSD() {
        return balanceUSD;
    }

    public double getBalanceKGS() {
        return balanceKGS;
    }

    public void depositBalanceUSD(double isDepositedUSD) {
        this.balanceUSD += isDepositedUSD;
    }

    public void depositBalanceKSG(double isDepositedKGS) {
        this.balanceKGS += isDepositedKGS;
    }

    public void withdrawBalanceUSD(double isWithdrawnUSD) {
        this.balanceUSD -= isWithdrawnUSD;
    }

    public void withdrawBalanceKGS(double isWithdrawnKGS) {
        this.balanceKGS -= isWithdrawnKGS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(User accountHolder) {
        this.accountHolder = accountHolder;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
