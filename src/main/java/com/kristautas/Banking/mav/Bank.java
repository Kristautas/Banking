package com.kristautas.Banking.mav;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank{
    Scanner scanner = new Scanner(System.in);

    String bankName;
    //ArrayList<BankAccount> accounts;
    ArrayList<User> users;

    public Bank() throws SQLException {
        this.users = new ArrayList<>();
    }

    public Bank(String name) throws SQLException {
        this.users = new ArrayList<>();
        this.bankName = name;
    }

    void addUser(User user){
        users.add(user);
    }


    Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bank", "root", "2x486F+SQL");
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");
    
    public void saveUserToDatabase(User user) {
        String query = "INSERT INTO users (FirstName, Surname, Pin) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.firstName);
            stmt.setString(2, user.surname);
            stmt.setString(3, user.password);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setUserID(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadUsersFromDatabase() throws SQLException {
        users.clear();
        String query = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                User user = new User(rs.getInt("idUsers"), rs.getString("FirstName"), rs.getString("Surname"), rs.getString("Pin"));
                users.add(user);
                loadAccountsForUser(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Changed: Added method to load accounts for a specific user
    public void loadAccountsForUser(User user) {
        String query = "SELECT * FROM accounts WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, user.getUserID());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BankAccount account = new BankAccount(rs.getInt("idAccounts"), rs.getInt("UserID"), rs.getString("AccountNumber"), rs.getDouble("Balance"));
                user.userAccounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveAccountToDatabase(User user, BankAccount account) {
        String query = "INSERT INTO accounts (UserID, AccountNumber, Balance) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, user.getUserID());
            stmt.setString(2, account.getAccountNumber());
            stmt.setDouble(3, account.getBalance());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                account.setIdAccounts(rs.getInt(1));
                account.setUserID(user.getUserID());
            }
            else{
                // Changed: Added error handling for failed account creation
                throw new SQLException("Failed to retrieve generated idAccounts");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateAccountBalance(BankAccount currentAccount) {
        String query = "UPDATE accounts SET Balance = ? WHERE idAccounts = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, currentAccount.getBalance());
            stmt.setInt(2, currentAccount.getIdAccounts());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Warning: Account balance update failed!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating account balance in database");
            e.printStackTrace();
        }
    }

    public void addAccount(User user, BankAccount account) {
        saveAccountToDatabase(user, account);
        user.userAccounts.add(account);
    }
}