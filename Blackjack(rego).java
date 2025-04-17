/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.blackjack;
import java.util.*;
import java.io.*;

/**
 *
 * @author regor
 */
public class Blackjack {
    static Scanner scanner = new Scanner(System.in);
    static int cardcounter = 0;
    static String name = "rego";


    public static void main(String[] args) throws IOException{
        
        
        double balance = 500.0;
        String choice;
        balance = play(balance);
        
        do{
        cardcounter = 0;
        
        if(balance==0) 
        {
            System.out.println("Ran out of balance! Deposit and spend more money! >:)");
            break;
        }
        
        System.out.printf("Balance: $%.2f%n", balance);
        System.out.println("\nDo you want to play again?\n(1) Play (2) Exit\n");
        choice = scanner.next();
        if(!(choice.equals("1") || choice.equals("2"))){
            do{
                    System.out.println("Please enter either 1 or 2.");
                    System.out.println("\nDo you want to play again?\n(1) Play (2) Exit\n");
                    choice = scanner.next();
                }while(!(choice.equals("1") || choice.equals("2")));
        }
        if (choice.equals("1")){
            balance = play(balance);
        }
        else if (choice.equals("2")){
            System.out.println("Thank you for playing! Come again!");
            break;
        }
        }while(true); 
    }
    
    public static double BJ(double bet, double balance) throws IOException{
        int vdealer;
        int vplayer;
        int hidden;
        String choice;
        System.out.printf("Bet with the amount of $%.2f confirmed! The remaining balance is $%.2f.%n%n", bet, balance);
        vdealer = random(); 
        hidden = random();
        vplayer = random() + random();
        do{
            
            System.out.println(dealer(vdealer));
            System.out.println(player(vplayer));
            
            if(vplayer==21){
                pwin(vdealer, hidden, vplayer, bet);
                return bet*2;
            }       
            
            if((vdealer + hidden) == 21){
                    plose(vdealer, hidden, vplayer, bet);
                    
                    return 0;
            }
            
            System.out.println("(1) Hit (2) Stand\n");
            choice = scanner.next();
            System.out.println("");
            if(!(choice.equals("1") || choice.equals("2"))){
                do{
                    System.out.println("Please enter either 1 or 2.");
                    choice = scanner.next();
                }while(!(choice.equals("1") || choice.equals("2")));
            }
            if (choice.equals("2")){
                vdealer = stand(vdealer+hidden);
                if(vdealer>21 || vdealer<vplayer){
                    pwin(vdealer, hidden, vplayer, bet);
                    
                    return bet*2;
                }
                else if(vdealer==21 || vdealer>vplayer){
                    plose(vdealer, hidden, vplayer, bet);
                    
                    return 0;   
                }
                else if(vplayer == vdealer){
                    System.out.printf("Tie! The bet of $%.2f will be returned.%n", bet);
                    ptie(vdealer, hidden, vplayer, bet);
                    return bet;
                }
            }
            if(choice.equals("1")){
                vplayer += random();
                if(vplayer>21){
                    plose(vdealer, hidden, vplayer, bet);
                    return 0;
                }
                if(vplayer==21){
                    pwin(vdealer, hidden, vplayer, bet);
                    return bet*2; 
                }
            }            
        }while(choice.equals("1") || choice.equals("2"));
        return 0;        
    }
    
    public static int random() {
    
    int[] values = {
    10, 3, 1, 10, 8, 2, 6, 10,
    5, 9, 7, 2, 4, 10, 10, 10,
    10, 10, 5, 4, 7, 10, 10, 9,
    8, 6, 11, 7, 10, 3, 4, 2,
    11, 10, 3, 8, 9, 6, 5, 10,
    2, 4, 3, 7, 6, 8, 1, 9,
    5, 10, 10, 10};

    Random random = new Random();
    int num = values[random.nextInt(values.length)];
    System.out.printf("Card dealt: %s%n", (cardcounter!=1 ? num : "Hidden"));
    cardcounter++;
    return num;
}
    
    public static void pwin(int vdealer, int hidden, int vplayer, double bet) throws IOException{
        System.out.println(dealerf(vdealer, hidden));
        System.out.println(player(vplayer));
        System.out.printf("You Win! Congratulations for winning $%.2f%n", bet*2);
        ptransac(bet, true, "win");
    }

    public static void plose(int vdealer, int hidden, int vplayer, double bet) throws IOException {
        System.out.println(dealerf(vdealer, hidden));
        System.out.println(player(vplayer));
        String output = (vplayer>21 ? "Bust!" : vdealer == 21 ? "Dealer wins through blackjack!" : "Dealer is closest to 21. Dealer wins! ");
        System.out.println(output);
        ptransac(bet, false, "lose");
    }
    
    public static void ptie(int vdealer, int hidden, int vplayer, double bet) throws IOException {
        System.out.println(dealerf(vdealer, hidden));
        System.out.println(player(vplayer));
        System.out.printf("The dealer and player cards tied. The bet of $%.2f will be returned.", bet);
        ptransac(bet, false, "tie");
    }

    public static String dealer(int vdealer){
        return String.format("---------------------%nDealer: %d + ?%n", vdealer);
    }
    
    public static String dealerf(int vdealer, int hidden){
        return String.format("---------------------%nDealer: %d Hidden: %d %n", vdealer, hidden);
    }
    
    public static String player(int vplayer){
        return String.format("Player: %d%n---------------------%n", vplayer);
    }

    public static int stand(int value){
        if(value<18)
        do{
            value+=random();
        }while(value<18);
        return value;
    }

    public static double play(double balance) throws IOException{
        double bet;
        System.out.println("Please enter bet.");
        do{
            bet = scanner.nextDouble();
            if(balance<bet){
                System.out.println("Please enter sufficient amount.");
            }
        }while(balance<bet);
        balance-=bet;
        return balance = balance + BJ(bet, balance);
    }
    
    public static String transac(double bet, boolean win, String result) throws FileNotFoundException, IOException{
        return String.format("%n----------Blackjack Result----------%nBet: $%.2f, Result: %s, %s: $%.2f%n", bet, (win ? "Win" : result.equalsIgnoreCase("lose") ? "Lose" : "Tie"), (win ? "Amount Won" : result.equalsIgnoreCase("lose") ? "Amount Lost" : "Amount Returned"), (win ? bet*2 : bet));
    }

    public static void ptransac(double bet, boolean win, String result) throws FileNotFoundException, IOException{
        FileWriter output = new FileWriter(name+"_transactionhistory.txt", true);
        PrintWriter print = new PrintWriter(output);
        Scanner scan = new Scanner(new FileReader(name+"_transactionhistory.txt"));
        
        System.out.println(transac(bet, win, result));
        print.write(transac(bet, win, result));
        print.close();
    }
}
