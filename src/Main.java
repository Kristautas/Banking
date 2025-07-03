import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static BankAccount currentAccount = null;
    public static User user;
    static boolean exit = false;


    public static void main(String[] args) {

        Bank bank = new Bank("Central Bank");

        User user = new User("Kristautas", "Lebedis", "123456");
        bank.addUser(user);

        //System.out.println(user);

        registerLogin(bank);

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
                    createAccount(user, bank);
                }
                case 5 -> {
                    user.showAllAccounts();
                }
                case 6 -> {
                    int index = user.switchAccount();
                    if(index >= 0 && index < user.userAccounts.size()){
                        currentAccount = user.userAccounts.get(index);
                        System.out.print("The selected account is ");
                        currentAccount.show();
                    }
                }
                case 7 -> {
                    exit = true;
                    System.out.println("The bank has closed for the day");
                }
                case 10 -> {
                    registerLogin(bank);
                }
            }
        }
        System.out.println("You don't have any accounts");
    }

    public static void settings(){
        System.out.println("\033[1mSettings:\033[0m");
    }

    public static void registerLogin(Bank bank){
        System.out.println("-----------------------------------------");
        System.out.println("||*O*||  Welcome to " + bank.bankName + "  ||*O*||");
        System.out.println("-----------------------------------------");
        System.out.println("Type 1 to login into an existing account");
        System.out.println("Type 2 to register a new account");
        System.out.print(">> ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if(choice == 2){
            register(bank);
        }
        else if(choice == 1){
            login(bank);
        }
    }

    public static void createAccount(User user, Bank bank){
        double startingAmount = 0;

        System.out.print("Enter the Number of the account: ");
        String name = scanner.nextLine();

        BankAccount myAccount = new BankAccount(name);

        currentAccount = myAccount;
        user.userAccounts.add(myAccount);
    }

    public static void saveBank(Bank bank, String fileName){
        try (FileOutputStream fileOut = new FileOutputStream("bankInfo.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)){

             out.writeObject(bank); // Serialize the Bank object
             System.out.println("Bank data saved successfully to: " + fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found or cannot be created: " + fileName);
            throw new RuntimeException("Failed to save bank data", e);
        } catch (IOException e) {
            System.err.println("Error: Failed to write bank data to file: " + fileName);
            throw new RuntimeException("Failed to save bank data", e);
        }
    }

    public static void saveUsers(){
        System.out.println();
    }

    public static void register(Bank bank){
        System.out.print("Enter you First name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your Surname: ");
        String surname = scanner.nextLine();

        System.out.println("Create your password: ");
        String password = createNewPassword();

        boolean repeat = false;
        do{
            System.out.println("Repeat your password: ");
            String repetition = scanner.nextLine();
            if (password.equals(repetition)){
                repeat = false;

                User newUser = new User(name, surname, password);
                bank.users.add(newUser);
                user = newUser;

                System.out.println("Congratulations " + name + " " + surname + " on succesfully registering to " + bank.bankName);
                System.out.println(user);
            }
            else{
                System.out.println("The passwords do not match!");
                repeat = true;
                System.out.print("| 1. Try again | 2. Create new password | - ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                if(1 == choice){
                }
                else if(2 == choice){
                    password = createNewPassword();
                }
            }
        }while(repeat);
    }

    public static void login(Bank bank){
        System.out.println("Enter your full name");
        String firstName = scanner.next();
        String surname = scanner.next();
        scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        for (User u : bank.users) {
            if (u.firstName.equals(firstName) && u.surname.equals(surname) && u.password.equals(password)) {
                user = u;
                if (!u.userAccounts.isEmpty()) {
                    currentAccount = u.userAccounts.get(0);
                }
                System.out.println("Login successful for " + firstName + " " + surname);
                return;
            }
        }
        System.out.println("Login failed. Check your credentials or register.");
    }

    public static String createNewPassword(){
        return scanner.nextLine();
    }

    public static void logIn(){
    }
}