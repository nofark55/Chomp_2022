import java.util.*;
import java.io.*;

public class MyChomp implements Serializable {
    private static final long serialVersionUID = 2L;
    int size = 10; // Set to 10 for the larger board
    //initially, I used a hashmap that stored the entire board as a string with stateinfo, however this was incredibly unoptimized
    public HashMap<Long, Byte> states = new HashMap<>(200000);
    //200000 was chosen because the capacity of a 10x10 is somewhere around 184k states
    //I looked it up and resizing is incredibly unoptimal, and setting the entries to a hard cap prevents constly resizing while attempting to compute the states
    public boolean boardsolver(int[] heights) {
        //bytes are also used bc I am storing very few bits and using a long would be extra wasted memory, this saves a ton.(great wombo)
        //my old method attempted to solve for around 25 billion possibilities, which explained why it took so long when
        long packed = pack(heights);

        Byte info = states.get(packed);
        //winstate flag, look up how to get the mask up but it masks
        if (info != null) return (info & 0x80) != 0;

        // exception, bad last square case so you have to make it false
        if (heights[0] == 1 && (size == 1 || heights[1] == 0)) {
            //https://www.w3schools.com/java/ref_hashmap_put.asp
            states.put(packed, (byte) 0);
            return false;
        }

        for (int c = 0; c < size; c++) {
            for (int r = 0; r < heights[c]; r++) {
                if (r == 0 && c == 0) continue;

                int[] nextState = simulateChomp(heights, r, c);
                if (!boardsolver(nextState)) {
                    states.put(packed, encode(r, c));
                    return true;
                }
            }
        }

        states.put(packed, (byte) 0);
        return false;
    }

// the reason I use bit packing and longs is because it is SIGNIFICANTLY faster than the strings I did before
    //it is also more complex however the time savings offset that. I store every game state as a long bit
    public long pack(int[] heights) {
        long res = 0;
        for (int i = 0; i < size; i++) {
            //what this does is do a left shift operator, so that the res can fit a clmumn  into it, and then it index through it to keep doing it and "pack it"
            res = (res << 4) | (heights[i]);
        }
        return res;
    }
    //https://stackoverflow.com/questions/42071459/bitwise-pack-multiple-numbers-into-a-byte
     byte encode(int r, int c) {
        //reason or is used is because it is faster than arithematic.
         //0x80 flag is used to force first bit to be one which is winning state in the byte that returned, connection to above thing
         //row is shifted 3 times to get out of c way, both take up 3 binary digits,
        return (byte) (0x80 | (r << 3) | c);
    }
    //https://stackoverflow.com/questions/42071459/bitwise-pack-multiple-numbers-into-a-byte
    public int[] simulateChomp(int[] heights, int row, int col) {
        int[] next = heights.clone();
        for (int i = col; i < size; i++) {
            if (next[i] > row) next[i] = row;
        }
        return next;
    }


    public void saveStates(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this.states);
            System.out.println("Saved " + states.size() + " states to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadStates(String filename) {
        File file = new File(filename);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            this.states = (HashMap<Long, Byte>) ois.readObject();
            System.out.println("Loaded " + states.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        MyChomp game = new MyChomp();
        String saveFile = "chomp_optimized.dat";

        // try to load existing data
        File file = new File(saveFile);
        if (file.exists()) {
            System.out.println("Loading: " + saveFile);
            game.loadStates(saveFile);
            System.out.println("Current state c: " + game.states.size());
        } else {
            System.out.println("nsv, fix");
        }
        long start = System.currentTimeMillis();
        game.saveStates(saveFile);
        System.out.println(" Time: " + (System.currentTimeMillis() - start) + "ms");
    }
}