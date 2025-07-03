import java.util.ArrayList;

public class User {
    String firstName;
    String surname;
    String password;
    ArrayList<BankAccount> userAccounts = new ArrayList<>();

    public User(String firstName, String surname, String password){
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
    }

    public void showUserName(){
        System.out.println(firstName + " " + surname);
    }

    public boolean checkPass(String attempt){
        return attempt.equals(password);
    }

    public void showAllAccounts(){
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

    public int switchAccount(){
        System.out.println("Pick an index for the account: ");
        for(int i = 0; i < userAccounts.size(); i++){
            int ii = i + 1;
            System.out.println(ii + ". " + userAccounts.get(i).accountNumber);
        }
        return(Main.scanner.nextInt() - 1);
    }

    public String toString(){

        return this.firstName + " " + this.surname + " " + this.password;
    }
}
