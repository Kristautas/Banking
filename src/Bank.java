import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank implements Serializable {
    Scanner scanner = new Scanner(System.in);

    String bankName;
    //ArrayList<BankAccount> accounts;
    ArrayList<User> users;

    public Bank(){
        this.users = new ArrayList<>();
    }

    public Bank(String name){
        this.users = new ArrayList<>();
        this.bankName = name;
    }

    void addUser(User user){
        users.add(user);
    }
}
