import java.util.ArrayList;
import java.util.Scanner;

public class Bank {
    Scanner scanner = new Scanner(System.in);

    String bankName;
    ArrayList<BankAccount> accounts;

    public Bank(){
        this.accounts = new ArrayList<>();
    }

    public Bank(String name){
        this.accounts = new ArrayList<>();
        this.bankName = name;
    }

    void addAccount(BankAccount account){
        accounts.add(account);
    }
    void showAllAccounts(){
        for(BankAccount account : accounts){
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
    public int switchAccount(){
        System.out.println("Pick an index for the account: ");
        for(int i = 0; i < accounts.size(); i++){
            int ii = i + 1;
            System.out.println(ii + ". " + accounts.get(i).accountName);
        }
        return(scanner.nextInt() - 1);
    }
}
