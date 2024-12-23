import java.util.Scanner;

public class run {
    public static void printStart() {
        System.out.println("===================================");
        System.out.println("              2048                 ");
        System.out.println("===================================");
        System.out.println("Welcome to 2048!");
        System.out.println("Use the following commands to play:");
        System.out.println(" - 'left' to move tiles left");
        System.out.println(" - 'right' to move tiles right");
        System.out.println(" - 'up' to move tiles up");
        System.out.println(" - 'down' to move tiles down");
        System.out.println(" - 'exit' to quit the game anytime");
        System.out.println("The goal is to get the tile to 2048!");
        System.out.println("Good luck!");
        System.out.println("===================================");
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Board board = new Board(4, 4);
        printStart();
    
        while (true) {
            board.printBoard();
    
            if (board.isGameOver()) {
                System.out.println("Game Over! No valid moves left.");
                System.out.println("Type 'restart' to play again or 'exit' to quit.");
            } else {
                System.out.println("Enter your move (left, right, up, down, restart, or exit):");
            }
    
            String playerMove = in.nextLine().toLowerCase().trim();
    
            if (playerMove.equals("exit")) {
                System.out.println("Thanks for playing! Goodbye.");
                break;
            }
    
            if (playerMove.equals("restart")) {
                board.resetBoard();
                System.out.println("Game restarted!");
                continue;
            }
    
            if (playerMove.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }
    
            boolean move = board.move(playerMove);
            if (move) {
                if (board.checkState()) {
                    board.printBoard();
                    System.out.println("Congratulations! You won the game!");
                    System.out.println("Type 'restart' to play again or 'exit' to quit.");
                    String choice = in.nextLine().toLowerCase().trim();
                    if (choice.equals("restart")) {
                        board.resetBoard();
                        System.out.println("Game restarted!");
                    } else {
                        System.out.println("Thanks for playing! Goodbye.");
                        break;
                    }
                }
            } else if (!playerMove.equals("left") && !playerMove.equals("right") &&
                       !playerMove.equals("up") && !playerMove.equals("down")) {
                System.out.println("Invalid input. Please enter 'left', 'right', 'up', 'down', 'restart', or 'exit'.");
            } else {
                System.out.println("Move not possible. Try a different direction.");
            }
        }
    
        in.close();
    }
}    
