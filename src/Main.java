import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static boolean exit = false;

    public static void main(String[] args) {

        double startingAmount = 0;
        BankAccount myAccount; //Creating account
        BankAccount JohnAccount;

        System.out.print("Do you want to deposit straight away? (yes/no): ");
        if(scanner.nextLine().equals("yes")) {
                System.out.println("Enter starting amount: ");
                startingAmount = scanner.nextDouble();
            }
        if(startingAmount == 0){
            myAccount = new BankAccount();
        }
        else{
            myAccount = new BankAccount(startingAmount);
        }





        while(!exit){

            System.out.print("1: Check amount | 2: Deposit money | 3: Withdraw money | 4: Exit | - ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    myAccount.checkBalance(myAccount);
                }
                case 2 -> {
                    System.out.print("How much do you want to deposit?: ");
                    double amount = scanner.nextDouble();
                    myAccount.deposit(myAccount, amount);
                }
                case 3 -> {
                    System.out.print("How much do you want to withdraw?: ");
                    double amount = scanner.nextDouble();
                    myAccount.withdraw(myAccount, amount);
                }
                case 4 -> exit = true;
            }
        }

    }
}