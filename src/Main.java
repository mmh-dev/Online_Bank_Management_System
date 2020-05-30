import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    static User loggedUser = new User();
    private static ArrayList <Integer> ids = new ArrayList<>();

    public static void main(String[] args) {

//        User timur = new User(getUniqueID(),"Timur", "Amanov", "timur", "tamanov");
//        User murod = new User(getUniqueID(),"Murod", "Khodzhaev", "murod", "mkhodzhaev");
//        User ikbol = new User(getUniqueID(),"Ikbol", "Tashpolotov", "ikbol", "itashpolotov");
//
//        Account timurUSD = new Account(getUniqueIDAccount(),"USD", timur);
//        Account timurKGS = new Account(getUniqueIDAccount(),"KGS", timur);
//        List<Account> timurAccounts = new ArrayList<>();
//        timurAccounts.add(timurUSD);
//        timurAccounts.add(timurKGS);
//        timur.setAccountList(timurAccounts);
//
//        Account murodUSD = new Account(getUniqueIDAccount(),"USD", murod);
//        Account murodKGS = new Account(getUniqueIDAccount(),"KGS", murod);
//        List<Account> murodAccounts = new ArrayList<>();
//        murodAccounts.add(murodUSD);
//        murodAccounts.add(murodKGS);
//        murod.setAccountList(murodAccounts);
//
//        Account ikbolUSD = new Account(getUniqueIDAccount(),"USD", ikbol);
//        Account ikbolKGS = new Account(getUniqueIDAccount(),"KGS", ikbol);
//        List<Account> ikbolAccounts = new ArrayList<>();
//        ikbolAccounts.add(ikbolUSD);
//        ikbolAccounts.add(ikbolKGS);
//        ikbol.setAccountList(ikbolAccounts);

        List<User> customers = new ArrayList<>();
//        customers.add(timur);
//        customers.add(murod);
//        customers.add(ikbol);

        List<Account> accounts = new ArrayList<>();
//        accounts.add(timurKGS);
//        accounts.add(timurUSD);
//        accounts.add(murodKGS);
//        accounts.add(murodUSD);
//        accounts.add(ikbolKGS);
//        accounts.add(ikbolUSD);

        String bankName = "Demir Bank";
        Bank bank = new Bank(bankName, customers, accounts);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to Demir Bank Online Banking...");
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bankDatabase"));
            bank = (Bank) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("Bank database is missing");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        mainMenu (bank, reader);
    }

    private static void mainMenu(Bank bank, BufferedReader reader) {
        byte loggingAttempts = 0;
        while (true){
            try {
                System.out.println();
                System.out.println("Please, enter your login...");
                String login = reader.readLine();
                System.out.println("Please, enter your password");
                String password = reader.readLine();
                while (true){
                    for ( User u: bank.getCustomers()) {
                        if (u.getLogin().equals(login) && u.getPassword().equals(password)){
                            System.out.println("Welcome back, " + u.getFirstName() + " " + u.getLastName() + "!");
                            loggedUser = u;
                            loggedMenu(bank,reader);
                        }
                    }
                    if (loggingAttempts < 2){
                        loggingAttempts++;
                        System.out.println("Login and/or password is incorrect. Try again!");
                        System.out.println("Please enter your login...");
                        login = reader.readLine();
                        System.out.println("Please enter your password...");
                        password = reader.readLine();
                    }
                    else {
                        System.out.println("You entered login and/or password incorrectly 3 times. Please, contact the Bank administration for verification");
                        System.exit(0);
                    }
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Please, enter valid information...");
                mainMenu(bank,reader);
            }
        }
    }

    private static void loggedMenu(Bank bank, BufferedReader reader) {
        System.out.println();
        System.out.println("Please, choose an option...");
        System.out.println();
        System.out.println("1. My accounts");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer cash");
        System.out.println("5. Transaction history");
        System.out.println("6. Manage my personal information");
        System.out.println("7. Logout");

        try {
            byte choice = Byte.parseByte(reader.readLine());
            switch (choice){
                case 1:
                    accountInfo ();
                    loggedMenu(bank, reader);
                case 2:
                    deposit(reader);
                    loggedMenu(bank, reader);
                case 3:
                    withdraw(reader);
                    loggedMenu(bank, reader);
                case 4:
                    transfer(bank, reader);
                    loggedMenu(bank, reader);
                case 5:
                    transactionHistory(reader);
                    loggedMenu(bank, reader);
                case 6:
                    manageUserData(bank, reader);
                    loggedMenu(bank, reader);
                case 7:
                    System.out.println("Logging out...");
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bankDatabase"));
                        oos.writeObject(bank);
                        oos.close();
                    } catch (IOException e) {
                        System.out.println("Bank database is corrupted");
                    }
                    System.exit(0);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Please, enter a valid digit");
            loggedMenu(bank,reader);
        }
    }

    private static void accountInfo() {
        User user = loggedUser;
        System.out.println(user.getFirstName() + " " + user.getLastName() + " has following accounts:");
        System.out.println();
        System.out.println("USD account: id: " + user.getAccountList().get(0).getId() + " | balance: " + user.getAccountList().get(0).getBalanceUSD());
        System.out.println("-----------------------------------------------------");
        System.out.println("KGS account: id: " + user.getAccountList().get(1).getId() + " | balance: " + user.getAccountList().get(1).getBalanceKGS());
    }

    private static void deposit(BufferedReader reader) throws IOException {
        User user = loggedUser;
        System.out.println("Which account do you want to deposit?");
        System.out.println("1. USD");
        System.out.println("2. KGS");
        byte choice = Byte.parseByte(reader.readLine());
        System.out.println("Please enter your deposit...");
        double deposit = Double.parseDouble(reader.readLine());

        if (choice == 1){
            user.getAccountList().get(0).depositBalanceUSD(deposit);
            Date date = new Date();
            Transaction t = new Transaction(deposit,"deposit", date, user.getAccountList().get(0));
            List<Transaction> transactions = user.getAccountList().get(0).getTransactions();
            transactions.add(t);
            user.getAccountList().get(0).setTransactions(transactions);
            System.out.println(deposit + " USD has been deposited to your account");
        }
        else if (choice == 2){
            user.getAccountList().get(1).depositBalanceKSG(deposit);
            Date date = new Date();
            Transaction t = new Transaction(deposit,"deposit", date, user.getAccountList().get(1));
            List<Transaction> transactions = user.getAccountList().get(1).getTransactions();
            transactions.add(t);
            user.getAccountList().get(1).setTransactions(transactions);
            System.out.println(deposit + " KGS has been deposited to your account");
        }
    }

    private static void withdraw(BufferedReader reader) throws IOException {
        User user = loggedUser;
        System.out.println("From which account do you want to withdraw?");
        System.out.println("1. USD");
        System.out.println("2. KGS");
        byte choice = Byte.parseByte(reader.readLine());
        System.out.println("Please enter the amount to withdraw...");
        double isWithdrawn = Double.parseDouble(reader.readLine());

        if (choice == 1){
            user.getAccountList().get(0).withdrawBalanceUSD(isWithdrawn);
            Date date = new Date();
            Transaction t = new Transaction(isWithdrawn,"withdrawal", date, user.getAccountList().get(0));
            List<Transaction> transactions = user.getAccountList().get(0).getTransactions();
            transactions.add(t);
            user.getAccountList().get(0).setTransactions(transactions);
            System.out.println(isWithdrawn + " USD has been withdrawn from your account");
        }
        else if (choice == 2){
            user.getAccountList().get(1).withdrawBalanceKGS(isWithdrawn);
            Date date = new Date();
            Transaction t = new Transaction(isWithdrawn,"withdrawal", date, user.getAccountList().get(1));
            List<Transaction> transactions = user.getAccountList().get(1).getTransactions();
            transactions.add(t);
            user.getAccountList().get(1).setTransactions(transactions);
            System.out.println(isWithdrawn + " KGS has been withdrawn from your account");
        }
    }

    private static void transfer(Bank bank, BufferedReader reader) throws IOException {
        User user = loggedUser;
        double balance = 0;
        Account account = new Account();
        System.out.println("From which account do you want to transfer?");
        System.out.println("1. USD");
        System.out.println("2. KGS");
        byte choice = Byte.parseByte(reader.readLine());
        if (choice == 1){
            account = user.getAccountList().get(0);
            balance = account.getBalanceUSD();
            System.out.println("You have " + balance + " USD in your account");
        }
        else if (choice == 2){
            account = user.getAccountList().get(1);
            balance = account.getBalanceKGS();
            System.out.println("You have " + balance + " KGS in your account");
        }
        System.out.println("Please enter the amount to transfer...");
        double transfer = Double.parseDouble(reader.readLine());
        if(transfer > balance){
            System.out.println("Not enough money in your account");
            loggedMenu(bank, reader);
        }
        else {
            System.out.println("Please enter the account ID to transfer...");
            System.out.println();
            for (Account a: bank.getAccounts()) {
                System.out.println("ID: " + a.getId() + " | Account holder: " + a.getAccountHolder().getFirstName() + " " + a.getAccountHolder().getLastName() + " | Currency: " + a.getName());
                System.out.println("-----------------------------------------------------------------------");
            }
            int chosenAccount = Integer.parseInt(reader.readLine());
            for (Account a: bank.getAccounts()) {
                if (a.getId() == chosenAccount && choice == 1){
                    a.depositBalanceUSD(transfer);
                    account.withdrawBalanceUSD(transfer);

                    Date date = new Date();
                    Transaction t = new Transaction(transfer,"outgoing transfer", date, account);
                    List<Transaction> transactions = account.getTransactions();
                    transactions.add(t);
                    account.setTransactions(transactions);

                    Transaction t1 = new Transaction(transfer,"incoming transfer", date, a);
                    List<Transaction> transactions1 = a.getTransactions();
                    transactions1.add(t1);
                    a.setTransactions(transactions1);

                    System.out.println(transfer + " USD has been transferred to the account " + a.getId() + ", owned by " + a.getAccountHolder().getFirstName() + " " + a.getAccountHolder().getLastName());
                }
                else if (a.getId() == chosenAccount && choice == 2){
                    a.depositBalanceKSG(transfer);
                    account.withdrawBalanceKGS(transfer);

                    Date date = new Date();
                    Transaction t = new Transaction(transfer,"outgoing transfer", date, account);
                    List<Transaction> transactions = account.getTransactions();
                    transactions.add(t);
                    account.setTransactions(transactions);

                    Transaction t1 = new Transaction(transfer,"incoming transfer", date, a);
                    List<Transaction> transactions1 = a.getTransactions();
                    transactions1.add(t1);
                    a.setTransactions(transactions1);

                    System.out.println(transfer + " KGS has been transferred to the account " + a.getId() + ", owned by " + a.getAccountHolder().getFirstName() + " " + a.getAccountHolder().getLastName());
                }
            }
        }

    }

    private static void transactionHistory(BufferedReader reader) throws IOException {
        User user = loggedUser;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss");
        System.out.println("Please, select the account...");
        System.out.println();
        System.out.println("1. USD");
        System.out.println("2. KGS");
        byte choice = Byte.parseByte(reader.readLine());
        if (choice == 1){
            for (Transaction t: user.getAccountList().get(0).getTransactions()) {
                System.out.println("Amount: " + t.getAmount() + " | Type of transaction: " + t.getTypeOfTransaction() + " | Time: " + sdf.format(t.getTimeStamp()));
                System.out.println("----------------------------------------------------------");
            }
        }
        else if (choice == 2){
            for (Transaction t: user.getAccountList().get(1).getTransactions()) {
                System.out.println("Amount: " + t.getAmount() + " | Type of transaction: " + t.getTypeOfTransaction() + " | Time: " + sdf.format(t.getTimeStamp()));
                System.out.println("----------------------------------------------------------");
            }
        }
    }

    private static void manageUserData(Bank bank, BufferedReader reader) {
        User user = loggedUser;
        System.out.println("Your personal information:");
        System.out.println("---------------------------------------------");
        System.out.println("First Name: " + user.getFirstName());
        System.out.println("Last Name: " + user.getLastName());
        System.out.println("Login: " + user.getLogin());
        System.out.println("Password: " + user.getPassword());
        System.out.println("Accounts: " + user.getAccountList().get(0).getName() + ", " + user.getAccountList().get(1).getName());
        System.out.println("---------------------------------------------");
        System.out.println();
        System.out.println("Please choose your option...");
        System.out.println();
        System.out.println("1. Change my first name");
        System.out.println("2. Change my last name");
        System.out.println("3. Change my login");
        System.out.println("4. Change my password");
        System.out.println("5. Exit to main menu");

        try {
            byte choice = Byte.parseByte(reader.readLine());
            switch (choice){
                case 1:
                    System.out.println("Please, enter your new first name...");
                    String firstName = reader.readLine();
                    user.setFirstName(firstName);
                    System.out.println("Your first name has been changed!");
                    loggedMenu(bank, reader);
                case 2:
                    System.out.println("Please, enter your new last name...");
                    String lastName = reader.readLine();
                    user.setLastName(lastName);
                    System.out.println("Your last name has been changed!");
                    loggedMenu(bank, reader);
                case 3:
                    System.out.println("Please, enter your new login...");
                    String login = reader.readLine();
                    user.setLogin(login);
                    System.out.println("Your login has been changed! Please, sign in again with new login...");
                    mainMenu(bank, reader);
                case 4:
                    System.out.println("Please, enter your new password...");
                    String password = reader.readLine();
                    user.setPassword(password);
                    System.out.println("Your password has been changed! Please, sign in again with new password...");
                    mainMenu(bank, reader);
                case 5:
                    loggedMenu(bank, reader);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Please, enter a valid digit");
            loggedMenu(bank,reader);
        }

    }

    public static int getUniqueID (){
        int id = 0;
        while (true){
            id = (int) (Math.random() * 899) + 100;  // generates 3 digit unique ids
            if (checkForDuplicates(id)){
                ids.add(id);
                break;
            }
        }
        return id;
    }

    public static int getUniqueIDAccount (){
        int id = 0;
        while (true){
            id = (int) (Math.random() * 900000) + 100000;  // generates 3 digit unique ids
            if (checkForDuplicates(id)){
                ids.add(id);
                break;
            }
        }
        return id;
    }

    public static boolean checkForDuplicates (int id){
        for (int i: ids) {
            if (i == id){
                return false;
            }
        }
        return true;
    }
}
