import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Game2048 {
    private Board board; 
    private JLabel[][] cells; 
    private JFrame frame;

    public Game2048() {
        board = new Board(4, 4);
        frame = new JFrame("2048 Game");
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(4, 4));
        cells = new JLabel[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                JLabel cell = new JLabel("", SwingConstants.CENTER);
                cell.setFont(new Font("Arial", Font.BOLD, 24));
                cell.setOpaque(true);
                cell.setBackground(Color.LIGHT_GRAY);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cells[i][j] = cell;
                gridPanel.add(cell);
            }
        }

        frame.add(gridPanel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        updateGrid(); 
        setupKeyListener();
    }

    private void setupKeyListener() {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String direction = switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP, KeyEvent.VK_W -> "up";
                    case KeyEvent.VK_DOWN, KeyEvent.VK_S -> "down";
                    case KeyEvent.VK_LEFT, KeyEvent.VK_A -> "left";
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> "right";
                    default -> null;
                };

                if (direction != null && board.move(direction)) {
                    updateGrid();
                    if (board.checkState()) {
                        JOptionPane.showMessageDialog(frame, "Congratulations! You won!");
                        board.resetBoard();
                        updateGrid();
                    } else if (board.isGameOver()) {
                        JOptionPane.showMessageDialog(frame, "Game Over! No valid moves left.");
                        board.resetBoard();
                        updateGrid();
                    }
                }
            }
        });
    }

    private void updateGrid() {
        int[][] boardState = board.getBoard();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int value = boardState[i][j];
                cells[i][j].setText(value == 0 ? "" : String.valueOf(value));
                cells[i][j].setBackground(getTileColor(value));
            }
        }
    }

    private Color getTileColor(int value) {
        return switch (value) {
            case 2 -> new Color(238, 228, 218);
            case 4 -> new Color(237, 224, 200);
            case 8 -> new Color(242, 177, 121);
            case 16 -> new Color(245, 149, 99);
            case 32 -> new Color(246, 124, 95);
            case 64 -> new Color(246, 94, 59);
            case 128 -> new Color(237, 207, 114);
            case 256 -> new Color(237, 204, 97);
            case 512 -> new Color(237, 200, 80);
            case 1024 -> new Color(237, 197, 63);
            case 2048 -> new Color(237, 194, 46);
            default -> Color.LIGHT_GRAY;
        };
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game2048::new);
    }
}
