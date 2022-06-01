package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CustomerAccountDatabase {

    private List<CustomerAccount> customerAccountList;
    private CustomerAccount currentUser;
    private BankingSystemMenu systemMenu;
    private static SQLiteDataSource dataSource;
    private static String url;

    public CustomerAccountDatabase(BankingSystemMenu systemMenu, String[] args) throws SQLException {
        this.customerAccountList = new ArrayList<>();
        this.systemMenu = systemMenu;
        dataSource = new SQLiteDataSource();
        url = "jdbc:sqlite:" + args[1];
        dataSource.setUrl(url);
        initSQLiteDatabase();
        loadCustomerAccountList();
    }

    private ResultSet sendQueryToSQLiteDatabase(String query) {
        ResultSet resultSet = null;

        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(5)) {
                try (Statement statement = connection.createStatement()) {
                    resultSet = statement.executeQuery(query);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    private void initSQLiteDatabase() {
        sendQueryToSQLiteDatabase("CREATE TABLE card (id INTEGER, number TEXT, " +
                "pin TEXT, balance INTEGER DEFAULT 0);");
    }

    private void loadCustomerAccountList() throws SQLException {
        ResultSet resultSet = sendQueryToSQLiteDatabase("SELECT * FROM cards");

        if (!(Objects.isNull(resultSet))) {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String cardNumber = resultSet.getString(2);
                String PINCode = resultSet.getString(3);
                Integer balance = Integer.valueOf(resultSet.getInt(4));

                CustomerAccount newCustomerAccount = new CustomerAccount(id, cardNumber, PINCode, balance);
                customerAccountList.add(newCustomerAccount);
            }

            resultSet.close();
        }
    }

    public void setCurrentUser(CustomerAccount currentUser) {
        this.currentUser = currentUser;
    }

    public CustomerAccount getCurrentUser() {
        return currentUser;
    }

    public List<CustomerAccount> getCustomerAccountList() {
        return customerAccountList;
    }

    public void addNewCustomerAccount(CustomerAccount customerAccount) {
        customerAccountList.add(customerAccount);

        sendQueryToSQLiteDatabase("INSERT INTO card(id, number, pin, balance)" +
                "VALUES(" + customerAccount.getIdentificationNumber() + ", '" + customerAccount.getCardNumber() +
                "', '" + customerAccount.getPINCode() + "', " + customerAccount.getAccountBalance() + ");");
    }

    public void loginCustomerAccount(String loginCardNumber, String loginPINCode, Scanner userInput) {
        CustomerAccount checkedCustomerAccount;

        for (int i = 0; i < customerAccountList.size(); i++) {
            checkedCustomerAccount = customerAccountList.get(i);

            if (loginCardNumber.equals(checkedCustomerAccount.getCardNumber()) &&
                    loginPINCode.equals(checkedCustomerAccount.getPINCode())) {
                setCurrentUser(customerAccountList.get(i));
                System.out.println("\nYou have successfully logged in!\n");
                CustomerAccountMenu userLoggedIn = new CustomerAccountMenu(this, systemMenu,
                        userInput);
                userLoggedIn.runUserAccount();

            } else {
                System.out.println("Wrong card number or PIN!");
                systemMenu.printMainMenu();
            }
        }
    }

    public void removeCustomerAccount(CustomerAccount customerAccount) {
        customerAccountList.remove(customerAccount);
    }

    public void addIncomeToCustomerAccountBalance(int incomeToAdd) {
        currentUser.setAccountBalance(incomeToAdd);

        sendQueryToSQLiteDatabase("UPDATE card " +
                "SET balance = " + incomeToAdd +" " +
                "WHERE number = " + currentUser.getCardNumber() + ";");
    }

    public boolean cardNumberExistsInDatabase(String cardNumber) {
        for (CustomerAccount customerAccount : customerAccountList) {
            if (cardNumber.equals(customerAccount.getCardNumber())) {
                return true;
            }
        }
        return false;
    }

    public void transferAmountBetweenCustomerAccounts(String transferToCardNumber) {

    }
}
