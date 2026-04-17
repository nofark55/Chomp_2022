import java.util.Arrays;
import java.util.HashMap;

public class MyChomp {
    int size = 3;
    public  HashMap<String, String> states = new HashMap<>();
    public void createboard() {
        int[] startHeights = new int[size];
        //https://www.w3schools.com/java/ref_arrays_fill.asp
        Arrays.fill(startHeights, size);
    }

    public boolean boardsolver(int[] heights) {
        //my current priority is ensuring that it works on a 3x3 in the same format that the chomp file uses for myplayer
        //this boardsolver interacts with the hashmap states and iterates through every
        String state = Arrays.toString(heights);
        //this checkes the hasmap states to see if the array of state is inside of it and returns false if
        if (states.containsKey(state)) {
            //https://www.w3schools.com/java/ref_hashmap_containskey.asp
            //checls if value is L and returns false if it is basicall
            return !states.get(state).equals("L");
        }
        //special case for the final one that is a lose
        if (heights[0] == 1 && (heights.length == 1 || heights[1] == 0)) {
            states.put(state, "L");
            return false;
        }

         for (int c = 0; c < size; c++) { // Columns
             for (int r = 0; r < heights[c]; r++) { // Rows
                 if (r == 0 && c == 0)
                     continue; // Skip the last square

                 int[] nextState = simulateChomp(heights, r, c);
                 if (!boardsolver(nextState)) {
                     states.put(state, r + "," + c);
                     return true;
                 }
             }
         // If no winning move is found, then losing move
        states.put(state, "L");
        return false;
    }
        return false;
    }
        //logic in here for next move, row height col will be put in later
    public int[] simulateChomp(int[] heights, int row, int col) {
        int[] newHeights = Arrays.copyOf(heights, heights.length);

        for (int c = col; c < size; c++) {
            newHeights[c] = Math.min(newHeights[c], row);
        }

        return newHeights;

    }

    public int[] convertToHeights(Chip[][] board) {
        //I need to convert it for my player so that I can translate it

        return n
    }

    public static void main(String[] args) {
        MyChomp game = new MyChomp();
        long startTime = System.nanoTime();
        game.createboard();
        game.createboard();
        long endTime = System.nanoTime();
        double durationMs = (endTime - startTime) / 1_000_000.0;

        System.out.println("TotalT: " + durationMs + " ms");
    }
}