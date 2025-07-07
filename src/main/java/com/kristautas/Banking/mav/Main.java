package com.kristautas.Banking.mav;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static BankAccount currentAccount = null;
    public static User user;
    static boolean exit = false;


    public static void main(String[] args) throws SQLException {

        SpringApplication.run(Main.class, args);

        Bank bank = new Bank("Central Bank");
        bank.loadUsersFromDatabase();

        //----------------------

        bank.loadUsersFromDatabase();
        //----------------------

        //User user = new User("Kristautas", "Lebedis", "123456");
        //bank.addUser(user);

        //System.out.println(user);

        System.out.println("-----------------------------------------");
        System.out.println("||*O*||  Welcome to " + bank.bankName + "  ||*O*||");
        System.out.println("-----------------------------------------");
        registerLogin(bank);

        while (!exit && user != null) {
            System.out.println();
            System.out.println("**************************************************************************************************************************************");
            System.out.print("1: Check amount | 2: Deposit money | 3: Withdraw money | 4: Add new account | 5: show all accounts | 6: Switch account | 7: Exit | - ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    if (currentAccount != null) {
                        currentAccount.checkBalance();
                    } else {
                        System.out.println("No account selected. Please create or switch to an account.");
                    }
                }
                case 2 -> {
                    // Changed: Added null check and database persistence
                    if (currentAccount != null) {
                        System.out.print("How much do you want to deposit?: ");
                        try {
                            double amount = scanner.nextDouble();
                            currentAccount.deposit(amount);
                            bank.updateAccountBalance(currentAccount); // Changed: Persist balance to database
                        } catch (Exception e) {
                            System.out.println("Invalid amount. Please enter a valid number.");
                        }
                        scanner.nextLine();
                    } else {
                        System.out.println("No account selected. Please create or switch to an account.");
                    }
                }
                case 3 -> {
                    if (currentAccount != null) {
                        System.out.print("How much do you want to withdraw?: ");
                        try {
                            double amount = scanner.nextDouble();
                            currentAccount.withdraw(amount);
                            bank.updateAccountBalance(currentAccount);
                        } catch (Exception e) {
                            System.out.println("Invalid amount. Please enter a valid number.");
                        }
                    } else {
                        System.out.println("No account selected. Please create or switch to an account.");
                    }
                }
                case 4 -> {
                    if (user != null) {
                        createAccount(user, bank);
                    } else {
                        System.out.println("Please log in first.");
                        registerLogin(bank);
                    }
                }
                case 5 -> {
                    assert user != null;
                    user.showAllAccounts();
                }
                case 6 -> {
                    user.switchAccount();
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
    }
        public static void settings () {
            System.out.println("\033[1mSettings:\033[0m");
        }

        public static void registerLogin (Bank bank){
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print(">> ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 2) {
                register(bank);
            } else if (choice == 1) {
                login(bank);
            }
        }

        public static void createAccount (User user, Bank bank){
            double startingAmount = 0;

            System.out.print("Enter the Number of the account: ");
            String name = scanner.nextLine();

            BankAccount myAccount = new BankAccount(name);

            currentAccount = myAccount;
            bank.addAccount(user, myAccount);

            user.showAllAccounts();
        }

        public static void saveBank (Bank bank, String fileName){
            try (FileOutputStream fileOut = new FileOutputStream("bankInfo.ser");
                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

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

        public static void saveUsers () {
            System.out.println();
        }

        public static void register (Bank bank){
            System.out.print("Enter you First name: ");
            String name = scanner.nextLine();

            System.out.print("Enter your Surname: ");
            String surname = scanner.nextLine();

            System.out.println("Create your password: ");
            String password = createNewPassword();

            boolean repeat = false;
            do {
                System.out.println("Repeat your password: ");
                String repetition = scanner.nextLine();
                if (password.equals(repetition)) {
                    repeat = false;

                    User newUser = new User(name, surname, password);
                    bank.users.add(newUser);
                    user = newUser;
                    bank.saveUserToDatabase(user);

                    System.out.println("Congratulations " + name + " " + surname + " on succesfully registering to " + bank.bankName);
                    System.out.println(user);
                } else {
                    System.out.println("The passwords do not match!");
                    repeat = true;
                    System.out.print("| 1. Try again | 2. Create new password | - ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    if (1 == choice) {
                    } else if (2 == choice) {
                        password = createNewPassword();
                    }
                }
            } while (repeat);
        }

        public static void login (Bank bank){
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
            registerLogin(bank);
        }

        public static String createNewPassword () {
            return scanner.nextLine();
        }

        public static void logIn () {
        }
    }

