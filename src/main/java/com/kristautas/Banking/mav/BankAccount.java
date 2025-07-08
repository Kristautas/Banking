package com.kristautas.Banking.mav;

import lombok.Getter;
import lombok.Setter;

public class BankAccount{
    @Setter
    @Getter
    private int idAccounts;
    // Fixed: Implement setUserID
    @Setter
    int userID;
    @Getter
    String accountNumber;
    @Getter
    private double balance;

    public BankAccount(String accountNumber){ //default constructor
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    public BankAccount(int idAccounts, int userID, String accountNumber, double balance){ //constructor with bank balance
        this.idAccounts = idAccounts;
        this.userID = userID;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    void deposit(double amount){
        this.balance += amount;
        System.out.println("Your balance was increased by " + amount + "$ and is now " + this.balance + "$");
    }
    public void checkBalance(){
        System.out.println("Your balance is " + this.balance);
    }
    public void withdraw(double amount){
        this.balance -= amount;
        System.out.println("You withdrew " + amount + "$ and you balance is now " + this.balance + "$");
    }

    public void show(){
        System.out.printf("%-20s %-20.2f%n", accountNumber, balance);
    }

    public void accountSettings(){

        boolean exit = false;

        while (!exit) {
            System.out.println();
            System.out.println("1. Show all accounts");
            System.out.println("2. Show account details");
            System.out.println("3. Switch account");
            System.out.println("4. Change account number");
            System.out.println("5. Delete account");
            System.out.println("6. Exit");
            System.out.print(">> ");
            int input = Main.scanner.nextInt();
            Main.scanner.nextLine();
            System.out.println();
            switch (input){
                case 1 -> {
                    assert Main.user != null;
                    Main.user.showAllAccounts();}
                case 2 -> {
                    System.out.println("   *   Account details:   *");
                    System.out.println("Owner: " + Main.user.firstName + " " + Main.user.surname);
                    System.out.println("Account number: " + Main.currentAccount.accountNumber);
                    System.out.println("Balance: " + Main.currentAccount.balance);
                }
                case 3 -> {
                    Main.user.switchAccount();}
                case 4 -> {
                    changeAccountNumber(Main.currentAccount);
                }
                case 5 -> {}
                case 6 -> {
                    System.out.println("Exiting account settings");
                    exit = true;
                }
            }
        }
    }

    public static void changeAccountNumber(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("Account and bank must not be null");
        }
        while (true) {
            System.out.print("Enter a 5 digit Number of the account (or 'exit' to cancel): ");
            String input = Main.scanner.nextLine();
            if(accountNumberValidation(input)){
                account.accountNumber = input;
                System.out.println("Account number changed to " + Main.currentAccount.accountNumber);
                Main.bank.saveAccountToDatabase(Main.user, account);
                break;
            }
            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Account creation cancelled");
                return;
            }
            else{
                System.out.println("Invalid format. Please enter exactly 5 digits");
            }
        }
    }

    public static boolean accountNumberValidation(String input){
        boolean valid = false;
        if (input.length() != 5 || !input.matches("[0-9]+")) {
            return valid;
        }
        else valid = true;
        return valid;
    }

    public static void createAccount(User user, Bank bank) {
        if (user == null || bank == null) {
            throw new IllegalArgumentException("User and bank must not be null");
        }

        while (true) {
            System.out.print("Enter a 5 digit Number of the account (or 'exit' to cancel): ");
            String number = Main.scanner.nextLine();
            
            if ("exit".equalsIgnoreCase(number)) {
                System.out.println("Account creation cancelled");
                return;
            }

            // Validate format first
            if (number.length() != 5 || !number.matches("[0-9]+")) {
                System.out.println("Invalid format. Please enter exactly 5 digits");
                continue;
            }

            // Check if account exists
            boolean accountExists = user.userAccounts.stream()
                    .anyMatch(account -> account.accountNumber.equals(number));
            
            if (accountExists) {
                System.out.println("Account number already exists. Please try another number");
                continue;
            }

            try {
                BankAccount myAccount = new BankAccount(number);
                Main.currentAccount = myAccount;
                bank.addAccount(user, myAccount);
                user.showAllAccounts();
                System.out.println("Account created successfully!");
                bank.saveUserToDatabase(user);
                return;
            } catch (Exception e) {
                System.out.println("Error creating account: " + e.getMessage());
                return;
            }
        }
    }
    
}