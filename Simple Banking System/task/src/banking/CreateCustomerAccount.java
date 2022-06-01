package banking;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CreateCustomerAccount {

    private CustomerAccountDatabase customerAccountDatabase;
    private String newCardNumber;
    private String newPINCode;
    private ThreadLocalRandom random = ThreadLocalRandom.current();
    private StringBuilder newCardNumberBuilder;
    private static int customerIdNumber = 0;

    public CreateCustomerAccount(CustomerAccountDatabase customerAccountDatabase) {
        this.customerAccountDatabase = customerAccountDatabase;
    }

    public CustomerAccount createNewAccount() {
        Integer generateNewPINCode = random.nextInt(9999);

        if (generateNewPINCode < 1000) {
            generateNewPINCode += 1000;
        }

        newPINCode = generateNewPINCode.toString();
        createNewCardNumberWithLuhnAlgo();
        CustomerAccount newCustomerAccount = null;

        if (accountNumberIsUnique(newCardNumber)) {
            newCustomerAccount = new CustomerAccount(customerIdNumber, newCardNumber, newPINCode);
            customerIdNumber++;
        } else {
            createNewAccount();
        }

        return newCustomerAccount;
    }

    private void createNewCardNumberWithLuhnAlgo() {
        newCardNumberBuilder = new StringBuilder();

        String bankIdentificationNumber = "400000";
        newCardNumberBuilder.append(bankIdentificationNumber);

        Integer generateNewAccountNumber = random.nextInt(100000000, 999999999 + 1);
        newCardNumberBuilder.append(generateNewAccountNumber);
        System.out.println(newCardNumberBuilder.toString());

        Integer generateCheckSum = createCheckSum(newCardNumberBuilder.toString());
        newCardNumberBuilder.append(generateCheckSum);

        newCardNumber = newCardNumberBuilder.toString();
    }

    private Integer createCheckSum(String fifteenDigitNumber) {
        char[] numsInCharArray = fifteenDigitNumber.toCharArray();
        int[] cardNums = new int[numsInCharArray.length];
        int sum = 0;
        int checkSum = 0;

        for (int i = 0; i < cardNums.length; i++) {
            cardNums[i] = Integer.parseInt("" + numsInCharArray[i]);
        }

        for (int i = 0; i < cardNums.length; i++) {
            if ((i + 1) % 2 != 0) {
                cardNums[i] *= 2;
            }
            if (cardNums[i] > 9) {
                cardNums[i] -= 9;
            }
            sum += cardNums[i];
        }

        if (sum % 10 == 0) {
            checkSum = 0;
        } else {
            checkSum = (10 - (sum % 10));
        }

        return Integer.valueOf(checkSum);
    }

    private boolean accountNumberIsUnique(String newCardNumber) {
        for (int i = 0; i < customerAccountDatabase.getCustomerAccountList().size(); i++) {
            if (customerAccountDatabase.getCustomerAccountList().get(i).getCardNumber().equals(newCardNumber)) {
                return false;
            }
        }
        return true;
    }
}
