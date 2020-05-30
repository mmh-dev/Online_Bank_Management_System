import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private double amount;
    private String typeOfTransaction;
    private Date timeStamp;
    private Account account;

    public Transaction(double amount, String typeOfTransaction, Date timestamp, Account account) {
        this.amount = amount;
        this.typeOfTransaction = typeOfTransaction;
        this.timeStamp = timestamp;
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(String typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimestamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
