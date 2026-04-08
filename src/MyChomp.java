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
            //checls if value is L and returns false if it is basically
            return !states.get(state).equals("L");
        }
        //special case for the final one that is a lose
        if (heights[0] == 1 && (heights.length == 1 || heights[1] == 0)) {
            states.put(state, "L");
            return false;
        }

        //in the current moment, saving to the file seems like it won't really matter

        return false;
    }

        public void RecursiveSteps(int[] heights) {
            for (int i = size; i > 0; i--) {

            }
        }


    public void NextStepFinder(int[] heights) {

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