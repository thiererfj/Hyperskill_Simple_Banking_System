package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BankingSystemMenu {

    private final Scanner userInput = new Scanner(System.in);
    private CustomerAccountDatabase customerAccountDatabase;

    public BankingSystemMenu(String[] args) throws SQLException {
        this.customerAccountDatabase = new CustomerAccountDatabase(this, args);
        printMainMenu();
    }

    public void printMainMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit\n");

        runUserOption();
    }

    public void runUserOption() {
        while (userInput.hasNext()) {
            switch (userInput.nextInt()) {
                case 0: userInput.nextLine();
                        exitSystem();
                        break;
                case 1: userInput.nextLine();
                        createAccount();
                        break;
                case 2: userInput.nextLine();
                        logIntoAccount();
                        break;
                default:
                        userInput.nextLine();
                        System.out.println("Enter an option");
                        break;
            }
        }
    }

    public void exitSystem() {
        System.out.println("\nBye!");

        System.exit(0);
    }

    public void createAccount() {
        CreateCustomerAccount accountCreator = new CreateCustomerAccount(customerAccountDatabase);
        CustomerAccount newCustomerAccount = accountCreator.createNewAccount();
        customerAccountDatabase.addNewCustomerAccount(newCustomerAccount);

        System.out.println("Your card has been created");
        System.out.println("Your card number: ");
        System.out.println(newCustomerAccount.getCardNumber());
        System.out.println("Your card PIN: ");
        System.out.println(newCustomerAccount.getPINCode() + "\n");

        printMainMenu();
    }

    public void logIntoAccount() {
        System.out.println("Enter your card number: ");
        String loginCardNumber = userInput.nextLine();
        System.out.println("Enter your PIN code: ");
        String loginPINCode = userInput.nextLine();

        customerAccountDatabase.loginCustomerAccount(loginCardNumber, loginPINCode, userInput);
    }
}
