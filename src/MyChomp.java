import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class MyChomp implements Serializable {
    //alter to 10, save board then done
    //slightly increases preformance
    //https://stackoverflow.com/questions/14274480/static-final-long-serialversionuid-1l
    private static final long serialVersionUID = 1L;
    int size = 4;
    private static class StateInfo implements Serializable {
        private static final long serialVersionUID = 1L;
        String result; // "W" for winning "L" for lose
        String bestMove;

        StateInfo(String result, String bestMove) {
            this.result = result;
            this.bestMove = bestMove;
        }
    }

    public HashMap<String, StateInfo> states = new HashMap<>();
    public void createboard() {
        int[] startHeights = new int[size];
        //https://www.w3schools.com/java/ref_arrays_fill.asp
        Arrays.fill(startHeights, size);
    }

    public boolean boardsolver(int[] heights) {
        String state = Arrays.toString(heights);

        // Check if the state is already solved
        if (states.containsKey(state)) {
            return states.get(state).result.equals("W");
        }

        // Special case: losing state (only one chip left)
        if (heights[0] == 1 && (heights.length == 1 || heights[1] == 0)) {
            states.put(state, new StateInfo("L", null));
            return false;
        }
        //add comment, maybe one on game theory
        // Try all possible moves
        for (int c = 0; c < size; c++) { // Columns
            for (int r = 0; r < heights[c]; r++) { // Rows
                if (r == 0 && c == 0) {
                    continue; // Skip the last square
                }

                int[] nextState = simulateChomp(heights, r, c);
                if (!boardsolver(nextState)) { // Recursive step
                    states.put(state, new StateInfo("W", r + "," + c)); // Winning move
                    return true;
                }
            }
        }

        // If no winning move is found, mark as losing state
        states.put(state, new StateInfo("L", null));
        return false;
    }

    public String getBestMove(int[] heights) {
        String state = Arrays.toString(heights);
        StateInfo info = states.get(state);
        return (info != null && info.bestMove != null) ? info.bestMove : "No move available";
    }

    public int[] simulateChomp(int[] heights, int row, int col) {
        int[] newHeights = Arrays.copyOf(heights, heights.length);

        // Update the heights for all columns >= col
        for (int c = col; c < size; c++) {
            newHeights[c] = Math.min(newHeights[c], row);
        }

        return newHeights;
    }

    public int[] convertToHeights(Chip[][] board) {
        int[] heights = new int[size];

        // Calculate the height of each column
        for (int c = 0; c < size; c++) {
            int height = 0;
            for (int r = 0; r < board.length; r++) {
                if (board[r][c] != null) { // assime null means no chip
                    height++;
                } else {
                    break;
                }
            }
            heights[c] = height;
        }

        return heights;
    }
    //needed for saving the states tothe seralize file
    public void saveStates(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this.states);
            System.out.println("saved to: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //file loader
    //https://stackoverflow.com/questions/1129795/what-is-suppresswarnings-unchecked-in-java
    @SuppressWarnings("unchecked")
    public void loadStates(String filename) {
        File file = new File(filename);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            this.states = (HashMap<String, StateInfo>) ois.readObject();
            System.out.println("States loaded successfully from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void logAllBestMoves() {
        // Generate all possible board states
        generateAndSolveBoards(new int[size], size - 1);

        // Log all states and their best moves
        // : form works best bc other methods would take sigificnatly more lines. 
        for (String state : states.keySet()) {
            StateInfo info = states.get(state);
            System.out.println("State: " + state + ", Result: " + info.result + ", Best Move: " + info.bestMove);
        }
    }

    private void generateAndSolveBoards(int[] heights, int column) {
        if (column < 0) {
            boardsolver(heights);
            return;
        }

        for (int h = 0; h <= size; h++) {
            heights[column] = h;
            generateAndSolveBoards(heights, column - 1);
        }
    }

    public static void main(String[] args) {
        MyChomp game = new MyChomp();
        String saveFile = "chomp_states.dat";
        //without this, it will try to trwrite to chomp_states every single time you load
        // Try to load existing data
        game.loadStates(saveFile);

        if (game.states.isEmpty()) {
            System.out.println("No save");
            long startTime = System.nanoTime();
            game.logAllBestMoves();
            long endTime = System.nanoTime();

            // Save after calculation
            game.saveStates(saveFile);

            double durationMs = (endTime - startTime) / 1_000_000.0;
            System.out.println("Calculation Time: " + durationMs + " ms");
        } else {
            System.out.println("Loaded " + game.states.size() + " states");
        }
    }
}