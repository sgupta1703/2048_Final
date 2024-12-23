import java.util.*;

public final class Board {
    int rows;
    int cols;
    int[][] board;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = createBoard(rows, cols);
        generateBoard(); 
    }

    public static int[][] createBoard(int rows, int cols) {
        return new int[rows][cols];
    }

    public int[][] getBoard() {
        return board;
    }

    public void generateBoard() {
        Random random = new Random();
        double probability1 = random.nextDouble();
        double probability2 = random.nextDouble();
        int num1 = (probability1 < 0.9) ? 2 : 4;
        int num2 = (probability2 < 0.9) ? 2 : 4;
        
        int index1_1 = random.nextInt(rows);
        int index1_2 = random.nextInt(cols);

        int index2_1, index2_2;

        do {
            index2_1 = random.nextInt(rows);
            index2_2 = random.nextInt(cols);
        } while (index2_1 == index1_1 && index2_2 == index1_2);

        board[index1_1][index1_2] = num1;
        board[index2_1][index2_2] = num2;
    }

    public boolean checkState() {
        for (int[] row : board) {
            for (int value : row) {
                if (value == 32) {
                    return true;
                }
            }
        }
        return false;
    }

    public void printBoard() {
        String horizontalBorder = "+";
        for (int i = 0; i < cols; i++) {
            horizontalBorder += "-------+";
        }
        System.out.println(horizontalBorder);
    
        for (int[] row : board) {
            System.out.print("|");
            for (int value : row) {
                if (value == 0) {
                    System.out.print("       |"); 
                } else {
                    System.out.printf(" %5d |", value);
                }
            }
            System.out.println();
            System.out.println(horizontalBorder);
        }
    }

    public void generateBoard2() {
        Random random = new Random();
        double probability = random.nextDouble();
        int randomNum = (probability < 0.8) ? 2 : 4;
    
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 0) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }
        if (emptyCells.isEmpty()) {
            return;
        }
        int[] randomCell = emptyCells.get(random.nextInt(emptyCells.size()));
        board[randomCell[0]][randomCell[1]] = randomNum;
    }
    
    public boolean move(String direction) {
        boolean moved;
    
        switch (direction) {
            case "left":
                moved = moveLeft();
                break;
            case "right":
                moved = moveRight();
                break;
            case "up":
                moved = moveUp();
                break;
            case "down":
                moved = moveDown();
                break;
            default:
                System.out.println("Invalid direction. Enter left, right, up, or down.");
                return false;
        }
    
        if (moved) {
            generateBoard2();
        }
    
        return moved;
    }
    
    private boolean moveLeft() {
        boolean moved = false;
    
        for (int i = 0; i < rows; i++) {
            int[] row = board[i];
            int[] newRow = new int[cols];
            int newIndex = 0;
    
            for (int j = 0; j < cols; j++) {
                if (row[j] != 0) {
                    newRow[newIndex++] = row[j];
                }
            }
    
            for (int j = 0; j < cols - 1; j++) {
                if (newRow[j] == newRow[j + 1] && newRow[j] != 0) {
                    newRow[j] *= 2;
                    newRow[j + 1] = 0;
                    moved = true; 
                    j++; 
                }
            }
    
            newIndex = 0;
            int[] finalRow = new int[cols];
            for (int value : newRow) {
                if (value != 0) {
                    finalRow[newIndex++] = value;
                }
            }
    
            if (!java.util.Arrays.equals(row, finalRow)) {
                moved = true;
            }
            board[i] = finalRow;
        }
    
        return moved;
    }
    
    private boolean moveRight() {
        reverseRows();
        boolean moved = moveLeft();
        reverseRows();
        return moved;
    }
    
    private void reverseRows() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols / 2; j++) {
                int temp = board[i][j];
                board[i][j] = board[i][cols - 1 - j];
                board[i][cols - 1 - j] = temp;
            }
        }
    }
    
    private boolean moveUp() {
        transposeBoard();
        boolean moved = moveLeft();
        transposeBoard();
        return moved;
    }
    
    private boolean moveDown() {
        transposeBoard();
        boolean moved = moveRight();
        transposeBoard();
        return moved;
    }
    
    private void transposeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = i + 1; j < cols; j++) {
                int temp = board[i][j];
                board[i][j] = board[j][i];
                board[j][i] = temp;
            }
        }
    }
    

    public boolean hasValidMoves() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 0) {  
                    return true;
                }
                if (i + 1 < rows && board[i][j] == board[i + 1][j]) {  
                    return true;
                }
                if (j + 1 < cols && board[i][j] == board[i][j + 1]) { 
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isGameOver() {
        return !hasValidMoves();
    }

    public void resetBoard() {
        board = createBoard(rows, cols); 
        generateBoard(); 
    }
}
