public class MyChomp {
    int i, j, k;
    int boardnum = 0;
    String LBoardCount = "";
    String WboardCount = "";


    public void createboard() {
        for (i = 0; i <= 3; i++) {
            for (j = 0; j <= i; j++) {
                for (k = 0; k <= j; k++){


                    if (i == 1 && j == 0 && k == 0) {
                        //least amout of hardcoding, tbh I could just switch it be a hard coded case.
                        LBoardCount += " [" + i + "," + j + "," + k + "]";
                        continue;

                    }
                    if (i == 0) continue;

                    boolean FL = false;
                    boardnum +=1;

                    for (int row = 0; row < i; row++) {
                        int nI = row;
                        int nJ = Math.min(j, row);
                        int nK = Math.min(k, row);
                        String nextState = "[" + nI + "," + nJ + "," + nK + "]";

                        if (LBoardCount.contains(nextState)) {
                            FL = true;
                            break;
                        }
                    }

                    if (!FL) {
                        for (int row = 0; row < j; row++) {
                            int nI = i;
                            int nJ = row;
                            int nK = Math.min(k, row);
                            String nextState = "[" + nI + "," + nJ + "," + nK + "]";
                            if (LBoardCount.contains(nextState)) {
                                FL = true;
                                break;
                            }
                        }
                    }

                    if (!FL) {
                        for (int row = 0; row < k; row++) {
                            int nI = i;
                            int nJ = j;
                            int nK = row;
                            String nextState = "[" + nI + "," + nJ + "," + nK + "]";
                            if (LBoardCount.contains(nextState)) {
                                FL = true;
                                break;
                            }
                        }
                    }

                    String currentBoard = "[" + i + "," + j + "," + k + "]";
                    if (FL) {
                        WboardCount += " " + currentBoard;
                    } else {
                        LBoardCount += " " + currentBoard;
                    }
                    String bestMove = findBestMove(i, j, k);
                    System.out.println("Board: [" + i + "," + j + "," + k + "] -> " + bestMove);
                }
            }
        }
        System.out.println("Losing Boards : " + LBoardCount);
        System.out.println("Winning Boards: " + WboardCount);
    }

    public String findBestMove(int currentI, int currentJ, int currentK) {
        for (int row = 0; row < currentI; row++) {
            int nI = row;
            int nJ = Math.min(currentJ, row);
            int nK = Math.min(currentK, row);
            String nextState = "[" + nI + "," + nJ + "," + nK + "]";
            if (LBoardCount.contains(nextState)) return nextState;
        }

        for (int row = 0; row < currentJ; row++) {
            int nI = currentI;
            int nJ = row;
            int nK = Math.min(currentK, row);
            String nextState = "[" + nI + "," + nJ + "," + nK + "]";
            if (LBoardCount.contains(nextState)) return nextState;
        }

        for (int row = 0; row < currentK; row++) {
            int nI = currentI;
            int nJ = currentJ;
            int nK = row;
            String nextState = "[" + nI + "," + nJ + "," + nK + "]";
            if (LBoardCount.contains(nextState)) return nextState;
        }

        return "LB";
    }

    public static void main(String[] args) {
        MyChomp game = new MyChomp();
        game.createboard();
    }
}