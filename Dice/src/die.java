
import java.util.Random;
import java.util.Scanner;
import java.time.*; // for LocalDate and LocalTime
import java.time.format.DateTimeFormatter; // for formatting date and time

public class die {
    private static int startingMoney = 1000;
    private static int diceSides = 6;
    private static int overUnderPayout = 2;
    private static int sevenPayout = 4;
    private static int historyInitialSize = 10;
    private static int historyColumns = 4; // Bet, Prediction, Result, Amount Changed

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        while (true) {
            int playerMoney = playGame(scanner, random);
            if (!playAgain(scanner, playerMoney)) {
                System.out.println("Thanks for playing!");
                scanner.close();
                return;
            }
        }
    }

    private static int playGame(Scanner scanner, Random random) {
        int playerMoney = startingMoney;
        int bet;
        String prediction;

        // Initialize 2D array for history: [Bet, Prediction, Result, Amount Changed]
        String[][] history = new String[historyInitialSize][historyColumns];
        int historyCount = 0;

        System.out.println("Welcome to 7 Over 7 Under!");
        System.out.println("Rules: Bet on whether the sum of two dice will be over, under, or exactly 7.");
        System.out.println("Payout: Over/Under - " + overUnderPayout + "x bet, Exactly 7 - " + sevenPayout + "x bet. You start with $" + startingMoney + ".");

        while (playerMoney > 0) {
            System.out.println("\nYour money: $" + playerMoney);
            System.out.print("Enter your bet (or 0 to quit): $");
            try {
                bet = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }
                if (bet == 0) {
                // Get current date and time
                LocalDate date = LocalDate.now();
                LocalTime time = LocalTime.now();
                DateTimeFormatter formatD = DateTimeFormatter.ofPattern("MMM dd, yyyy ");
                DateTimeFormatter formatT = DateTimeFormatter.ofPattern("hh:mm");
                String formattedD = date.format(formatD);
                String formattedT = time.format(formatT);

            if (bet == 0) {
                System.out.println("\nGame History:");
                if (historyCount == 0) {
                    System.out.println("No rolls made in this session.");
                } else {
                    for (int i = 0; i < historyCount; i++) {
                        System.out.printf(
                            "%d. Bet: $%-4s | Prediction: %-6s | Result: %-4s | %s$%s\n",
                            (i + 1),
                            history[i][0],
                            history[i][1],
                            history[i][2],
                            history[i][3].startsWith("-") ? "-" : "+",
                            history[i][3].replace("-", "")
                        );
                    }
                }
                System.out.println("Thanks for playing! Final balance: $" + playerMoney);
                return playerMoney;
            }

            if (bet < 0 || bet > playerMoney) {
                System.out.println("Invalid bet! Must be between $0 and $" + playerMoney);
                continue;
            }

            System.out.print("Predict (over/under/seven): ");
            scanner.nextLine();
            prediction = scanner.nextLine().toLowerCase();

            if (!prediction.equals("over") && !prediction.equals("under") && !prediction.equals("seven")) {
                System.out.println("Invalid prediction! Choose 'over', 'under', or 'seven'.");
                continue;
            }

            int die1 = random.nextInt(diceSides) + 1;
            int die2 = random.nextInt(diceSides) + 1;
            int sum = die1 + die2;
            System.out.println("Dice rolled: " + die1 + " + " + die2 + " = " + sum);

            String result;
            int amountChange = 0;

            if (sum == 7 && prediction.equals("seven")) {
                amountChange = bet * sevenPayout;
                playerMoney += amountChange;
                result = "WIN";
                System.out.println("Exactly 7! You win $" + amountChange);
            } else if (sum > 7 && prediction.equals("over")) {
                amountChange = bet * overUnderPayout;
                playerMoney += amountChange;
                result = "WIN";
                System.out.println("Over 7! You win $" + amountChange);
            } else if (sum < 7 && prediction.equals("under")) {
                amountChange = bet * overUnderPayout;
                playerMoney += amountChange;
                result = "WIN";
                System.out.println("Under 7! You win $" + amountChange);
            } else {
                amountChange = -bet;
                playerMoney += amountChange;
                result = "LOSE";
                System.out.println("You lose $" + bet);
            }

            // Resize history array if full
            if (historyCount >= history.length) {
                String[][] newHistory = new String[history.length * 2][historyColumns];
                for (int i = 0; i < history.length; i++) {
                    for (int j = 0; j < historyColumns; j++) {
                        newHistory[i][j] = history[i][j];
                    }
                }
                history = newHistory;
            }

            // Store history entry in 2D array
            history[historyCount][0] = String.valueOf(bet);
            history[historyCount][1] = prediction;
            history[historyCount][2] = result;
            history[historyCount][3] = String.valueOf(amountChange);
            historyCount++;

            if (playerMoney <= 0) {
                System.out.println("You're out of money! Game over.");
                // Get current date and time
                LocalDate date = LocalDate.now();
                LocalTime time = LocalTime.now();
                DateTimeFormatter formatD = DateTimeFormatter.ofPattern("MMM dd, yyyy ");
                DateTimeFormatter formatT = DateTimeFormatter.ofPattern("hh:mm");
                String formattedD = date.format(formatD);
                String formattedT = time.format(formatT);

                System.out.println("\nGame History:");
                if (historyCount == 0) {
                    System.out.println("No rolls made in this session.");
                } else {
                    for (int i = 0; i < historyCount; i++) {
                        System.out.printf(
                            "%d. Bet: $%-4s | Prediction: %-6s | Result: %-4s | %s$%s\n",
                            (i + 1),
                            history[i][0],
                            history[i][1],
                            history[i][2],
                            history[i][3].startsWith("-") ? "-" : "+",
                            history[i][3].replace("-", "")
                        );
                    }
                }
                String tryAgain;
                while (true) {
                    System.out.print("Would you like to try again? (yes/no): ");
                    tryAgain = scanner.nextLine().toLowerCase();
                    if (tryAgain.equals("yes") || tryAgain.equals("no")) {
                        break;
                    }
                    System.out.println("Invalid input! Please enter 'yes' or 'no'.");
                }
                if (!tryAgain.equals("yes")) {
                    return playerMoney;
                }
                return playerMoney;
            }
        }
        return playerMoney;
    }

    private static boolean playAgain(Scanner scanner, int playerMoney) {
        if (playerMoney > 0) {
            String newGame;
            while (true) {
                System.out.print("Would you like to start a new game? (yes/no): ");
                newGame = scanner.nextLine().toLowerCase();
                if (newGame.equals("yes") || newGame.equals("no")) {
                    break;
                }
                System.out.println("Invalid input! Please enter 'yes' or 'no'.");
            }
            return newGame.equals("yes");
        }
        return false;
    }
}
