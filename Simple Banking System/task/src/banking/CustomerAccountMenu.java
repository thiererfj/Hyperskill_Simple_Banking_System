package banking;

import java.util.Scanner;

public class CustomerAccountMenu {

    private final CustomerAccountDatabase customerAccountDatabase;
    private final CustomerAccount currentUser;
    private final BankingSystemMenu systemMenu;
    private final Scanner userInput;

    public CustomerAccountMenu(CustomerAccountDatabase customerAccountDatabase, BankingSystemMenu systemMenu,
                               Scanner userInput) {
        this.customerAccountDatabase = customerAccountDatabase;
        this.currentUser = customerAccountDatabase.getCurrentUser();
        this.systemMenu = systemMenu;
        this.userInput = userInput;
    }

    public void runUserAccount() {
        printUserMenu();
        runUserOption();
    }

    public void printUserMenu() {
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit\n");
    }

    public void runUserOption() {
        while (userInput.hasNext()) {
            switch (userInput.nextInt()) {
                case 0: userInput.nextLine();
                        exitSystem();
                        break;
                case 1: userInput.nextLine();
                        checkBalance();
                        break;
                case 2: userInput.nextLine();
                        addIncome();
                        break;
                case 3: userInput.nextLine();
                        doTransfer();
                        break;
                case 4: userInput.nextLine();
                        closeAccount();
                        break;
                case 5: userInput.nextLine();
                        logOut();
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

    public void checkBalance() {
        System.out.println("Balance: " + currentUser.getAccountBalance() + "\n");
        runUserAccount();
    }

    public void addIncome() {
        System.out.println("Enter income: ");
        int incomeToAdd = userInput.nextInt();
        customerAccountDatabase.addIncomeToCustomerAccountBalance(incomeToAdd);
        System.out.println("Income was added!");
    }

    public void doTransfer() {
        System.out.println("Enter card number: ");
        String cardNumberForTransfer = userInput.nextLine();

        if (!(customerAccountDatabase.cardNumberExistsInDatabase(cardNumberForTransfer))) {
            System.out.println("Such a card does not exist.");
        }

        runUserAccount();

        // check number

        for (int i = 0; i < customerAccountDatabase.getCustomerAccountList().size(); i++) {
            if (customerAccountDatabase.getCustomerAccountList().get(i).getCardNumber().equals(cardNumberForTransfer)) {
            }
        }
    }

    public void closeAccount() {
        customerAccountDatabase.removeCustomerAccount(currentUser);
    }

    public void logOut() {
        customerAccountDatabase.setCurrentUser(null);
        System.out.println("\nYou have successfully logged out!\n");
        systemMenu.printMainMenu();
    }

}
