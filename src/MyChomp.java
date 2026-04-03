public class MyChomp {
    int i, j, k, l, m;
    int boardnum = 0;
    String LBoardCount = "";
    String WboardCount = "";

    public void createboard() {
        for (i = 0; i <= 5; i++) {
            for (j = 0; j <= i; j++) {
                for (k = 0; k <= j; k++) {
                    for (l = 0; l <= k; l++) {
                        for (m = 0; m <= l; m++) {

                            if (i == 1 && j == 0 && k == 0 && l == 0 && m == 0) {
                                LBoardCount += " [" + i + "," + j + "," + k + "," + l + "," + m + "]";
                                continue;
                            }
                            if (i == 0) continue;

                            boolean FL = false;
                            boardnum += 1;

                            for (int row = 0; row < i; row++) {
                                if (LBoardCount.contains("[" + row + "," + Math.min(j, row) + "," + Math.min(k, row) + "," + Math.min(l, row) + "," + Math.min(m, row) + "]")) {
                                    FL = true;
                                    break;
                                }
                            }

                            if (!FL) {
                                for (int row = 0; row < j; row++) {
                                    if (LBoardCount.contains("[" + i + "," + row + "," + Math.min(k, row) + "," + Math.min(l, row) + "," + Math.min(m, row) + "]")) {
                                        FL = true;
                                        break;
                                    }
                                }
                            }

                            if (!FL) {
                                for (int row = 0; row < k; row++) {
                                    if (LBoardCount.contains("[" + i + "," + j + "," + row + "," + Math.min(l, row) + "," + Math.min(m, row) + "]")) {
                                        FL = true;
                                        break;
                                    }
                                }
                            }

                            if (!FL) {
                                for (int row = 0; row < l; row++) {
                                    if (LBoardCount.contains("[" + i + "," + j + "," + k + "," + row + "," + Math.min(m, row) + "]")) {
                                        FL = true;
                                        break;
                                    }
                                }
                            }

                            if (!FL) {
                                for (int row = 0; row < m; row++) {
                                    if (LBoardCount.contains("[" + i + "," + j + "," + k + "," + l + "," + row + "]")) {
                                        FL = true;
                                        break;
                                    }
                                }
                            }

                            String currentBoard = "[" + i + "," + j + "," + k + "," + l + "," + m + "]";
                            if (FL) {
                                WboardCount += " " + currentBoard;
                            } else {
                                LBoardCount += " " + currentBoard;
                            }

                            System.out.println("Board: " + currentBoard + " -> " + findBestMove(i, j, k, l, m));
                        }
                    }
                }
            }
        }
        System.out.println("Losing Boards : " + LBoardCount);
        System.out.println("Winning Boards: " + WboardCount);
    }

    public String findBestMove(int cI, int cJ, int cK, int cL, int cM) {
        for (int r = 0; r < cI; r++) {
            String s = "[" + r + "," + Math.min(cJ, r) + "," + Math.min(cK, r) + "," + Math.min(cL, r) + "," + Math.min(cM, r) + "]";
            if (LBoardCount.contains(s)) return s;
        }
        for (int r = 0; r < cJ; r++) {
            String s = "[" + cI + "," + r + "," + Math.min(cK, r) + "," + Math.min(cL, r) + "," + Math.min(cM, r) + "]";
            if (LBoardCount.contains(s)) return s;
        }
        for (int r = 0; r < cK; r++) {
            String s = "[" + cI + "," + cJ + "," + r + "," + Math.min(cL, r) + "," + Math.min(cM, r) + "]";
            if (LBoardCount.contains(s)) return s;
        }
        for (int r = 0; r < cL; r++) {
            String s = "[" + cI + "," + cJ + "," + cK + "," + r + "," + Math.min(cM, r) + "]";
            if (LBoardCount.contains(s)) return s;
        }
        for (int r = 0; r < cM; r++) {
            String s = "[" + cI + "," + cJ + "," + cK + "," + cL + "," + r + "]";
            if (LBoardCount.contains(s)) return s;
        }
        return "LB";
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