package banking;

public class CustomerAccount {

    private int identificationNumber;
    private String cardNumber;
    private String PINCode;
    private Integer accountBalance = 0;

    public CustomerAccount(int identificationNumber, String cardNumber, String PINCode) {
        this.identificationNumber = identificationNumber;
        this.cardNumber = cardNumber;
        this.PINCode = PINCode;
    }

    public CustomerAccount(int identificationNumber, String cardNumber, String PINCode, Integer balance) {
        this.identificationNumber = identificationNumber;
        this.cardNumber = cardNumber;
        this.PINCode = PINCode;
        this.accountBalance = balance;
    }

    public int getIdentificationNumber() {return identificationNumber;}

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPINCode() {
        return PINCode;
    }

    public void setAccountBalance(int incomeToAdd) {
        accountBalance += Integer.valueOf(incomeToAdd);
    }

    public Integer getAccountBalance() {
        return accountBalance;
    }
}
