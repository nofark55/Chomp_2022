import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class MyPlayer {
    public Chip[][] gameBoard;
    private MyChomp solver;
    private final String SAVE_FILE = "chomp_optimized.dat";

    public MyPlayer() {
        solver = new MyChomp();
        solver.loadStates(SAVE_FILE);
    }

    public Point move(Chip[][] pBoard) {
        this.gameBoard = pBoard;

        int[] heights = convertToHeights(pBoard);

        long packedState = solver.pack(heights);

        Byte info = solver.states.get(packedState);

        if (info != null && (info & 0x80) != 0) {
            int r = (info >> 3) & 0x7;
            int c = info & 0x7;

            System.out.println("AI Move Found! Playing: Row " + r + ", Col " + c);

            // In your Chomp.java: row = theMove.getX(), col = theMove.getY()
            // So we put Row in X and Col in Y
            return new Point(r, c);
        }

        System.out.println("no move found");
        return playsafe(heights);
    }

    private int[] convertToHeights(Chip[][] board) {
        int[] heights = new int[10];

        // loop through  columns
        for (int c = 0; c < 10; c++) {
            int h = 0;
            for (int r = 0; r < 10; r++) {
                //checks isalive and null
                if (board[r][c] != null && board[r][c].isAlive) {
                    h++;
                } else {
                    break;
                }
            }
            heights[c] = h;
        }

        System.out.println("Solver sees heights: " + Arrays.toString(heights));
        return heights;
    }
//for some reason sometimes it misses so this ensure that if it cna't find a move, it will attempt to alter the board to find a winning wombo.
    private Point playsafe(int[] heights) {
        for (int c = 9; c >= 0; c--) {
            if (heights[c] > 0) {
                int r = heights[c] - 1;
                if (r == 0 && c == 0) continue;
                return new Point(r, c);
            }
        }
        return new Point(0, 0);
    }
}