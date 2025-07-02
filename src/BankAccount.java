public class BankAccount {
    private double balance;
    String accountName;


    public BankAccount(String accountName){ //default constructor
        this.accountName = accountName;
        this.balance = 0;
    }

    public BankAccount(String accountName, double balance){ //constructor with bank balance
        this.accountName = accountName;
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
        System.out.printf("%-20s %-20.2f%n", accountName, balance);
    }

}