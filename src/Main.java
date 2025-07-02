import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    static boolean exit = false;
    //static ArrayList<BankAccount> accounts = new ArrayList<>();
    public static BankAccount currentAccount = null;

    public static void main(String[] args) {

        Bank bank = new Bank();

        createAccount(currentAccount, bank);

        while(!exit){
            System.out.println();
            System.out.println("**************************************************************************************************************************************");
            System.out.print("1: Check amount | 2: Deposit money | 3: Withdraw money | 4: Add new account | 5: show all accounts | 6: Switch account | 7: Exit | - ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    currentAccount.checkBalance();
                }
                case 2 -> {
                    System.out.print("How much do you want to deposit?: ");
                    double amount = scanner.nextDouble();
                    currentAccount.deposit(amount);
                }
                case 3 -> {
                    System.out.print("How much do you want to withdraw?: ");
                    double amount = scanner.nextDouble();
                    currentAccount.withdraw(amount);
                }
                case 4 -> {
                    createAccount(currentAccount, bank);
                }
                case 5 -> {
                    bank.showAllAccounts();
                }
                case 6 -> {
                    int index = bank.switchAccount();
                    if(index >= 0 && index < bank.accounts.size()){
                        currentAccount = bank.accounts.get(index);
                        System.out.print("The selected account is ");
                        currentAccount.show();
                    }
                }
                case 7 -> {
                    exit = true;
                    System.out.println("The bank has closed for the day");
                }
            }
        }

    }

    public static void createAccount(BankAccount bankAccount, Bank bank){
        double startingAmount = 0;

        BankAccount myAccount;

        System.out.print("Enter the full name for the account: ");
        String name = scanner.nextLine();

        System.out.print("Do you want to deposit straight away? (yes/no): ");
        if(scanner.nextLine().equals("yes")) {
            System.out.print("Enter starting amount: ");
            startingAmount = scanner.nextDouble();
            scanner.nextLine();
        }

        if(startingAmount == 0){
            currentAccount = new BankAccount(name);
        }
        else{
            currentAccount = new BankAccount(name, startingAmount);
        }

        bank.addAccount(currentAccount);
    }

}