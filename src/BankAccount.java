public class BankAccount {
    private double balance;


    public BankAccount(){ //default constructor
        this.balance = 0;
    }

    public BankAccount( double balance){ //constructor with bank balance
        this.balance = balance;
    }

    void deposit(BankAccount myAccount, double amount){
        this.balance += amount;
        System.out.println("Your balance was increased by " + amount + "$ and is now " + this.balance + "$");
    }
    public void checkBalance(BankAccount myAccount){
        System.out.println("Your balance is " + this.balance);
    }
    public void withdraw(BankAccount myAccount, double amount){
        this.balance -= amount;
        System.out.println("You withdrew " + amount + "$ and you balance is now " + this.balance + "$");
    }
}