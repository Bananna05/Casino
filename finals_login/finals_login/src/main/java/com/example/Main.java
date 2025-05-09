package com.example;
import java.util.*;
import java.io.*;
import com.example.Users;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int choice;

        while(true) {
            System.out.println("\nEnter a choice:");
            System.out.println("1: User Sign Up");
            System.out.println("2: User Log-In");
            System.out.print("Enter your choice: ");

            if(scanner.hasNextInt()) {
                choice = scanner.nextInt();

                if(choice == 1) {
                    FileWriter writeUser = new FileWriter("user.txt", true);
                    FileWriter writeBalance = new FileWriter("balance.txt", true);
                    
                    scanner.nextLine(); 
                    int startBal = 1000;
        
                    System.out.print("Enter your full name: ");
                    String fullName = scanner.nextLine();
                    System.out.print("Enter a username (no white spaces): ");
                    String userName = scanner.next();
                    System.out.print("Enter a password (no white spaces): ");
                    String password = scanner.next();
        
                    writeUser.write("Full Name: " + fullName + " || " + "Username: " + userName + " || " + "Password: " + password + "\n");
                    writeBalance.write(fullName + "'s remaining balance: " + startBal + "\n");
                    writeBalance.close();
                    writeUser.close();
                    File userFile = new File("userdata/" + userName + ".txt");
                    userFile.getParentFile().mkdirs();
                    userFile.createNewFile();
                    FileWriter writeUFile = new FileWriter(userFile, true);
                    writeUFile.write("Full Name: " + fullName 
                                       + "\nUsername: " + userName
                                       + "\nYour remaining balance: " + startBal + "\n");
                    
                    writeUFile.close();

                    System.out.println("Sign-up successful! Please log in now.");
                    login(scanner);
                }
                else if(choice == 2) {
                    login(scanner);
                }
                else {
                    System.out.println("Invalid input. Please choose between 1 or 2.");
                }
            } else {
                scanner.nextLine();
                System.out.println("Invalid input. Please choose an integer between 1 or 2.");
            }   
        } 
    }

    private static void login(Scanner scanner) throws IOException {
        System.out.print("Enter your username: ");
        String cUsername = scanner.next();
        System.out.print("Enter your password: ");
        String cPassword = scanner.next();

        List<Users> users = readUsersFromFile();

        boolean loggedIn = false;
        for (Users user : users) {
            if(user.getUsername().equals(cUsername)&& user.getPassword().equals(cPassword)) {
                loggedIn = true;
                break;
            }
        }

        if(loggedIn) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    private static List<Users> readUsersFromFile() throws IOException {
        
        List<Users> users = new ArrayList<>();

        Scanner fileReader = new Scanner(new File("user.txt"));

        while(fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] parts = line.split(" \\|\\| ");
            if (parts.length == 3) {
                String fullName = parts[0].split(": ")[1];
                String userName = parts[1].split(": ")[1];
                String password = parts[2].split(": ")[1];

                users.add(new Users(fullName, userName, password));
            } 
        }
        fileReader.close();
        return users;
    }
}