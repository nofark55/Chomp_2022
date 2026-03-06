public class MyChomp {
    int i, j, k;
    int boardnum = 0;
    boolean isboard = false;
    String LBoardCount = "";
    String WboardCount = "";
    boolean FL = false;

    public String createboard() {
        for (i = 0; i <= 3; i++) {
            for (j = i; j <= 3; j++) {
                for (k = j; k <= 3; k++) {

                    if (i == 1 && j == 0 && k == 0) {
                        LBoardCount +=  " / " + i + ", " + j + ", " + k;
                        continue;
                    }


                    boardnum += 1;
                    System.out.println("num"+ boardnum + " is " + i + ", " + j + ", " + k);
                    System.out.println("Possible boards:");
                    //i is column 1, it is the only one that needs this check though
                    for (int row = 1; row < i; row++) {

                        if (row == 0 && j == 0 && k == 0){
                            System.out.println("no boards left, end board");
                            break;
                        }
                        int nextI = row;
                        int nextJ = Math.min(j, row);
                        int nextK = Math.min(k, row);
                        System.out.println("[" + nextI + ", " + nextJ + ", " + nextK + "]");

                        if (nextI == 1 && nextJ == 0 && nextK == 0) {
                            System.out.println("winning board found: " + "[" + i + ", " + j + ", " + k + "]");
                        }

                        if (LBoardCount.contains(" / " + i + ", " + j + ", " + k)) {
                            FL = true;
                            break;
                        }

                    }
                    if (! FL) {
                        for (int row = 0; row < j; row++) {
                            int nextI = i;
                            int nextJ = row;
                            int nextK = Math.min(k, row);
                            System.out.println("[" + nextI + ", " + nextJ + ", " + nextK + "]");

                            if (nextI == 1 && nextJ == 0 && nextK == 0) {
                                System.out.println("winning board found: " + "[" + i + ", " + j + ", " + k + "]");
                            }

                            if (LBoardCount.contains(" / " + i + ", " + j + ", " + k)) {
                                FL = false;
                                break;
                            }

                        }
                    }

                    for (int row = 0; row < k; row++) {
                        int nextI = i;
                        int nextJ = j;
                        int nextK = row;
                        System.out.println("[" + nextI + ", " + nextJ + ", " + nextK + "]");

                        if (nextI == 1 && nextJ == 0 && nextK == 0) {
                            System.out.println("winning board found: " + "[" + i + ", " + j + ", " + k + "]");
                        }

                        if (LBoardCount.contains(" / " + i + ", " + j + ", " + k)) {
                            FL = true;
                            break;
                        }

                    }

                    if (FL) {
                        WboardCount +=   " , [ " + i + ", " + j + ", " + k + " ]";
                    } else {
                        LBoardCount += " , [ " + i + ", " + j + ", " + k + " ]";
                    }
                }
            }
        }
        System.out.println("Losing Boards: " + LBoardCount);
        System.out.println("Winning Boards: " + WboardCount);
        //string type needs retrun
        return "";
    }

    public static void main(String[] args) {
        MyChomp game = new MyChomp();
        game.createboard();
    }
}