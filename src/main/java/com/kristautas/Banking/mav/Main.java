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
    public static Bank bank;

    static {
        try {
            bank = new Bank("Central Bank");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws SQLException {

        SpringApplication.run(Main.class, args);


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
            System.out.print("1: Check amount | 2: Deposit money | 3: Withdraw money | 4: Add new account | 5: Settings | 6: Switch account | 7: Exit | - ");
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
                        BankAccount.createAccount(user, bank);
                    } else {
                        System.out.println("Please log in first.");
                        registerLogin(bank);
                    }
                }
                case 5 -> {
                    currentAccount.accountSettings();
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
            else{
                System.out.println("Invalid choice. Please try again.");
                registerLogin(bank);
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

        public static void login(Bank bank) {
            System.out.println("Enter your full name");
            String firstName = scanner.next();
            String surname = scanner.next();
            scanner.nextLine();

            boolean userFound = false;

            for (User u : bank.users) {
                if (u.firstName.equals(firstName) && u.surname.equals(surname)) {
                    userFound = true;
                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();

                    if (!u.checkPass(password)) {
                        System.out.println("Invalid password. Please try again. Type 'exit' to exit.");
                        String password2 = scanner.nextLine();
                        if (password2.equalsIgnoreCase("exit")) {
                            registerLogin(bank);
                            return;
                        }
                        if (!u.checkPass(password2)) {
                            System.out.println("Invalid password. Returning to login.");
                            registerLogin(bank);
                            return;
                        }
                    }

                    user = u; // Set the user variable
                    if (!u.userAccounts.isEmpty()) {
                        currentAccount = u.userAccounts.getFirst();
                        System.out.println("Login successful for " + firstName + " " + surname);
                    } else {
                        System.out.println("You don't have any accounts. Please create one.");
                        BankAccount.createAccount(u, bank);
                    }
                    break;
                }
            }

            if (!userFound) {
                System.out.println("User not found. Please try again or register.");
                registerLogin(bank);
            }
        }

        public static String createNewPassword () {
            return scanner.nextLine();
        }

        public static void logIn () {
        }
    }