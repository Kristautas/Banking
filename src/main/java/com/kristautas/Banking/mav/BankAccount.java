package com.kristautas.Banking.mav;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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

}