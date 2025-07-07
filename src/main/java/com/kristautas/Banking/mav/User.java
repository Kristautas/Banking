package com.kristautas.Banking.mav;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class User {
    String firstName;
    String surname;
    String password;
    @Getter
    @Setter
    private int userID;
    ArrayList<BankAccount> userAccounts = new ArrayList<>();

    public User(String firstName, String surname, String password){
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
    }

    public User(String firstName, String surname, String password, int userID){
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
        this.userID = userID;
    }

    public User(int userID, String firstName, String surname, String password){
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
        this.userID = userID;
    }

    public void showUserName(){
        System.out.println(firstName + " " + surname);
    }

    public boolean checkPass(String attempt){
        return attempt.equals(password);
    }

    public void showAllAccounts(){
        if (userAccounts.isEmpty()) {
            System.out.println("You don't have any accounts");
            return;
        }
        
        for(BankAccount account : userAccounts){
            if(Main.currentAccount.equals(account)){
                System.out.print("* ");
                account.show();
            }
            else{
                System.out.print("  ");
                account.show();
            }
        }
    }

    public void switchAccount(){
        if (userAccounts.isEmpty()) {
            System.out.println("You don't have any accounts to switch to");
            return;
        }
        
        System.out.println("Pick an index for the account: ");
        for(int i = 0; i < userAccounts.size(); i++){
            int ii = i + 1;
            System.out.println(ii + ". " + userAccounts.get(i).accountNumber);
        }
        Main.currentAccount = userAccounts.get(Main.scanner.nextInt() - 1);
        System.out.println("Switched to account " + Main.currentAccount.accountNumber);
    }

    public String toString(){
        return this.firstName + " " + this.surname + " " + this.password;
    }

}