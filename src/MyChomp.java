public class MyChomp {
    int i = 3;
    int k = 3;
    int j = 3;
    int boardnum = 0;
    String output = "";
    String temp = "";

    //todo:print out all the 3x3 boards
    public String createboard(){

        for (i=3; i > -1; i--){
            if (i == 0){
                break;

            }
            for (j=i; j > -1; j--) {
                for (k=j; k > -1; k--) {
                    boardnum +=1;
                    System.out.println("board: " + i + ", " + j + ", " + k);
                    System.out.println("board count: " + boardnum);
                }
            }
        }
        return (temp);
    }


    public static void main(String[] args) {
        MyChomp game = new MyChomp();
        game.createboard();
    }
}


