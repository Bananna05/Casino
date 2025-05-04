/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.authenticatorcasinogame;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Arch Coles
 */
public class AuthenticatorCasinoGame {

    static final String FILE_NAME = "credentials.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Casino Game!");
        System.out.print("Do you want to Sign Up or Log In? (signup/login): ");
        String choice = sc.nextLine().trim().toLowerCase();

        if (choice.equals("signup")) {
            signUp();
            System.out.println("\nNow please log in.");
            logIn();
        } else if (choice.equals("login")) {
            logIn();
        } else {
            System.out.println("Invalid option. Restart the program.");
        }
    }

    public static void signUp() throws IOException {
        System.out.print("Enter a username: ");
        String username = sc.nextLine().trim();

        System.out.print("Enter a password: ");
        String password = sc.nextLine().trim();

        FileWriter fw = new FileWriter(FILE_NAME, true);
        fw.write(username + "," + password + "\n");
        fw.close();

        System.out.println("Account created successfully.");
    }

    public static void logIn() throws IOException {
        while (true) {
            System.out.print("Enter your username: ");
            String inputUser = sc.nextLine().trim();

            System.out.print("Enter your password: ");
            String inputPass = sc.nextLine().trim();

            if (validateCredentials(inputUser, inputPass)) {
                System.out.println("Login successful! Welcome, " + inputUser + "!");
                break;
            } else {
                System.out.println("Invalid username or password. Try again.\n");
            }
        }
    }

    public static boolean validateCredentials(String username, String password) throws IOException {
        FileReader fr = new FileReader(FILE_NAME);
        StringBuilder line = new StringBuilder();
        int ch;

        while ((ch = fr.read()) != -1) {
            if (ch == '\n') {
                if (checkLineMatch(line.toString(), username, password)) {
                    fr.close();
                    return true;
                }
                line.setLength(0);
            } else {
                line.append((char) ch);
            }
        }

        if (line.length() > 0 && checkLineMatch(line.toString(), username, password)) {
            fr.close();
            return true;
        }

        fr.close();
        return false;
    }

    public static boolean checkLineMatch(String line, String username, String password) {
        int commaIndex = line.indexOf(',');
        if (commaIndex != -1) {
            String fileUser = line.substring(0, commaIndex);
            String filePass = line.substring(commaIndex + 1);
            return fileUser.equals(username) && filePass.equals(password);
        }
        return false;
    }
}
