package sample;

import com.sun.corba.se.spi.ior.IdentifiableFactory;
import com.sun.javafx.collections.ElementObservableListDecorator;

import java.awt.*;

public class Person {
    private double speed;
    private int intellect;
    private boolean isSolitary;
    private int strength;
    private long startTime;
    private int x;
    private int y;
    private int direction =0; //0 up,1right,2down,3 left
    private boolean isGoingSomewhere =false;
    private int[] cordsGoingToRn = new int[2];
    private boolean isMale;
    private boolean isAlive;


    public Person(double s,int x,int y,boolean gender){
        speed = s;
        this.x= x;
        this.y = y;
        startTime = System.nanoTime();
        cordsGoingToRn = new int[]{0,0};
        isMale = gender;


    }

    public boolean canDoAction(){
        return startTime%speed == 0;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime() {
        this.startTime = System.nanoTime();
    }

    public double getSpeed() {
        return speed;
    }

    public void move(int[][] board) {
        boolean check = false;
        int tempx = x;
        int tempy = y;
        int[][] whatIsInFront;
        System.out.println(cordsGoingToRn[0] + "    " + cordsGoingToRn[1] + "    " + isGoingSomewhere);

        if (isGoingSomewhere) {
            int[] temp = moveToSpot();
            tempx = temp[0];
            tempy = temp[1];
        } else {


            if (direction == 0) {
                if (y - 3 < 0) {
                    whatIsInFront = new int[y + 1][];
                } else {
                    whatIsInFront = new int[3][];
                }
            } else if (direction == 1) {
                if (x + 3 > board.length) {
                    whatIsInFront = new int[board.length - x][];
                } else {
                    whatIsInFront = new int[3][];
                }
            } else if (direction == 2) {
                if (y + 3 > board.length) {
                    whatIsInFront = new int[board.length - y][];
                } else {
                    whatIsInFront = new int[3][];
                }
            } else { // this one is 3
                if (x - 3 < 0) {
                    whatIsInFront = new int[x + 1][];
                } else {
                    whatIsInFront = new int[3][];
                }
            }


            if (direction == 0) {
                for (int i = 0; i < whatIsInFront.length; i++) {
                    if (x - (i + 1) < 0) {
                        whatIsInFront[i] = new int[(x + 1) + (i + 1)];
                        for (int w = 0; w < whatIsInFront[i].length; w++) {
                            whatIsInFront[i][w] = board[w][y - (i)];
                        }
                    } else if (x + i + 1 > board.length) {
                        whatIsInFront[i] = new int[(i + 1) + (board.length - x)];
                        for (int w = 0; w < whatIsInFront[i].length; w++) {
                            whatIsInFront[i][w] = board[x - (i + 1) + w][y - (i)];
                        }
                    } else {
                        whatIsInFront[i] = new int[(i + 1) * 2 + 1];
                        for (int w = 0; w < whatIsInFront[i].length; w++) {
                            whatIsInFront[i][w] = board[x - (i + 1) + w][y - (i)];
                        }
                    }
                }
            } else if (direction == 1) {


            } else if (direction == 2) {
                for (int i = 0; i < whatIsInFront.length; i++) {
                    if (x - (i + 1) < 0) {
                        whatIsInFront[i] = new int[(x + 1) + (i + 1)];
                        for (int w = 0; w < whatIsInFront[i].length; w++) {
                            whatIsInFront[i][w] = board[w][y + (i)];
                        }
                    } else {
                        whatIsInFront[i] = new int[(i + 1) * 2 + 1];
                        for (int w = 0; w < whatIsInFront[i].length; w++) {
                            whatIsInFront[i][w] = board[x - (i + 1) + w][y - (i)];
                        }
                    }
                }
            } else {


            }

            for (int i=0;i<whatIsInFront.length;i++){
                for (int w=0;w<whatIsInFront[i].length;w++){
                    System.out.print(whatIsInFront[i][w]);
                }
                System.out.println("  ");


            }
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");














            if (direction == 0) {
                for (int i = 0; i < whatIsInFront.length; i++) {
                    for (int w = 0; w < whatIsInFront[i].length; w++) {
                        if (whatIsInFront[i][w] == 2) {
                            if (x - (i + 1) < 0) {
                                cordsGoingToRn[0] = w;
                                cordsGoingToRn[1] = y - (i+1);
                            } else if (x + i + 1 > board.length) {
                                cordsGoingToRn[0] = w;
                                cordsGoingToRn[1] = y - (i+1);
                            } else {
                                cordsGoingToRn[0] = w;
                                cordsGoingToRn[1] = y - (i+1);
                            }
                            isGoingSomewhere = true;
                            int[] temp = moveToSpot();
                            tempx = temp[0];
                            tempy = temp[1];
                            break;

                        }
                    }
                }
            } else if (direction == 1) {

            } else if (direction == 2) {
                for (int i = 0; i < whatIsInFront.length; i++) {
                    for (int w = 0; w < whatIsInFront[i].length; w++) {
                        if (whatIsInFront[i][w] == 2) {
                            if (x - (i + 1) < 0) {
                                cordsGoingToRn[0] = w;
                                cordsGoingToRn[1] = y - i;
                            } else if (x + i + 1 > board.length) {
                                cordsGoingToRn[0] = w;
                                cordsGoingToRn[1] = y - (i);
                            } else {
                                cordsGoingToRn[0] = w;
                                cordsGoingToRn[1] = y - (i);
                            }
                            isGoingSomewhere = true;
                            int[] temp = moveToSpot();
                            tempx = temp[0];
                            tempy = temp[1];
                            break;

                        }
                    }
                }
            } else {

            }


        }

        if (!isGoingSomewhere) {
            while (!check) {
                tempx = x;
                tempy = y;
                if (Math.random() > .5) {
                    tempx++;
                    if (Math.random() > .33) {
                        tempy--;
                    }
                } else  {
                    tempx--;
                    if (Math.random() > .33) {
                        tempy++;
                    }
                }

                if (board.length > tempx && board[1].length > tempy && tempx >= 0 && tempy >= 0) {
                    if (board[tempx][tempy] == 0 || board[tempx][tempy] == 2) {
                        check = true;
                    }
                }
            }
        }

        board[x][y] = 0;
        board[tempx][tempy] = 1;

        x = tempx;
        y = tempy;

    }


    private int[] moveToSpot(){
        int[] spot = new int[]{x,y};

        if (cordsGoingToRn[0] >spot[0]){
            spot[0]++;
        }else if (cordsGoingToRn[0] <spot[0]){
            spot[0]--;
        }
        if (cordsGoingToRn[1] >spot[1]){
            spot[1]++;
        }else if (cordsGoingToRn[1]<spot[1]){
            spot[1]--;
        }
        if (cordsGoingToRn[0] ==spot[0] && cordsGoingToRn[1] ==spot[1]){
            isGoingSomewhere = false;
        }



        return spot;
    }






    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }








}
